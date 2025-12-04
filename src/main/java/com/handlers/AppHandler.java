package com.handlers;

import com.service.*;

import java.util.Scanner;

public class AppHandler {

    private final AccountService accountService;
    private final TransactionServices transactionService;
    private final CustomerService customerService;
    private final AccountManagement accountManagement = new AccountManagement();

    private final TransactionManagement transactionManagement = new TransactionManagement();

    private final CustomerManagement customerManagement = new CustomerManagement();

    private final Scanner scanner = new Scanner(System.in);

    public AppHandler() {
        accountService = new AccountService(accountManagement, transactionManagement,customerManagement,scanner);
        transactionService = new TransactionServices(accountManagement, transactionManagement, scanner);
        customerService = new CustomerService(customerManagement,scanner);
    }


    public void start() {
        boolean running = true;
        System.out.println("||====================================||");
        System.out.println("  BANK ACCOUNT MANAGEMENT - MAIN MENU");
        System.out.println("||====================================||");
        while (running) {
            System.out.println("\n1. Create Account \n2. View Customers \n3. View Accounts \n4. Process Transaction \n5. View Transaction History \n6. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            running = switch (choice) {
                case "1" -> { accountService.createAccount(); yield true; }
                case "2" -> { customerService.viewAllCustomers();yield true;}
                case "3" -> { accountService.viewAllAccounts(); yield true; }
                case "4" -> { transactionService.processTransaction(); yield true; }
                case "5" -> { transactionService.viewTransactionHistory(); yield true; }
                case "6" -> { System.out.println("Thank you for using Bank Account Management System!\nGoodbye!"); yield false; }
                default -> { System.out.println("Please select a number between [1-6]"); yield true; }
            };
        }
    }
}
