
package com.models;

import com.exceptions.CustomExceptions;

/*
 * Abstract class representing a bank account.
 *
 * This class provides common properties and behaviors for all account types,
 * such as account number, customer details, balance, and status.
 *
 * Subclasses include:
 * - SavingsAccount
 * - CheckingAccount
 * - Customer
 */
public abstract class Account implements Transactable {

    // Unique identifier for the account in format "ACC00" + counter
    private String accountNumber;

    // The customer who owns this account
    private Customer customer;

    // Current account balance in dollars
    private double balance;

    // Account status (e.g., "active", "closed", "suspended")
    private String status;

    // Static counter for generating unique account numbers
    public static int accountCounter;

    /*
     * Default constructor that initializes the account and generates a unique account number.
     * Increments the static account counter to ensure each account receives a unique identifier.
     */
    Account() {
        setAccountNumber();

    }

    /*
     * Returns a formatted string containing all account details for display.
     * Subclasses must implement this method to provide a comprehensive view of the account.
     */
    public abstract String displayAccountDetails();

    /*
     * Returns the type of account (e.g., "Savings", "Checking").
     */
    public abstract String getAccountType();

    /*
     * Returns account-specific details that vary by account type.
     * For Savings accounts: interest rate, minimum balance.
     * For Checking accounts: overdraft limit, monthly fees.
     */
    public abstract String getAccountSpecificDetails();

    /*
     * Generates a unique account number based on the current account counter.
     * Format: "ACC00" followed by the account counter value.
     */
    private void generateAccountNumber() {
        this.accountNumber = "ACC00" + ++accountCounter;
    }

    // Retrieves the account number
    public String getAccountNumber() {
        return this.accountNumber;
    }

    // Sets the account number by generating a new one
    public void setAccountNumber() {
        generateAccountNumber();
    }

    // Returns a string representation of the customer associated with this account
    public String getCustomer() {
        return this.customer.toString();
    }

    // Gets the current account counter value
    public int getAccountCounter() {
        return accountCounter;
    }

    // Associates a customer with this account
    public void setCustomer(Customer Customer) {
        this.customer = Customer;
    }

    // Retrieves the current account balance
    public double getBalance() {
        return this.balance;
    }

    // Sets the account balance to the specified value
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Retrieves the current account status
    public String getStatus() {
        return this.status;
    }

    // Sets the account status (e.g., "active", "closed", "suspended")
    public void setStatus(String status) {
        this.status = status;
    }

    /*
     * Processes a deposit transaction.
     * Validates that the deposit amount is positive before adding it to the account balance.
     * Returns true if successful, false otherwise.
     */
    public abstract boolean deposit(double amount) throws CustomExceptions.IllegalAmountException;

    /*
     * Abstract method for processing withdrawal transactions.
     * Subclasses must implement account-specific withdrawal logic.
     */
    public abstract void withdraw (double amount)throws CustomExceptions.IllegalAmountException, CustomExceptions.InsufficientFundsExceptions;

    /*
     * Returns a string representation of the account.
     * Includes customer name, account type, and current balance.
     */
    @Override
    public String toString() {
        return String.format("Customer: %s\nAccount Type:%s\nCurrent Balance: %.2f",
                getCustomer(), getAccountType(), getBalance());
    }

    /*
     * Processes a transaction based on the specified type.
     * Routes to deposit or withdraw based on transaction type.
     */
    public boolean processTransactions(double amount, String type) throws CustomExceptions.IllegalAmountException, CustomExceptions.InsufficientFundsExceptions {
        if (type.equalsIgnoreCase("Deposit")) {
            return deposit(amount);
        } else if (type.equalsIgnoreCase("Withdrawal")) {
            withdraw(amount);
        }
        return true;
    }
}
