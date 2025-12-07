package com.models;
import com.models.  exceptions.*;

public class CheckingAccount extends Account {

    private final double overdraftLimit;
    private double monthlyFee;

    CheckingAccount() {
        super();
        this.overdraftLimit = 1000;
        this.monthlyFee = 10;
    }

    public CheckingAccount(Customer customer, double balance) {
        this();
        setCustomer(customer);
        setBalance(balance);
        setStatus("active");
    }

    @Override
    public String getAccountSpecificDetails() {
        return String.format("Overdraft Limit: $%.2f MonthlyFee: $%.2f", getOverdraftLimit(), getMonthlyFee());
    }

    public double getOverdraftLimit() {
        return this.overdraftLimit;
    }

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

    @Override
    public String getAccountType() {
        return "Checking";
    }

    public double getMonthlyFee() {
        return this.monthlyFee;
    }

    public boolean hasOverdraftLimitExceeded(double amount) throws OverdraftLimitException , IllegalAmountException {
        double overdraftLimit = this.getOverdraftLimit();
        double balance = getBalance();
        if(amount < 0) throw new IllegalAmountException("Amount cannot be less than 0");
        if (Math.abs(balance - amount) > overdraftLimit) {
            throw new OverdraftLimitException("You can't withdraw more than your overdraft limit");

        }
        return false;
    }

    public  boolean deposit(double amount) throws IllegalAmountException {
        if (amount <= 0) throw new IllegalAmountException("Deposit amount cannot be zero");
        setBalance(getBalance() + amount);
        return true;
    }

    public void withdraw(double amount) throws IllegalAmountException, OverdraftLimitException {
        double balance = getBalance();
        if (amount <= 0) throw new IllegalAmountException("Withdrawal amount cannot be zero");
        hasOverdraftLimitExceeded(amount);
        this.setBalance(balance - amount);
    }


    public boolean processTransactions(double amount, String type) throws IllegalAmountException, InsufficientFundsExceptions {
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
