package com.service;
import com.models.Account;
import com.models.Customer;
import com.models.exceptions.InvalidAccountException;

import java.util.ArrayList;
import java.util.Optional;

public class CustomerManagement {

    private static ArrayList<Customer> customers = new ArrayList<>();

    public static void addCustomer(Customer customer){
        customers.add(customer);
    }

    public ArrayList<Customer> getAllCustomers(){
        return customers;
    }
    public static int getCustomerSize(){
        return  customers.size();
    }

    public static Customer findCustomerById(String customerId) throws InvalidAccountException {
        for(Customer customer:customers){
//            System.out.println(customer.displayCustomerDetails());
            if(customer.getCustomerId().equals(customerId)) return customer;
//            throw new InvalidAccountException("Account Number not found.Returning to main menu");
        }
        return null;
    }
}
