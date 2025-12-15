package com;
import com.handlers.AppHandler;
import com.models.*;
import com.service.AccountManagement;
import com.service.CustomerManagement;
import com.service.FilePersistenceService;
import com.service.TransactionManagement;
import com.utilities.ValidationUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the Bank Account Management System.
 */
public class Main {
    static Customer customer;
    static Account account;
    static Transaction transaction;
    public static void main(String[] args) throws IOException {
        Double balance;
        Double amount;
        Double balanceAfter;
        ArrayList<List> ct = FilePersistenceService.readFromFile("customer");
        for (List<String> row : ct) {
            if(row.size()>=5) {
                if(row.get(5).equalsIgnoreCase("Regular")){
                    customer = new RegularCustomer(
                            row.get(0),
                            row.get(1),
                            Integer.parseInt(row.get(2)),
                            row.get(3),
                            row.get(4),
                            row.get(5));

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
                balance = ValidationUtils.parseMoney(row.get(3));
                if(row.get(2).equalsIgnoreCase("Savings")){
                    account = new SavingsAccount(row.get(0).trim(),customer1,balance);
                }else {
                    account = new CheckingAccount(row.get(0).trim(),customer1,balance);
                }
                }

            AccountManagement.addAccount(account);
            }
        ArrayList<List> tr = FilePersistenceService.readFromFile("transaction");
        Transaction.transactionCounter = tr.size();
        for (List<String> row : tr) {
            if(row.size() >=3) {
                Account account1 = AccountManagement.findAccount(row.get(1).trim());
                amount = ValidationUtils.parseMoney(row.get(3));
                balanceAfter = ValidationUtils.parseMoney(row.get(4));
                transaction = new Transaction(row.get(0), row.get(1).trim(), row.get(2), amount,balanceAfter,row.get(5));
            }

            TransactionManagement.addTransaction(transaction);
        }
        AppHandler app = new AppHandler();
        app.start();
    }
}
