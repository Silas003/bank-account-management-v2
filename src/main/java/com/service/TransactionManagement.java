
package com.service;
import com.models.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Management layer for transaction data operations and storage.
 */
public class TransactionManagement {

    public static List<Transaction> transactions = new ArrayList<>();

    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> viewTransactionByAccount(String accountNumber) {
        return transactions.stream()
                .filter(trnx -> trnx.getAccountNumber().equalsIgnoreCase(accountNumber))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Transaction> viewAllTransactionsByAmount(String accountNumber){
        return transactions.stream().filter(trnx->trnx.getAccountNumber().equalsIgnoreCase(accountNumber))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Transaction> viewAllTransactionsByDateTime(String accountNumber){
        return transactions.stream().filter(trnx->trnx.getAccountNumber().equalsIgnoreCase(accountNumber))
                .sorted(Comparator.comparing(Transaction::getTimeStamp).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getTransactionCount() {
        return this.transactions.size();
    }
}
