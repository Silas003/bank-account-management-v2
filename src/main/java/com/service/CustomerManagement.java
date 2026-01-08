package com.service;
import com.models.Account;
import com.models.Customer;
import com.models.exceptions.InvalidAccountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerManagement {

    private static List<Customer> customers = new ArrayList<>();

    public static void addCustomer(Customer customer){
        customers.add(customer);
    }

    public List<Customer> getAllCustomers(){
        return customers;
    }
    public static int getCustomerSize(){
        return  customers.size();
    }

    public static Customer findCustomerById(String customerId) throws InvalidAccountException {
        return customers.stream().filter(cstm -> cstm.getCustomerId().equalsIgnoreCase(customerId))
                .findFirst()
                .orElse(null);
    }
}
