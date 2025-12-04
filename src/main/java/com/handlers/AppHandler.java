package com.handlers;

import com.service.AccountManagement;
import com.service.AccountService;
import com.service.TransactionManagement;
import com.service.TransactionServices;

import java.util.Scanner;

/*
 * Application handler that manages the main menu and user interaction flow.
 *
 * This class serves as the primary controller for the Bank Account Management System,
 * coordinating between the account and transaction services to provide a unified
 * user interface. It maintains the main application loop and routes user selections
 * to the appropriate service methods.
 *
 * The handler supports the following operations:
 * - Account creation
 * - Account viewing
 * - Transaction processing
 * - Transaction history viewing
 */
public class AppHandler {

    // Service for handling account-related operations
    private final AccountService accountService;

    // Service for handling transaction-related operations
    private final TransactionServices transactionService;

    // Management layer for account data operations
    private final AccountManagement accountManagement = new AccountManagement();

    // Management layer for transaction data operations
    private final TransactionManagement transactionManagement = new TransactionManagement();

    // Scanner instance for reading user input from console
    private final Scanner scanner = new Scanner(System.in);

    /*
     * Constructs a new AppHandler.
     *
     * Initializes the account and transaction services with their respective
     * management dependencies and the shared scanner for user input.
     */
    public AppHandler() {
        accountService = new AccountService(accountManagement, transactionManagement,scanner);
        transactionService = new TransactionServices(accountManagement, transactionManagement, scanner);
    }

    /*
     * Starts the main application loop and displays the interactive menu.
     *
     * Continuously displays the main menu options and processes user selections
     * until the user chooses to exit. Each menu option delegates to the appropriate
     * service method for execution.
     *
     * Menu options:
     * 1. Create Account - Opens account creation wizard
     * 2. View Accounts - Displays all accounts in the system
     * 3. Process Transaction - Handles deposits and withdrawals
     * 4. View Transaction History - Shows transaction history for an account
     * 5. Exit - Terminates the application
     */
    public void start() {
        boolean running = true;
        System.out.println("||====================================||");
        System.out.println("  BANK ACCOUNT MANAGEMENT - MAIN MENU");
        System.out.println("||====================================||");
        while (running) {
            System.out.println("\n1. Create Account \n2. View Accounts \n3. Process Transaction \n4. View Transaction History \n5. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            running = switch (choice) {
                case "1" -> { accountService.createAccount(); yield true; }
                case "2" -> { accountService.viewAllAccounts(); yield true; }
                case "3" -> { transactionService.processTransaction(); yield true; }
                case "4" -> { transactionService.viewTransactionHistory(); yield true; }
                case "5" -> { System.out.println("Thank you for using Bank Account Management System!\nGoodbye!"); yield false; }
                default -> { System.out.println("Please select a number between [1-5]"); yield true; }
            };
        }
    }
}
