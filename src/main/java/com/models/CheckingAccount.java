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
    public CheckingAccount(String accountNumber,Customer customer, double balance) {
        this();
        setAccountNumberFromFile(accountNumber);
        setCustomer(customer);
        setBalance(balance);
        setStatus("active");
    }
    public void setAccountNumberFromFile(String accountNumber) {
        setAccountNumber(accountNumber);
    }

    @Override
    public String getAccountSpecificDetails() {
        return String.format("Overdraft Limit: $%.2f MonthlyFee: $%.2f", getOverdraftLimit(), getMonthlyFee());
    }


    public double getOverdraftLimit() {
        return this.overdraftLimit;
    }


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


    public boolean hasOverdraftLimitExceeded(double amount) throws OverdraftLimitException  {
        double overdraftLimit = this.getOverdraftLimit();
        double balance = getBalance();
        if (balance - amount < -overdraftLimit) {
            throw new OverdraftLimitException("You can't withdraw more than your overdraft limit");
        }
        return false;
    }


    public synchronized  boolean deposit(double amount)  {
        setBalance(getBalance() + amount);
        return true;
    }

    public synchronized void withdraw(double amount) throws OverdraftLimitException, InsufficientFundsExceptions {
        double balance = getBalance();
        hasOverdraftLimitExceeded(amount);
        this.setBalance(balance - amount);
    }



    public boolean processTransactions(double amount, String type) throws InsufficientFundsExceptions, OverdraftLimitException {
        if (type.equalsIgnoreCase("Deposit")) {
            return deposit(amount);
        } else if (type.equalsIgnoreCase("Withdrawal")) {
            hasOverdraftLimitExceeded(amount);
            try {
                withdraw(amount);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }
}
