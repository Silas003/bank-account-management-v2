import com.handlers.AppHandler;

/*
 * Main entry point for the Bank Account Management System.
 *
 * This application provides a console-based interface for managing bank accounts,
 * including account creation, transaction processing, and transaction history viewing.
 * The system supports both Savings and Checking accounts for Regular and Premium customers.
 *
 * Features:
 * - Account creation with customer information
 * - Account listing and viewing
 * - Deposit and withdrawal transactions
 * - Transaction history tracking
 */

public class Main {

    /*
     * Main method that initializes and starts the Bank Account Management application.
     *
     * Displays the welcome banner and creates an AppHandler instance to manage
     * the application's main menu and user interactions.
     */
    public static void main(String[] args) {
        AppHandler app = new AppHandler();
        app.start();
    }
}
