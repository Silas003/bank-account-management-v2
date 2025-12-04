package com.service;

import com.models.Customer;

import java.util.ArrayList;

public class CustomerManagement {

    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public ArrayList<Customer> getAllCustomers(){
        return customers;
    }
    public int getCustomerSize(){
        return  customers.size();
    }
}
