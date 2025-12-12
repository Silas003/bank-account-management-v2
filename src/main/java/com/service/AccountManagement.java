package com.service;
import com.models.*;
import com.models.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Management layer for account data operations and storage.
 */

public class AccountManagement {

    private static HashMap<String,Account> accounts = new HashMap<>();
    public static int accountCount;


    public static void addAccount(Account account) throws IOException {

        accounts.put(account.getAccountNumber(), account);
    }


    public static Account findAccount(String accountNumber) throws InvalidAccountException {
        return Optional.ofNullable(accounts.get(accountNumber))
                .orElseThrow(()-> new InvalidAccountException("Account Number not found.Returning to main menu"));
    }



    public static ArrayList<Account> viewAllAccounts() {
        ArrayList<Account> viewAccounts = new ArrayList<>();
        for(Account account:accounts.values())
            viewAccounts.add(account);
        return viewAccounts;
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (Account account:accounts.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    public static int getAccountCount() {
        return accounts.size();
    }

    public static void returnVoid(){
        System.out.println(accounts);
    }
}
