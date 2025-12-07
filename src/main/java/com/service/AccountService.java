package com.service;

import com.models.*;
import com.utilities.CustomUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Service layer for account-related business operations and user interactions.
 */
public class AccountService {

    private final AccountManagement accountManagement;
    private final TransactionManagement transactionManagement;
    private final CustomerManagement customerManagement;
    private final Scanner scanner;

    public AccountService(AccountManagement accountManagement,TransactionManagement transactionManagement,CustomerManagement customerManagement ,Scanner scanner) {
        this.accountManagement = accountManagement;
        this.transactionManagement = transactionManagement;
        this.scanner = scanner;
        this.customerManagement = customerManagement;
    }

    public void createAccount() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String customerName, customerAddress, customerContact;
        int customerAge;

        System.out.println("ACCOUNT CREATION");
        System.out.println("====================================");
        customerName = CustomUtils.validateCustomerNameInput(scanner);
        if (customerName == null) return;
        customerAge = CustomUtils.validateCustomerAgeInput(scanner);
        if (customerAge == -1) return;
        customerContact = CustomUtils.validateCustomerContactInput(scanner);
        if (customerContact == null) return;
        customerAddress = CustomUtils.validateCustomerAddressInput(scanner);
        if (customerAddress == null) return;
        String customerTypeInput = CustomUtils.validateCustomerTypeInput(scanner);
        if (customerTypeInput == null) return;
        String accounTypeInput = CustomUtils.validateAccountTypeInput(scanner);
        if (accounTypeInput == null) return;

        double initialDepositAmount = CustomUtils.validateInitialDepositInput(scanner, customerTypeInput, accounTypeInput);

        Customer customer;
        switch (customerTypeInput) {
            case "1":
                customer = new RegularCustomer(customerName, customerAge, customerContact, customerAddress, "Regular");
                break;
            case "2":
                customer = new PremiumCustomer(customerName, customerAge, customerContact, customerAddress,"Premium");
                break;
            default:
                System.out.println("Invalid customer type. Account creation aborted.");
                return;
        }

        Account newAccount;
        switch (accounTypeInput) {
            case "1":
                newAccount = new SavingsAccount(customer, initialDepositAmount);
                break;
            case "2":
                newAccount = new CheckingAccount(customer, initialDepositAmount);
                break;
            default:
                System.out.println("Invalid account type. Please select 1 or 2.");
                return;
        }

        customerManagement.addCustomer(customer);
        accountManagement.addAccount(newAccount);
        String dateTime = LocalDateTime.now().format(formatter);
        boolean success = newAccount.processTransactions(initialDepositAmount, "Deposit");
        if (success) {
            transactionManagement.addTransaction(new Transaction(newAccount.getAccountNumber(),
                    "Deposit",
                    initialDepositAmount,
                    initialDepositAmount,
                    dateTime));

        } else {
            System.out.println("Transaction failed! Check balance or account rules.");
        }
        System.out.println("Account created successfully!");
        System.out.println(newAccount.displayAccountDetails());

        CustomUtils.promptEnterKey(scanner);
    }

    public void viewAllAccounts() {
        System.out.println("ACCOUNT LISTING");
        System.out.println("====================================================");
        System.out.println("ACC NO | CUSTOMER NAME | TYPE | BALANCE | STATUS");
        System.out.println("====================================================");

        Account[] allAccounts = accountManagement.viewAllAccounts();
        if(allAccounts.length ==0){
            System.out.println("No Account In System.Returning to Main menu");
            CustomUtils.promptEnterKey(scanner);
            return;
        }
        for (int i = 0; i < accountManagement.accountCount; i++) {
            Account account = allAccounts[i];
            System.out.printf("%s | %s | %s | $%.2f | %s | %s\n",
                    account.getAccountNumber(),
                    account.getCustomer(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.getStatus(),
                    account.getAccountSpecificDetails());
        }

        System.out.printf("Total Accounts: %d\nTotal Bank Balance: $%.2f\n",
                accountManagement.getAccountCount(), accountManagement.getTotalBalance());
        CustomUtils.promptEnterKey(scanner);
    }
}
