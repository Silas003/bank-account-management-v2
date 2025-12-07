package com.service;

import com.models.Transaction;

import java.util.ArrayList;

/**
 * Management layer for transaction data operations and storage.
 */
public class TransactionManagement {

    public Transaction transactions[] = new Transaction[200];
    public int transactionCount;

    public void addTransaction(Transaction transaction) {
        transactions[transactionCount] = transaction;
        transactionCount++;
    }

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

    public int getTransactionCount() {
        return this.transactionCount;
    }
}
