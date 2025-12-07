package com.models;
import com.models.exceptions.*;
public interface Transactable {

    boolean processTransactions(double amount, String type) throws RuntimeException;
}
