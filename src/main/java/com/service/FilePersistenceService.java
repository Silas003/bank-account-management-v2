package com.service;
import com.models.*;
import com.utilities.ValidationUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class FilePersistenceService {
    static Customer customer;
    static Account account;
    static Transaction transaction;
    private static Path getOrCreateFile(String fileType) throws IOException {
        Path path = Paths.get("data/" + fileType + ".txt");
        if (Files.notExists(path))
            return  Files.createFile(path);
        return path;
    }

    public static void writeToAccountFile(String filepath, Account account) throws IOException{
        Path path = getOrCreateFile(filepath);
        if(Files.notExists(path))
            throw new IOException("File not found.Try again later...");
        try(var write = Files.newBufferedWriter(path,StandardCharsets.UTF_8,StandardOpenOption.APPEND)){
            write.write(convertAccountToString(account));
            write.newLine();
        }

    }
    public static void writeToCustomerFile(String filepath, Customer customer) throws IOException{
        Path path = getOrCreateFile(filepath);
        if(Files.notExists(path))
            throw new IOException("File not found.Try again later...");
        try(var write = Files.newBufferedWriter(path,StandardCharsets.UTF_8,StandardOpenOption.APPEND)){
            write.write(convertCustonerToString(customer));
            write.newLine();
        }

    }

    public static void writeToTransactionFile(String filepath, Transaction transaction) throws IOException{
        Path path = getOrCreateFile(filepath);
        if(Files.notExists(path))
            throw new IOException("File not found.Try again later...");
        try(var write = Files.newBufferedWriter(path,StandardCharsets.UTF_8,StandardOpenOption.APPEND)){
            write.write(convertTransactionToString(transaction));
            write.newLine();
        }

    }

    public static ArrayList<List> readFromFile(String filepath) throws IOException {
        Path path = getOrCreateFile(filepath);
        List<String> readData = Files.readAllLines(path);
        return readData.stream().map(p->Arrays.asList(p.split(",")))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    private static String convertAccountToString(Account account){
        return String.format("%s,%s,%s,%.2f,%s",
                account.getAccountNumber(),
                account.getCustomer(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    private static String convertCustonerToString(Customer customer){
        return String.format("%s,%s,%s,%s,%s,%s\n",
                customer.getCustomerId(),
                customer.getName(),
                customer.getAge(),
                customer.getContact(),
                customer.getAddress(),
                customer.getCustomerType()
        );
    }

    private static String convertTransactionToString(Transaction transaction){
        return String.format("%s,%s,%s,%.2f,%.2f,%s\n",
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getTimeStamp()
        );
    }

    public static void reWriteAllToFile() throws IOException {
        var accounts = AccountManagement.viewAllAccounts();
        Path path = getOrCreateFile("account");

        try (var writer = Files.newBufferedWriter(
                path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (var account : accounts) {
                try {
                    writer.write(convertAccountToString(account));
                    writer.newLine();
                } catch (IOException writeError) {
                    System.err.printf("Failed to write account %s: %s%n",
                            account.getAccountNumber(), writeError.getMessage());
                }
            }
            System.out.println("Accounts saved to accounts.txt");
        } catch (IOException e) {
            throw new java.io.UncheckedIOException("Failed to open account file", e);
        }
    }

    public static void loadAllDataFromFile() throws IOException {
        Double balance;
        Double amount;
        Double balanceAfter;
        System.out.println("Loading customer data from files...");
        ArrayList<List> customers = readFromFile("customer");
        for (List<String> row : customers) {
            if(row.size()>=5) {
                if(row.get(5).equalsIgnoreCase("Regular")){
                    customer = new RegularCustomer(
                            row.get(0),
                            row.get(1),
                            Integer.parseInt(row.get(2)),
                            row.get(3),
                            row.get(4),
                            row.get(5));

                }else {
                    customer = new PremiumCustomer(
                            row.get(0),
                            row.get(1),
                            Integer.parseInt(row.get(2)),
                            row.get(3),
                            row.get(4),
                            row.get(5)
                    );
                }
                CustomerManagement.addCustomer(customer);
            }
        }
        Customer.customerCounter = customers.size();
        System.out.println("Loading account data from files...");
        ArrayList<List> accounts = readFromFile("account");
        Account.accountCounter = accounts.size();
        for (List<String> row : accounts) {
            if(row.size() >=3) {
                Customer customer1 = CustomerManagement.findCustomerById(row.get(1).trim());
                balance = ValidationUtils.parseMoney(row.get(3));
                if(row.get(2).equalsIgnoreCase("Savings")){
                    account = new SavingsAccount(row.get(0).trim(),customer1,balance);
                }else {
                    account = new CheckingAccount(row.get(0).trim(),customer1,balance);
                }
            }

            AccountManagement.addAccount(account);
        }
        System.out.println("Loading transaction data from files...");
        ArrayList<List> transactions = readFromFile("transaction");
        Transaction.transactionCounter = transactions.size();
        for (List<String> row : transactions) {
            if(row.size() >=3) {
                amount = ValidationUtils.parseMoney(row.get(3));
                balanceAfter = ValidationUtils.parseMoney(row.get(4));
                transaction = new Transaction(row.get(0), row.get(1).trim(), row.get(2), amount,balanceAfter,row.get(5));
            }

            TransactionManagement.addTransaction(transaction);
        }
        System.out.printf("%d customers loaded from customer.txt\n",customers.size());
        System.out.printf("%d accounts loaded from account.txt\n",accounts.size());
        System.out.printf("%d transactions loaded from transaction.txt\n",transactions.size());
    }
}
