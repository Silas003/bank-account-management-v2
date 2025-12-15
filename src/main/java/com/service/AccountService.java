
package com.service;
import com.models.*;
import com.models.exceptions.*;
import com.utilities.ValidationUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

        try{
            customerName = ValidationUtils.validateCustomerNameInput(scanner);
            customerAge = ValidationUtils.validateCustomerAgeInput(scanner);
            customerContact = ValidationUtils.validateCustomerContactInput(scanner);
            customerAddress = ValidationUtils.validateCustomerAddressInput(scanner);
            String customerTypeInput = ValidationUtils.validateCustomerTypeInput(scanner);
            String accounTypeInput = ValidationUtils.validateAccountTypeInput(scanner);
            double initialDepositAmount = ValidationUtils.validateInitialDepositInput(scanner, customerTypeInput, accounTypeInput);


            Customer customer;
            customer = customerTypeInput.equals("1") ? new RegularCustomer(customerName, customerAge, customerContact, customerAddress, "Regular") :
                    new PremiumCustomer(customerName, customerAge, customerContact, customerAddress,"Premium");

            Account newAccount;
            newAccount = accounTypeInput.equals("1") ? new SavingsAccount(customer, initialDepositAmount) : new CheckingAccount(customer, initialDepositAmount);

            customerManagement.addCustomer(customer);
            FilePersistenceService.writeToCustomerFile("customer",customer);
            accountManagement.addAccount(newAccount);
            String dateTime = LocalDateTime.now().format(formatter);
            Transaction transaction = new Transaction(newAccount.getAccountNumber(),
                    "Deposit",
                    initialDepositAmount,
                    initialDepositAmount,
                    dateTime);
            transactionManagement.addTransaction(transaction);

            System.out.println("Account created successfully!");
            FilePersistenceService.writeToTransactionFile("transaction",transaction);
            FilePersistenceService.writeToAccountFile("account",newAccount);
            System.out.println(newAccount.displayAccountDetails());
            ValidationUtils.promptEnterKey(scanner);

        } catch (CustomerNameException | CustomerAddressException | CustomerAgeException |
                 TypeSelectionException | CustomerContactException | IllegalAmountException ce){
            System.out.println(ce.getMessage());
            ValidationUtils.promptEnterKey(scanner);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public void viewAllAccounts() throws IOException {

        if(accountManagement.getAccountCount() <= 0){
            System.out.println("No Account In System.Returning to Main menu");
            ValidationUtils.promptEnterKey(scanner);
            return;
        }

        System.out.println("ACCOUNT LISTING");
        System.out.println("====================================================");
        System.out.println("ACC NO | CUSTOMER NAME | TYPE | BALANCE | STATUS");
        System.out.println("====================================================");

        ArrayList<Account> allAccounts = accountManagement.viewAllAccounts();

        for (int i = 0; i < accountManagement.getAccountCount(); i++) {

            Account account = allAccounts.get(i);
            System.out.printf("%s | %s | %s | $%.2f | %s | %s\n",
                    account.getAccountNumber(),
                    account.getCustomer(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.getStatus(),
                    account.getAccountSpecificDetails());
        }

        ValidationUtils.promptEnterKey(scanner);

    }
}
