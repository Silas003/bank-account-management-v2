
package com.service;

import com.models.Transaction;

import java.util.ArrayList;

// Management layer for transaction data operations and storage.
// Provides methods for managing the collection of transactions in the system.
// Maintains an array-based storage structure with a maximum capacity of 200 transactions
// and provides operations for adding transactions and retrieving transaction history by account.
//
// Key responsibilities:
// - Transaction storage and retrieval
// - Transaction lookup by account number
// - Transaction history aggregation
//
// Note: This implementation uses a fixed-size array. In production, consider using
// a more scalable data structure such as a HashMap indexed by account number or a database.
// Methods calculateTotalDeposits and calculateTotalWithdrawals are placeholders.
public class TransactionManagement {

    // Fixed-size array to store transactions (maximum 200 transactions)
    public Transaction transactions[] = new Transaction[200];

    // Current number of transactions in the system
    public int transactionCount;

    // Adds a new transaction to the system.
    // Stores the transaction in the next available position and increments the counter.
    // Warning: Does not check for array bounds; may throw ArrayIndexOutOfBoundsException if full.
    public void addTransaction(Transaction transaction) {
        transactions[transactionCount] = transaction;
        transactionCount++;
    }

    // Retrieves all transactions associated with a specific account.
    // Searches through all transactions and returns those that match the account number.
    public ArrayList<Transaction> viewTransactionByAccount(String accountNumber) {
        ArrayList<Transaction> accountTransactions = new ArrayList<>();
        Transaction transaction;
        for (int i = 0; i < transactionCount; i++) {
            transaction = transactions[i];
            if (transaction.getAccountNumber().equals(accountNumber)) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }

    // Placeholder method for calculating total deposits for an account.
    public void calculateTotalDeposits(String accountNumber) {
    }

    // Placeholder method for calculating total withdrawals for an account.
    public void calculateTotalWithdrawals(String accountNumber) {
    }

    // Retrieves the current number of transactions in the system.
    public int getTransactionCount() {
        return this.transactionCount;
    }
}
