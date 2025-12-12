package com.models;
import com.models.exceptions.*;

public abstract class Account implements Transactable {

    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;
    public static int accountCounter;


    Account() {
        setAccountNumber();

    }


    public abstract String displayAccountDetails();

    public abstract String getAccountType();

    public abstract String getAccountSpecificDetails();


    private void generateAccountNumber() {
        this.accountNumber = "ACC00" + ++accountCounter;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber() {
        generateAccountNumber();
    }

    public String getCustomer() {
        return this.customer.toString();
    }

    public int getAccountCounter() {
        return accountCounter;
    }

    public void setCustomer(Customer Customer) {
        this.customer = Customer;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract boolean deposit(double amount) throws IllegalAmountException;

    public abstract void withdraw (double amount)throws IllegalAmountException, InsufficientFundsExceptions;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

//    @Override
//    public String toString() {
//        return String.format("Customer: %s\nAccount Type:%s\nCurrent Balance: %.2f",
//                getCustomer(), getAccountType(), getBalance());
//    }


    public boolean processTransactions(double amount, String type) throws IllegalAmountException, InsufficientFundsExceptions {

        if (type.equalsIgnoreCase("Deposit")) {
            return deposit(amount);
        } else if (type.equalsIgnoreCase("Withdrawal")) {
            withdraw(amount);
        }
        return true;
    }
}
