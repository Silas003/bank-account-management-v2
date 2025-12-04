
package com.models;

import com.exceptions.CustomExceptions;

/*
 * Represents a checking account with overdraft protection and monthly fees.
 *
 * Checking accounts allow customers to withdraw funds beyond their current balance
 * up to a specified overdraft limit. These accounts typically have monthly maintenance
 * fees, though fees may be waived for premium customers.
 *
 * Key features:
 * - Overdraft limit of $1,000 (default)
 * - Monthly fee of $10 (may be waived for premium customers)
 * - Withdrawals can result in negative balances up to the overdraft limit
 *
 * Withdrawal validation ensures that the account balance after withdrawal does not
 * exceed the overdraft limit.
 */
public class CheckingAccount extends Account {

    // Maximum amount the account can go into negative balance (overdraft)
    private final double overdraftLimit;

    // Monthly maintenance fee charged to the account
    private double monthlyFee;

    /*
     * Default constructor that initializes a checking account with default values.
     * Sets overdraft limit to $1,000 and monthly fee to $10.
     */
    CheckingAccount() {
        super();
        this.overdraftLimit = 1000;
        this.monthlyFee = 10;
    }

    /*
     * Constructs a checking account with a customer and initial balance.
     * Associates the customer and sets the initial balance and status.
     */
    public CheckingAccount(Customer customer, double balance) {
        this();
        setCustomer(customer);
        setBalance(balance);
        setStatus("active");
    }

    /*
     * Returns account-specific details including overdraft limit and monthly fee.
     */
    @Override
    public String getAccountSpecificDetails() {
        return String.format("Overdraft Limit: $%.2f MonthlyFee: $%.2f", getOverdraftLimit(), getMonthlyFee());
    }

    // Retrieves the overdraft limit for this checking account
    public double getOverdraftLimit() {
        return this.overdraftLimit;
    }

    // Returns a formatted string containing all account details
    @Override
    public String displayAccountDetails() {
        return String.format("Account Number: %s\n" +
                        "Customer: %s\n" +
                        "Account Type: %s\n" +
                        "Initial Balance: %.2f\n" +
                        "Overdraft Limit: %s\n" +
                        "Monthly Fee: %s\n" +
                        "Status: %s",
                this.getAccountNumber(), this.getCustomer(), this.getAccountType(), this.getBalance(),
                this.getOverdraftLimit(), (this.monthlyFee + " (WAIVED - Premium Customer)"), this.getStatus());
    }

    // Returns the account type identifier
    @Override
    public String getAccountType() {
        return "Checking";
    }

    // Retrieves the monthly maintenance fee for this account
    public double getMonthlyFee() {
        return this.monthlyFee;
    }

    /*
     * Applies the monthly fee to the account.
     * Note: Current implementation sets fee to -10 (placeholder).
     */

    /*
     * Validates whether a withdrawal would exceed the overdraft limit.
     * Checks if the absolute value of the balance after withdrawal would exceed the limit.
     */
    public boolean hasOverdraftLimitExceeded(double amount) throws CustomExceptions.OverdraftLimitException , CustomExceptions.IllegalAmountException {
        double overdraftLimit = this.getOverdraftLimit();
        double balance = getBalance();
        if(amount < 0) throw new CustomExceptions.IllegalAmountException("Amount cannot be less than 0");
        if (Math.abs(balance - amount) > overdraftLimit) {
            throw new CustomExceptions.OverdraftLimitException("You can't withdraw more than your overdraft limit");

        }
        return false;
    }

    public  boolean deposit(double amount) throws CustomExceptions.IllegalAmountException {
        if (amount <= 0) throw new CustomExceptions.IllegalAmountException("Deposit amount cannot be zero");
        setBalance(getBalance() + amount);
        return true;
    }
    /*
     * Processes a withdrawal transaction with overdraft protection.
     * Rejects withdrawal if overdraft limit would be exceeded.
     */
    public void withdraw(double amount) throws CustomExceptions.IllegalAmountException, CustomExceptions.OverdraftLimitException {
        double balance = getBalance();
        if (amount <= 0) throw new CustomExceptions.IllegalAmountException("Withdrawal amount cannot be zero");
        hasOverdraftLimitExceeded(amount);
        this.setBalance(balance - amount);
    }



    /*
     * Processes a transaction with enhanced validation for checking accounts.
     * Deposits are processed normally; withdrawals are validated against overdraft limit.
     */
    public boolean processTransactions(double amount, String type) throws CustomExceptions.IllegalAmountException, CustomExceptions.InsufficientFundsExceptions {
        if (type.equalsIgnoreCase("Deposit")) {
            return deposit(amount);
        } else if (type.equalsIgnoreCase("Withdrawal")) {
            if (hasOverdraftLimitExceeded(amount)) {
                return false;
            }
            withdraw(amount);
            return true;
        }
        return false;
    }
}
