package net.epiclanka.training.controller;

import org.apache.log4j.Logger;

import net.epiclanka.training.dto.CustomerDto;
import net.epiclanka.training.model.Customer;
import net.epiclanka.training.model.CustomerModel;
import net.epiclanka.training.service.CustomerService;
import net.epiclanka.training.util.EndPoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


@RestController
@RequestMapping("/")
public class CustomerController {
    private CustomerService customerService;
    private ModelMapper modelMapper;

    private static final Logger LOGGER = Logger.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;

    }

    @PostMapping(value = EndPoint.ADD_CUSTOMER_DATA, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCustomerData(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customerService.addCustomerData(customer);
    }

    @GetMapping(value = EndPoint.GET_CUSTOMER_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity> getCustomerData() {

        return customerService.getCustomerData()
                .<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleCustomerDataFailure);
    }

    @GetMapping(value = EndPoint.GET_CUSTOMER_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCustomerNameFilter(@PathVariable String customerName) {

        return customerService.getCustomerNameFilter(customerName);
    }

    @GetMapping(value = EndPoint.GET_ONE_CUSTOMER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping(value = EndPoint.GET_OVER_TWENTY, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity> getCustomersByAge()
    {
        return customerService.getCustomersByAge()
                .<ResponseEntity>thenApply(ResponseEntity::ok)
            .exceptionally(handleCustomerDataFailure);
    }
    @PutMapping(value = EndPoint.UPDATE_CUSTOMER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerDto customerDto)
    {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customerService.updateCustomer(customerId, customer);
    }
    @DeleteMapping(value = EndPoint.DELETE_CUSTOMER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId)
    {
        return customerService.deleteCustomer(customerId);
    }

    private static Function<Throwable, ResponseEntity<? extends List<CustomerModel>>> handleCustomerDataFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
}
