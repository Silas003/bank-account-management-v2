package com.handlers;
import com.CustomRunner;
import com.service.*;
import java.io.IOException;
import com.utilities.ConcurrencyUtils;
import java.util.Scanner;

/**
 * Application handler that manages the main menu and user interaction flow.
 */

public class AppHandler {

    private final AccountService accountService;
    private final TransactionServices transactionService;
    private final CustomerService customerService;
    private final AccountManagement accountManagement = new AccountManagement();
    private final TransactionManagement transactionManagement = new TransactionManagement();
    private final CustomerManagement customerManagement = new CustomerManagement();
    private final Scanner scanner = new Scanner(System.in);
    private final SubmenuHandler submenuHandler;
    public AppHandler() {
        accountService = new AccountService(accountManagement, transactionManagement,customerManagement,scanner);
        transactionService = new TransactionServices(accountManagement, transactionManagement, scanner);
        customerService = new CustomerService(customerManagement,scanner);
        submenuHandler = new SubmenuHandler(scanner, accountService, transactionService, customerService);

    }

    public void start() throws IOException {
        FilePersistenceService.loadAllDataFromFile();
        boolean running = true;
        System.out.println("||====================================||");
        System.out.println("  BANK ACCOUNT MANAGEMENT - MAIN MENU");
        System.out.println("||====================================||");
        while (running) {

            System.out.println("\n1. Manage Accounts");
            System.out.println("2. Perform Transaction");
            System.out.println("3. Generate Account Statements");
            System.out.println("4. Run Tests");
            System.out.println("5. Load Data");
            System.out.println("6. Run concurrent Simulation");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            running = switch (choice) {

                case "1" -> { 
                    submenuHandler.manageAccounts(); 
                    yield true; 
                }
                case "2" -> { 
                    submenuHandler.performTransaction(); 
                    yield true; 
                }
                case "3" -> { 
                    transactionService.viewTransactionHistory(); 
                    yield true; 
                }
                case "4" -> {
                    CustomRunner.runAllTestsInPackage();
                    yield true;
                }
                case "5" -> {
                    FilePersistenceService.loadAllDataFromFile();
                    yield true;
                }
                case "6" -> {
                    ConcurrencyUtils.simulateConcurrentTransactions();
                    yield true;
                }
                case "7" -> {
                    System.out.println("Thank you for using Bank Account Management System!" +
                            "\nData automatically saved to disk.\nGoodbye!");
                    yield false;
                }
                default -> { 
                    System.out.println("Please select a number between [1-6]");
                    yield true; 
                }

            };
        }
    }
}
