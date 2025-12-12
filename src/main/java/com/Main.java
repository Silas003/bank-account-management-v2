package com;
import com.handlers.AppHandler;
import com.models.*;
import com.service.AccountManagement;
import com.service.CustomerManagement;
import com.service.FilePersistenceService;
import com.utilities.ValidationUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the Bank Account Management System.
 */
public class Main {
    static Customer customer;
    static Account account;
    public static void main(String[] args) throws IOException {
        Double balance;
        ArrayList<List> ct = FilePersistenceService.readFromFile("customer");
        for (List<String> row : ct) {
            if(row.size()>=5) {
                System.out.println(row);
                if(row.get(5).equalsIgnoreCase("Regular")){
                    customer = new RegularCustomer(
                            row.get(0),
                            row.get(1),
                            Integer.parseInt(row.get(2)),
                            row.get(3),
                            row.get(4),
                            row.get(5));
//
                }else {
                    customer = new PremiumCustomer(
                            row.get(0),
                            row.get(1),
                            Integer.parseInt(row.get(2)),
                            row.get(3),
                            row.get(4),
                            row.get(5)
                    );
                }
                CustomerManagement.addCustomer(customer);
            }
        }
        Customer.customerCounter = ct.size();
        ArrayList<List> rd = FilePersistenceService.readFromFile("account");
        Account.accountCounter = rd.size();
        for (List<String> row : rd) {
            if(row.size() >=3) {
                Customer customer1 = CustomerManagement.findCustomerById(row.get(1).trim());
                System.out.println(row.get(2));
                balance = ValidationUtils.parseMoney(row.get(3));
                if(row.get(2).equalsIgnoreCase("Savings")){
                    account = new SavingsAccount(row.get(0).trim(),customer1,balance);
                }else {
                    account = new CheckingAccount(row.get(0).trim(),customer1,balance);
                }
                }

            AccountManagement.addAccount(account);
            }
        AppHandler app = new AppHandler();
        app.start();
    }
}
