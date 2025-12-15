
package com.models;

public interface Transactable {
     boolean processTransactions(double amount, String type, Account receiver) throws RuntimeException;

}
