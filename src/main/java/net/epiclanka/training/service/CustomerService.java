package net.epiclanka.training.service;

import net.epiclanka.training.model.Customer;
import net.epiclanka.training.model.CustomerModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerService {
    public ResponseEntity<Object> addCustomerData(Customer customer);
    public CompletableFuture<List<CustomerModel>> getCustomerData();
    public ResponseEntity<Object> getCustomerById(Long customerId);
    public ResponseEntity<Object> getCustomerNameFilter(String customerName);
    public CompletableFuture<List<CustomerModel>> getCustomersByAge();
    public ResponseEntity<Object> updateCustomer(Long customerId, Customer customer);
    public ResponseEntity<Object> deleteCustomer(Long customerId);
}