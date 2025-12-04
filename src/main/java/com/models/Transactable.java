package com.models;

public interface Transactable {

    boolean processTransactions(double amount, String type) throws RuntimeException;
}
