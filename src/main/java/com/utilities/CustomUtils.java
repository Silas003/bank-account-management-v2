
package com.utilities;
import java.util.Scanner;

/*
 * Utility class for validating user input in the Bank Account Management System.
 *
 * Provides reusable methods for validating customer details, account types,
 * transaction types, and amounts entered via console.
 */
public class CustomUtils {

    // Maximum number of retries for invalid input
    private static final int maxRetries = 3;

    /*
     * Validates customer name input.
     * Name must not be empty and should contain only letters.
     */
    public static String validateCustomerNameInput(Scanner scanner) {
        String customerName;
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter customer name: ");
            customerName = scanner.nextLine();
            if (customerName.isBlank() || !customerName.matches("^[A-Za-z ]+$")) {
                System.out.println("Invalid Customer Name. Name must not be empty or contain numbers.");
            } else return customerName;
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Validates customer age input.
     * Age must be a positive integer.
     */
    public static int validateCustomerAgeInput(Scanner scanner) {
        int customerAge;
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter customer age: ");
            try {
                customerAge = Integer.parseInt(scanner.nextLine());
                if (customerAge > 0) return customerAge;
                System.out.println("Age must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Must be a number.");
            }
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return -1;
    }

    /*
     * Validates customer type selection.
     * Options: 1 for Regular, 2 for Premium.
     */
    public static String validateCustomerTypeInput(Scanner scanner) {
        System.out.println("1. Regular Customer (Standard banking services)\n2. Premium Customer (Enhanced benefits, min balance $10,000)");
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Select type (1-2): ");
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2")) return input;
            System.out.println("Invalid selection. Choose 1 or 2.");
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Validates account type selection.
     * Options: 1 for Savings, 2 for Checking.
     */
    public static String validateAccountTypeInput(Scanner scanner) {
        System.out.println("1. Savings Account (Interest: 3.5%, Minimum Balnance: $500)\n2. Checking Account (Overdraft: $1,000 ,Monthly Fee: $10)");
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Select type (1-2): ");
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2")) return input;
            System.out.println("Invalid selection. Choose 1 or 2.");
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Validates transaction type selection.
     * Options: 1 for Deposit, 2 for Withdrawal.
     */
    public static String validateTransactionTypeInput(Scanner scanner) {
        System.out.println("1. Deposit\n2. Withdrawal");
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Select type (1-2): ");
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2")) return input;
            System.out.println("Invalid selection. Choose 1 or 2.");
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Validates initial deposit amount based on customer and account type.
     * Premium customers require at least $10,000; Savings accounts require at least $500.
     */
    public static double validateInitialDepositInput(Scanner scanner, String customerType, String accountType) {
        final int premiumDeposit = 10000;
        final int savingsDeposit = 500;
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter initial deposit amount: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount < 0) {
                    System.out.println("Deposit cannot be negative.");
                } else if (customerType.equals("2") && amount < premiumDeposit) {
                    System.out.println("Premium customers require a minimum deposit of $10,000.");
                } else if (accountType.equals("1") && amount < savingsDeposit) {
                    System.out.println("Savings account requires a minimum deposit of $500.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Must be a number.");
            }
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return -1;
    }

    /*
     * Validates transaction amount.
     * Amount must be positive.
     */
    public static double validateTransactionAmount(Scanner scanner) {
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter amount: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount < 0) {
                    System.out.println("Amount cannot be negative.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Must be a number.");
            }
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return -1;
    }

    /*
     * Validates customer contact input.
     * Must be 10 digits and contain only numbers.
     */
    public static String validateCustomerContactInput(Scanner scanner) {
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter customer contact: ");
            String contact = scanner.nextLine();
            if (!contact.isBlank() && contact.matches("^[0-9]+$") && contact.length() == 10) return contact;
            System.out.println("Invalid contact. Must contain only digits and be 10 digits long.");
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Validates customer address input.
     * Address must not be empty.
     */
    public static String validateCustomerAddressInput(Scanner scanner) {
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter customer address: ");
            String address = scanner.nextLine();
            if (!address.isBlank()) return address;
            System.out.println("Invalid address. Cannot be empty.");
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Confirms transaction with user (Y/N).
     */
    public static String validateTransactionConfirmation(Scanner scanner) {
        for (int i = 0; i < 2; i++) {
            System.out.print("Confirm transaction? (Y/N): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) return input;
            System.out.println("Invalid selection. Choose Y or N.");
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Validates account number format.
     * Must match pattern ACC00 followed by digits.
     */
    public static String validateAccountNumberInput(Scanner scanner) {
        String accountNumber;
        for (int i = 0; i < maxRetries; i++) {
            System.out.print("Enter Account Number: ");
            accountNumber = scanner.nextLine();
            if (accountNumber.isBlank() || !accountNumber.matches("(?i)^ACC00\\d{1,}$")) {
                System.out.println("Invalid Account Number provided. Example: ACC004");
            } else return accountNumber;
        }
        System.out.println("Too many invalid attempts. Returning to main menu.");
        return null;
    }

    /*
     * Prompts the user to press Enter to continue.
     */
    public static void promptEnterKey(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
