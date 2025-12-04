
package com.models;

import com.exceptions.CustomExceptions;

/*
 * Represents a savings account with interest accrual and minimum balance requirements.
 *
 * Savings accounts are designed for customers who want to earn interest on their deposits
 * while maintaining a minimum balance. These accounts have restrictions on withdrawals to
 * ensure the minimum balance requirement is maintained.
 *
 * Key features:
 * - Annual interest rate of 3.5% (0.035)
 * - Minimum balance requirement of $500
 * - Withdrawals cannot reduce balance below $0
 * - Withdrawals must maintain the minimum balance requirement
 *
 * Withdrawal validation ensures that:
 * 1. The balance does not go below $0
 * 2. The balance after withdrawal meets the minimum balance requirement
 */
public class SavingsAccount extends Account {

    // Annual interest rate as a decimal (0.035 = 3.5%)
    private final double interestRate;

    // Minimum balance that must be maintained in the account
    private final double minimumBalance;

    /*
     * Default constructor that initializes a savings account with default values.
     * Sets interest rate to 3.5% (0.035) and minimum balance to $500.
     */
    SavingsAccount() {
        super();
        this.interestRate = 0.035;
        this.minimumBalance = 500;
    }

    /*
     * Constructs a savings account with a customer and initial balance.
     * Associates the customer and sets the initial balance and status.
     */
    public SavingsAccount(Customer customer, double balance) {
        this();
        setCustomer(customer);
        setBalance(balance);
        setStatus("active");
    }

    // Returns a formatted string containing all account details
    @Override
    public String displayAccountDetails() {
        return String.format("Account Number: %s\n" +
                        "Customer: %s\n" +
                        "Account Type: %s\n" +
                        "Initial Balance: %.2f\n" +
                        "Interest Rate: %.1f%%\n" +
                        "Minimum Balance: %s\n" +
                        "Status: %s",
                this.getAccountNumber(), this.getCustomer(), this.getAccountType(), this.getBalance(),
                (this.interestRate * 100), this.minimumBalance, this.getStatus());
    }

    // Returns the account type identifier
    @Override
    public String getAccountType() {
        return "Savings";
    }

    public  boolean deposit(double amount) throws CustomExceptions.IllegalAmountException {
        if (amount <= 0) throw new CustomExceptions.IllegalAmountException("Deposit amount cannot be zero");
        setBalance(getBalance() + amount);
        return true;
    }

    /*
     * Processes a withdrawal transaction with balance validation.
     * Ensures the balance does not go below zero.
     */
    @Override
    public void withdraw(double amount) throws CustomExceptions.InsufficientFundsExceptions,CustomExceptions.IllegalAmountException{
        double balance = getBalance();
        if (amount <= 0) throw new CustomExceptions.IllegalAmountException("Deposit amount cannot be zero");
        if (balance != 0 && (balance - amount < 0)) {
            throw new CustomExceptions.InsufficientFundsExceptions("You can't withdraw below a balance of 0");
        } else {
            setBalance(balance - amount);
        }
    }

    /*
     * Calculates the interest that would be earned on the current balance.
     * Simple interest calculation based on balance and interest rate.
     */
    public double calculateInterest() {
        double balance = getBalance();
        return balance * this.interestRate;
    }

    // Returns the interest rate as a formatted percentage string
    private String getInterestRate() {
        return String.format("%.1f%%", interestRate * 100);
    }

    // Retrieves the minimum balance requirement for this savings account
    public double getMinimumBalance() {
        return minimumBalance;
    }

    // Returns account-specific details including interest rate and minimum balance
    @Override
    public String getAccountSpecificDetails() {
        return String.format("Interest Rate: %s Min Balance:$ %.2f", getInterestRate(), getMinimumBalance());
    }


    /*
     * Processes a transaction with minimum balance validation for savings accounts.
     * Deposits are processed normally; withdrawals must maintain minimum balance.
     */
    public boolean processTransactions(double amount, String type) throws CustomExceptions.IllegalAmountException, CustomExceptions.InsufficientFundsExceptions {
        if ("Deposit".equalsIgnoreCase(type)) {
            return deposit(amount);
        } else if ("Withdrawal".equalsIgnoreCase(type)) {
            if (getBalance() - amount >= getMinimumBalance()) {
                withdraw(amount);
                return true;
            } throw new CustomExceptions.InsufficientFundsExceptions("You cannot withdraw more than your minimum allowed balance");
        }
        return false;
    }
}
