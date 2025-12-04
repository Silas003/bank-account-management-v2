
package com.service;

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

    // Scanner instance for reading user input
    private final Scanner scanner;

    // Constructs a new AccountService with the provided dependencies.
    public AccountService(AccountManagement accountManagement,TransactionManagement transactionManagement ,Scanner scanner) {
        this.accountManagement = accountManagement;
        this.transactionManagement = transactionManagement;
        this.scanner = scanner;
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

        // Determine customer type
        Customer customer;
        switch (customerTypeInput) {
            case "1":
                customer = new RegularCustomer(customerName, customerAge, customerContact, customerAddress);
                break;
            case "2":
                customer = new PremiumCustomer(customerName, customerAge, customerContact, customerAddress);
                break;
            default:
                System.out.println("Invalid customer type. Account creation aborted.");
                return;
        }

        // Determine account type
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

        // Add account and display confirmation
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

    // Displays a formatted listing of all accounts in the system.
    // Shows account number, customer name, account type, balance, status, and account-specific details.
    // Also displays summary info: total accounts and total balance.
    public void viewAllAccounts() {
        System.out.println("ACCOUNT LISTING");
        System.out.println("====================================================");
        System.out.println("ACC NO | CUSTOMER NAME | TYPE | BALANCE | STATUS");
        System.out.println("====================================================");

        Account[] allAccounts = accountManagement.viewAllAccounts();
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
