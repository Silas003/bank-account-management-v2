package com.service;

import com.models.Customer;
import com.utilities.CustomUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerService {
    private final CustomerManagement customerManagement;
    private final Scanner scanner;
    public CustomerService(CustomerManagement customerManagement,Scanner scanner){
        this.customerManagement = customerManagement;
        this.scanner = scanner;
    }


    public void viewAllCustomers(){
        ArrayList<Customer> customers = customerManagement.getAllCustomers();
        if (customers.size() == 0) {
            System.out.println("No Customer in system.Returning to Main menu");
            CustomUtils.promptEnterKey(scanner);
            return;
        }

        System.out.println("CUSTOMERS");
        System.out.println("======================================================");
        System.out.println("CUSTOMER ID | NAME    | TYPE  | CONTACT    | ADDRESS");

        for (Customer customer : customers) {
            System.out.printf("%s",customer.displayCustomerDetails());
        }

        System.out.println("========================================================\n");
        System.out.println("Total Customers: " + customerManagement.getCustomerSize());
        CustomUtils.promptEnterKey(scanner);
    }

}
