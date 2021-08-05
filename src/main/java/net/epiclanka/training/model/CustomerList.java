package net.epiclanka.training.model;

import java.util.List;

public class CustomerList {
    private List<CustomerModel> customerModels;

    public CustomerList() {
    }

    public List<CustomerModel> getCustomerModels() {
        return customerModels;
    }

    public void setCustomerModels(List<CustomerModel> customerModels) {
        this.customerModels = customerModels;
    }
}
