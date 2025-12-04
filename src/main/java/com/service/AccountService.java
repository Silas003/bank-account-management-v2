
package com.service;

import com.exceptions.CustomExceptions;
import com.models.*;
import com.utilities.CustomUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Service layer for account-related business operations and user interactions.
// Provides high-level account management functionality, coordinating between
// the user interface (via Scanner) and the data management layer (AccountManagement).
// Handles the complete account creation workflow and account listing operations.
//
// Key responsibilities:
// - Account creation with customer information collection
// - Account listing and display
// - User input validation coordination
// - Customer and account type selection
//
// The service validates all user inputs through utility methods before proceeding
// with account creation, ensuring data integrity and providing user-friendly error messages.
public class AccountService {

    // Data management layer for account operations
    private final AccountManagement accountManagement;
    private final TransactionManagement transactionManagement;
    private  final CustomerManagement customerManagement;
    // Scanner instance for reading user input
    private final Scanner scanner;

    // Constructs a new AccountService with the provided dependencies.
    public AccountService(AccountManagement accountManagement,TransactionManagement transactionManagement,CustomerManagement customerManagement ,Scanner scanner) {
        this.accountManagement = accountManagement;
        this.transactionManagement = transactionManagement;
        this.scanner = scanner;
        this.customerManagement = customerManagement;
    }

    // Guides the user through the account creation process.
    // Workflow:
    // 1. Collect and validate customer information (name, age, contact, address)
    // 2. Prompt for customer type (Regular or Premium)
    // 3. Prompt for account type (Savings or Checking)
    // 4. Validate initial deposit amount based on requirements
    // 5. Create Customer and Account objects
    // 6. Add account to the system and display confirmation
    public void createAccount() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String customerName, customerAddress, customerContact;
        int customerAge;

        System.out.println("ACCOUNT CREATION");
        System.out.println("====================================");
        try{
            customerName = CustomUtils.validateCustomerNameInput(scanner);
            customerAge = CustomUtils.validateCustomerAgeInput(scanner);
            customerContact = CustomUtils.validateCustomerContactInput(scanner);
            customerAddress = CustomUtils.validateCustomerAddressInput(scanner);
            String customerTypeInput = CustomUtils.validateCustomerTypeInput(scanner);
            String accounTypeInput = CustomUtils.validateAccountTypeInput(scanner);
            double initialDepositAmount = CustomUtils.validateInitialDepositInput(scanner, customerTypeInput, accounTypeInput);

            // Determine customer type
            Customer customer;
            customer = customerTypeInput.equals("1") ? new RegularCustomer(customerName, customerAge, customerContact, customerAddress, "Regular") :
                    new PremiumCustomer(customerName, customerAge, customerContact, customerAddress,"Premium");

            Account newAccount;
            newAccount = accounTypeInput.equals("1") ? new SavingsAccount(customer, initialDepositAmount) : new CheckingAccount(customer, initialDepositAmount);

            // Add account and display confirmation
            customerManagement.addCustomer(customer);
            accountManagement.addAccount(newAccount);
            String dateTime = LocalDateTime.now().format(formatter);
            transactionManagement.addTransaction(new Transaction(newAccount.getAccountNumber(),
                        "Deposit",
                        initialDepositAmount,
                        initialDepositAmount,
                        dateTime));

            System.out.println("Account created successfully!");
            System.out.println(newAccount.displayAccountDetails());

            CustomUtils.promptEnterKey(scanner);

        } catch (CustomExceptions.CustomerNameException | CustomExceptions.CustomerAddressException | CustomExceptions.CustomerAgeException |
                 CustomExceptions.TypeSelectionException | CustomExceptions.CustomerContactException |CustomExceptions.IllegalAmountException ce){
            System.out.println(ce.getMessage());
            CustomUtils.promptEnterKey(scanner);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Displays a formatted listing of all accounts in the system.
    // Shows account number, customer name, account type, balance, status, and account-specific details.
    // Also displays summary info: total accounts and total balance.
    public void viewAllAccounts() {
        System.out.println("ACCOUNT LISTING");
        System.out.println("====================================================");
        System.out.println("ACC NO | CUSTOMER NAME | TYPE | BALANCE | STATUS");
        System.out.println("====================================================");

        Account[] allAccounts = accountManagement.viewAllAccounts();
        if(allAccounts.length == 0){
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
