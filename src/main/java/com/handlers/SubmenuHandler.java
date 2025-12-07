package com.handlers;

import com.service.*;

import java.util.Scanner;

/**
 * Handler class for managing submenu displays and user interactions.
 */
public class SubmenuHandler {

    private final Scanner scanner;
    private final AccountService accountService;
    private final TransactionServices transactionService;
    private final CustomerService customerService;

    public SubmenuHandler(Scanner scanner, AccountService accountService, 
                         TransactionServices transactionService, CustomerService customerService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    public void manageAccounts() {
        boolean backToMain = false;
        System.out.println("\n||=================||");
        System.out.println("  MANAGE ACCOUNTS");
        System.out.println("||=================||");
        
        while (!backToMain) {
            System.out.println("\n1. Create Account");
            System.out.println("2. View Customers");
            System.out.println("3. View Accounts");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            backToMain = switch (choice) {
                case "1" -> { 
                    accountService.createAccount(); 
                    yield false; 
                }
                case "2" -> { 
                    customerService.viewAllCustomers(); 
                    yield false; 
                }
                case "3" -> { 
                    accountService.viewAllAccounts(); 
                    yield false; 
                }
                case "4" -> {
                    System.out.println("Returning to main menu...");
                    yield true;
                }
                default -> { 
                    System.out.println("Please select a number between [1-4]"); 
                    yield false; 
                }
            };
        }
    }

    public void performTransaction() {
        boolean backToMain = false;
        System.out.println("\n||====================||");
        System.out.println("  PERFORM TRANSACTION");
        System.out.println("||====================||");
        
        while (!backToMain) {
            System.out.println("\n1. Deposit/Withdrawal");
            System.out.println("2. Transfer to Other Accounts");
            System.out.println("3. Return to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            backToMain = switch (choice) {
                case "1" -> { 
                    transactionService.processDepositWithdrawal(); 
                    yield false; 
                }
                case "2" -> { 
                    transactionService.transferToOtherAccounts(); 
                    yield false; 
                }
                case "3" -> {
                    System.out.println("Returning to main menu...");
                    yield true;
                }
                default -> { 
                    System.out.println("Please select a number between [1-3]"); 
                    yield false; 
                }
            };
        }
    }
}

