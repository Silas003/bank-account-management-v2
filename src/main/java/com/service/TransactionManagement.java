
package com.service;
import com.models.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;


/**
 * Management layer for transaction data operations and storage.
 */
public class TransactionManagement {

    public static ArrayList<Transaction> transactions = new ArrayList<>();
    public int transactionCount;

    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> viewTransactionByAccount(String accountNumber) {
        return transactions.stream()
                .filter(trnx -> trnx.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Transaction> viewAllTransactionsTyType(String type){
        return transactions.stream().filter(trnx->trnx.getType().equals(type))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getTransactionCount() {
        return this.transactions.size();
    }
}
