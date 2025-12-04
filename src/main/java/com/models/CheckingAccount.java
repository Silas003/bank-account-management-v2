
package com.models;

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
    public boolean hasOverdraftLimitExceeded(double amount) {
        double overdraftLimit = this.getOverdraftLimit();
        double balance = getBalance();

        if (Math.abs(balance - amount) > overdraftLimit) {
            System.out.println("You can't withdraw more than your overdraft limit");
            return true;
        }
        return false;
    }

    public  boolean deposit(double amount) {
        if (amount <= 0) return false;
        setBalance(getBalance() + amount);
        return true;
    }
    /*
     * Processes a withdrawal transaction with overdraft protection.
     * Rejects withdrawal if overdraft limit would be exceeded.
     */
    public void withdraw(double amount) {
        double balance = getBalance();
        if (hasOverdraftLimitExceeded(amount)) {
            return;
        }
        this.setBalance(balance - amount);
    }



    /*
     * Processes a transaction with enhanced validation for checking accounts.
     * Deposits are processed normally; withdrawals are validated against overdraft limit.
     */
    public boolean processTransactions(double amount, String type) {
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
