package com.service;
import com.models.exceptions.*;
import com.models.Account;
import com.models.Transaction;
import com.utilities.ValidationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Service layer for transaction-related operations.
 */
public class TransactionServices {

    private final AccountManagement accountManagement;
    private final TransactionManagement transactionManagement;
    private final Scanner scanner;
    private final HashMap<String, String> transactionType = new HashMap<>();

    public TransactionServices(AccountManagement accountManagement, TransactionManagement transactionManagement, Scanner scanner) {
        this.accountManagement = accountManagement;
        this.transactionManagement = transactionManagement;
        this.scanner = scanner;
        transactionType.put("1", "Deposit");
        transactionType.put("2", "Withdrawal");
    }

    public void processDepositWithdrawal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("DEPOSIT/WITHDRAWAL");
        System.out.println("==================");

        try {
            String accountNumber = ValidationUtils.validateAccountNumberInput(scanner);
            Account userAccount = accountManagement.findAccount(accountNumber.toUpperCase());
            String transactionTypeInput = ValidationUtils.validateTransactionTypeInput(scanner);
            double amount = ValidationUtils.validateTransactionAmount(scanner);
            double newBalance = transactionTypeInput.equals("1") ? userAccount.getBalance() + amount : userAccount.getBalance() - amount;
            String dateTime = LocalDateTime.now().format(formatter);
            printTransactionSummary(userAccount, amount, transactionType.get(transactionTypeInput), newBalance, dateTime);
            ValidationUtils.validateTransactionConfirmation(scanner);
            boolean success = userAccount.processTransactions(amount, transactionType.get(transactionTypeInput));
            if (success) {
                transactionManagement.addTransaction(new Transaction(userAccount.getAccountNumber(),
                        transactionType.get(transactionTypeInput),
                        amount,
                        userAccount.getBalance(),
                        dateTime));
                System.out.println("Transaction successful!");
            } else {
                System.out.println("Transaction failed! Check balance or account rules.");
            }
        }catch (InvalidAccountException | InsufficientFundsExceptions | TypeSelectionException
        | ArrayIndexOutOfBoundsException | IllegalAmountException ce){
            System.out.println(ce.getMessage());
            ValidationUtils.promptEnterKey(scanner);
        }catch (RuntimeException re){
            System.out.println(re.getMessage());
        }
    }

    public void transferToOtherAccounts() {
        System.out.println("TRANSFER TO OTHER ACCOUNTS");
        System.out.println("==========================");
        System.out.println("This feature is coming soon!");
        System.out.println("You will be able to transfer funds between accounts here.");
        ValidationUtils.promptEnterKey(scanner);
    }

    public void viewTransactionHistory() {
        System.out.println("GENERATE ACCOUNT STATEMENT");
        System.out.println("========================");
        try{
            String accountNumber = ValidationUtils.validateAccountNumberInput(scanner);
            Account account = accountManagement.findAccount(accountNumber.toUpperCase());
            ArrayList<Transaction> transactions = transactionManagement.viewTransactionByAccount(account.getAccountNumber());
            printTransactionHistory(account,transactions);
        }catch (InvalidAccountException iae){
            System.out.println(iae.getMessage());
        }
        ValidationUtils.promptEnterKey(scanner);
    }

    public void printTransactionHistory(Account account,ArrayList<Transaction> transactions){
        double totalDeposits = 0;
        double totalWithdrawals = 0;
        System.out.printf("Account: %s - %s\nAccount Type: %s\nCurrent Balance: %.2f\n\n",
                account.getAccountNumber(), account.getCustomer(), account.getAccountType(), account.getBalance());
        System.out.println("TRANSACTION HISTORY");
        System.out.println("=====================================================================");
        System.out.println("TXN ID | DATE/TIME          | TYPE    | AMOUNT    | BALANCE");

        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("Deposit")) totalDeposits += transaction.getAmount();
            else if (transaction.getType().equals("Withdrawal")) totalWithdrawals += transaction.getAmount();

            System.out.printf("%s |%s |%s  |%s$%.2f  |$%.2f\n",
                    transaction.getTransactionId(), transaction.getTimeStamp(), transaction.getType(),
                    transaction.getType().equals("Deposit") ? "+" : "-", transaction.getAmount(), transaction.getBalanceAfter());
        }

        System.out.println("=====================================================================\n");
        System.out.println("Total Transactions: " + transactions.size());
        System.out.println("Total Deposits: " + totalDeposits);
        System.out.println("Total Withdrawals: " + totalWithdrawals);
        System.out.println("Net Change: " + (totalDeposits - totalWithdrawals));


        ValidationUtils.promptEnterKey(scanner);
    }


    public void printTransactionSummary(Account account, double amount, String type, double newBalance, String dateTime) {
        System.out.println("TRANSACTION CONFIRMATION");
        System.out.println("========================");
        System.out.printf("Transaction ID: TNX00%d\n", transactionManagement.getTransactionCount());
        System.out.printf("Account: %s\n", account.getAccountNumber());
        System.out.printf("Type: %s\n", type);
        System.out.printf("Amount: $%.2f\n", amount);
        System.out.printf("Previous Balance: $%.2f\n", account.getBalance());
        System.out.printf("New Balance: $%.2f\n", newBalance);
        System.out.printf("Date/Time: %s\n", dateTime);
    }
}
