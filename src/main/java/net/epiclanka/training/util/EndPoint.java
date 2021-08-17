package net.epiclanka.training.util;

public class EndPoint {
    public static final String ADD_CUSTOMER_DATA = "/addCustomer/";
    public static final String GET_CUSTOMER_DATA = "/getCustomerData/";
    public static final String GET_ONE_CUSTOMER = "/getCustomerData/{customerId}";
    public static final String GET_CUSTOMER_NAME = "/getCustomerName={customerName}";
    public static final String GET_OVER_TWENTY = "/getOverTwenty";
    public static final String UPDATE_CUSTOMER = "/updateCustomer/{customerId}";
    public static final String DELETE_CUSTOMER = "/deleteCustomer/{customerId}";
}
