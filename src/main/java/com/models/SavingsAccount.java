package com.models;
import com.models.exceptions.*;
public class SavingsAccount extends Account {

    private final double interestRate;
    private final double minimumBalance;

     SavingsAccount() {
        this.interestRate = 0.035;
        this.minimumBalance = 500;
    }


    public SavingsAccount(Customer customer, double balance) {
        this();
        setCustomer(customer);
        setBalance(balance);
        setStatus("active");
    }

    public SavingsAccount(String accountNumber,Customer customer, double balance) {
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


    @Override
    public String getAccountType() {
        return "Savings";
    }


    public synchronized  boolean deposit(double amount) {
        setBalance(getBalance() + amount);
        return true;
    }


    @Override
    public synchronized void withdraw(double amount) throws InsufficientFundsExceptions{
        double balance = getBalance();
        if (balance - amount < minimumBalance) {
            throw new InsufficientFundsExceptions("You can't withdraw below minimum Balance of $500.Current Balance: $"+getBalance());

        } else {
            setBalance(balance - amount);
        }
    }

    public double calculateInterest() {
        double balance = getBalance();
        return balance * this.interestRate;
    }


    private String getInterestRate() {
        return String.format("%.1f%%", interestRate * 100);
    }


    public double getMinimumBalance() {
        return minimumBalance;
    }


    @Override
    public String getAccountSpecificDetails() {
        return String.format("Interest Rate: %s Min Balance:$ %.2f", getInterestRate(), getMinimumBalance());
    }


    public boolean processTransactions(double amount, String type) throws InsufficientFundsExceptions {
        if ("Deposit".equalsIgnoreCase(type)) {
            return deposit(amount);
        } else if ("Withdrawal".equalsIgnoreCase(type)) {
                withdraw(amount);
                return true;

        }
        return false;
    }
}
