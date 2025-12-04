
package com.service;
import com.models.Account;

// Management layer for account data operations and storage.
// Provides static methods for managing the collection of accounts in the system.
// Maintains an array-based storage structure with a maximum capacity of 50 accounts
// and provides operations for adding, finding, and retrieving accounts.
//
// Key responsibilities:
// - Account storage and retrieval
// - Account lookup by account number
// - Account listing and aggregation
// - Balance calculations across all accounts
//
// Note: This implementation uses a fixed-size array. In production, consider using
// a more scalable data structure such as a HashMap or database for better performance.
public class AccountManagement {

    // Fixed-size array to store accounts (maximum 50 accounts)
    private static Account[] accounts = new Account[50];

    // Current number of accounts in the system
    public static int accountCount;

    // Adds a new account to the system.
    // Validates that there is available capacity before adding the account.
    public static void addAccount(Account account) {
        if (accountCount < accounts.length) {
            accounts[accountCount++] = account;
        } else {
            System.out.println("Account list is full!");
        }
    }

    // Finds an account by its account number.
    // Searches through all accounts in the system to find a match.
    public static Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            Account account = accounts[i];
            if (account.getAccountNumber().equals(accountNumber)) return account;
        }
        return null;
    }

    // Retrieves all accounts in the system.
    // Returns the entire accounts array (may include null entries beyond accountCount).
    public static Account[] viewAllAccounts() {
        return accounts;
    }

    // Calculates the total balance across all accounts in the system.
    // Iterates through all active accounts and sums their balances.
    public double getTotalBalance() {
        double totalBalance = 0;
        Account account;
        System.out.println(accountCount); // Debug print statement
        for (int i = 0; i < accountCount; i++) {
            account = accounts[i];
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    // Retrieves the current number of accounts in the system.
    public static int getAccountCount() {
        return accountCount;
    }
}
