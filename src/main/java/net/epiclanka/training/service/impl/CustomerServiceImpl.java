package net.epiclanka.training.service.impl;


import net.epiclanka.training.model.Customer;
import net.epiclanka.training.model.CustomerModel;
import net.epiclanka.training.model.CustomerResponse;
import net.epiclanka.training.model.ErrorResponse;
import net.epiclanka.training.repository.CustomerRepo;
import net.epiclanka.training.service.CustomerService;
import net.epiclanka.training.service.SequenceGeneratorService;
import net.epiclanka.training.util.EncoderDecoder;
import net.epiclanka.training.util.ErrorHandling;
import net.epiclanka.training.util.GenericDisplay;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;
    private ModelMapper modelMapper;
    private ErrorHandling errorHandling;
    private SequenceGeneratorService generatorService;
    private final MongoTemplate mongoTemplate;

    private static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, ModelMapper modelMapper, ErrorHandling errorHandling, SequenceGeneratorService generatorService, MongoTemplate mongoTemplate) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.errorHandling = errorHandling;
        this.generatorService = generatorService;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public ResponseEntity<Object> addCustomerData(Customer customer) {
        try {
            if (customerRepo.existsById(customer.getId())) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setErrorCode(errorHandling.getSaveErrorCode());
                errorResponse.setErrorMessage(errorHandling.getSaveErrorMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.ACCEPTED);
            }

            customer.setId(generatorService.generateSequence(CustomerModel.SEQUENCE_NAME));
            customer.setNic(EncoderDecoder.base64Encoding(customer.getNic()));

            CustomerModel customerModel = customerRepo.save(modelMapper.map(customer, CustomerModel.class));

            if (Objects.nonNull(customerModel.getId())) {
                CustomerResponse customerResponse = new CustomerResponse();
                customerResponse.setCustomerId(customerModel.getId());
                customerResponse.setCustomerName(customerModel.getName());
                customerResponse.setStatus("success");
                return new ResponseEntity<>(customerResponse, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setErrorCode(errorHandling.getSaveErrorCode());
                errorResponse.setErrorMessage(errorHandling.getSaveErrorMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.ACCEPTED);
            }
        } catch (NullPointerException | QueryTimeoutException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }


    }

    @Override
    @Async
    public CompletableFuture<List<CustomerModel>> getCustomerData() {

        LOGGER.info("Request to get a list of Customers");

        final List<CustomerModel> customerModels = customerRepo.findAll();
        for (CustomerModel cc : customerModels) {
            cc.setNic(EncoderDecoder.base64Decoding(cc.getNic()));
        }
        GenericDisplay.display(customerModels);
        return CompletableFuture.completedFuture(customerModels);
    }

    @Override
    public ResponseEntity<Object> getCustomerNameFilter(String customer) {
        try {
            List<Object> nameList = new ArrayList<>();
            Query query = new Query();
            query.fields().include("name").exclude("id");
            List<Object> customerNameList = mongoTemplate.find(query, CustomerModel.class).stream().map(CustomerModel::getName).collect(Collectors.toList());
            Predicate<String> nameFilter = s -> s.toUpperCase(Locale.ROOT).startsWith(customer.toUpperCase(Locale.ROOT));
            for (Object name : customerNameList) {
                // call the test method
                if (nameFilter.test(name.toString()))
                    nameList.add(name);
            }
            if (!nameList.isEmpty())
                return new ResponseEntity<>(nameList, HttpStatus.OK);
            else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setErrorCode(errorHandling.getSaveErrorCode());
                errorResponse.setErrorMessage(errorHandling.getSaveErrorMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.ACCEPTED);
            }
        } catch (NullPointerException | QueryTimeoutException e) {
            e.printStackTrace();
            return new ResponseEntity<>(errorHandling, HttpStatus.BAD_REQUEST);

        }


    }

    @Override
    public ResponseEntity<Object> getCustomerById(Long customerId) {
        if (customerRepo.existsById(customerId)) {
            Optional<CustomerModel> customerModel = customerRepo.findById(customerId);
            if (customerModel.isPresent()) {
                CustomerModel customerModel1 = customerModel.get();
                customerModel1.setNic(EncoderDecoder.base64Decoding(customerModel1.getNic()));
            } else {
                System.out.println("Invalid id");
            }

            return new ResponseEntity<>(customerModel, HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(errorHandling.getSaveErrorCode());
            errorResponse.setErrorMessage(errorHandling.getSaveErrorMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.ACCEPTED);
        }


    }

    @Override
    public CompletableFuture<List<CustomerModel>> getCustomersByAge() {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(20));
        List<CustomerModel> users = mongoTemplate.find(query,CustomerModel.class);
        Set<Integer> customerAges = customerRepo.findAll().stream().map(x -> x.getAge()).collect(Collectors.toSet());
        LOGGER.info("Customer Ages\n" + customerAges);
        return CompletableFuture.completedFuture(users);
    }
}
