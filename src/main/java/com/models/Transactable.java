
package com.models;

import com.models.exceptions.OverdraftLimitException;

public interface Transactable {
     boolean processTransactions(double amount, String type, Account receiver) throws RuntimeException;

}
