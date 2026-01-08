package com.service;
import com.models.*;
import com.models.exceptions.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Management layer for account data operations and storage.
 */

public class AccountManagement {

    private static Map<String,Account> accounts = new HashMap<>();

    public static void addAccount(Account account) {
            accounts.put(account.getAccountNumber(), account);
    }


    public static Account findAccount(String accountNumber) throws InvalidAccountException {
        return Optional.ofNullable(accounts.get(accountNumber.toUpperCase()))
                .orElseThrow(()-> new InvalidAccountException("Account Number: "+accountNumber+" not found.Returning to main menu"));
    }



    public static List<Account> viewAllAccounts() {
        return accounts.values().stream().collect(Collectors.toList());
    }

    public double getTotalBalance() {

        return accounts.values().stream().mapToDouble(account ->account.getBalance()).sum();

    }

    public static int getAccountCount(){
        return accounts.values().size();
    }



}
