
package com.service;

import com.models.Account;
import com.models.Transaction;
import com.utilities.CustomUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Service layer for transaction-related operations.
// Responsibilities:
// - Transaction processing (deposits and withdrawals)
// - Transaction validation and confirmation
// - Transaction history retrieval and display
// - Transaction summary generation
//
// The service validates account existence, transaction amounts, and obtains user
// confirmation before processing transactions. All transactions are recorded with
// timestamps for audit purposes.
public class TransactionServices {

    // Data management layer for account operations
    private final AccountManagement accountManagement;

    // Data management layer for transaction operations
    private final TransactionManagement transactionManagement;

    // Scanner instance for reading user input
    private final Scanner scanner;

    // Map of user input choices to transaction type strings
    private final HashMap<String, String> transactionType = new HashMap<>();

    // Constructs a new TransactionServices with the provided dependencies.
    // Initializes the transaction type mapping for user input conversion:
    // "1" -> "Deposit", "2" -> "Withdrawal"
    public TransactionServices(AccountManagement accountManagement, TransactionManagement transactionManagement, Scanner scanner) {
        this.accountManagement = accountManagement;
        this.transactionManagement = transactionManagement;
        this.scanner = scanner;
        transactionType.put("1", "Deposit");
        transactionType.put("2", "Withdrawal");
    }

    // Processes a transaction (deposit or withdrawal).
    // Validates account number, transaction type, and amount.
    // Displays a summary for confirmation before processing.
    public void processTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("PROCESS TRANSACTION");
        System.out.println("===================");

        String accountNumber = CustomUtils.validateAccountNumberInput(scanner);
        if (accountNumber == null) return;
        Account userAccount = accountManagement.findAccount(accountNumber.toUpperCase());
        if (userAccount == null) {
            System.out.println("Account not found. Returning to main menu");
            return;
        }

        String transactionTypeInput = CustomUtils.validateTransactionTypeInput(scanner);
        if (transactionTypeInput == null) return;

        double amount = CustomUtils.validateTransactionAmount(scanner);
        if (amount == -1) return;

        double newBalance = transactionTypeInput.equals("1") ? userAccount.getBalance() + amount : userAccount.getBalance() - amount;
        String dateTime = LocalDateTime.now().format(formatter);

        printTransactionSummary(userAccount, amount, transactionType.get(transactionTypeInput), newBalance, dateTime);

        String confirmation = CustomUtils.validateTransactionConfirmation(scanner);
        if (confirmation == null || confirmation.equalsIgnoreCase("N")) return;

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

        CustomUtils.promptEnterKey(scanner);
    }

    // Displays the complete transaction history for a specific account.
    // Shows all transactions, totals for deposits and withdrawals, and net change.
    public void viewTransactionHistory() {
        System.out.println("VIEW TRANSACTION HISTORY");
        System.out.println("========================");
        String accountNumber = CustomUtils.validateAccountNumberInput(scanner);
        if (accountNumber == null) return;
        if (accountManagement.findAccount(accountNumber.toUpperCase()) == null) {
            System.out.println("Account not found. Returning to main menu");
            return;
        }
        Account account = accountManagement.findAccount(accountNumber.toUpperCase());

        ArrayList<Transaction> transactions = transactionManagement.viewTransactionByAccount(account.getAccountNumber());
        double totalDeposits = 0;
        double totalWithdrawals = 0;
        if (transactions.size() == 0) {
            System.out.println("No transactions recorded for this account");
            return;
        }
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

        CustomUtils.promptEnterKey(scanner);
    }

    // Displays a summary of the transaction before confirmation.
    // Shows transaction details: ID, account, type, amount, previous balance, new balance, and timestamp.
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
