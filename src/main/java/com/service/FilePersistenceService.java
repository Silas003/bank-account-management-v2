package com.service;
import com.models.*;
import com.models.exceptions.InvalidAccountException;
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

    public static List<String[]> readFromFile(String filepath) throws IOException {
        Path path = getOrCreateFile(filepath);
        var readData = Files.readAllLines(path);
        return readData.stream().map(item-> item.split(",")).toList();

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
        return String.format("%s,%s,%s,%s,%s,%s",
                customer.getCustomerId(),
                customer.getName(),
                customer.getAge(),
                customer.getContact(),
                customer.getAddress(),
                customer.getCustomerType()
        );
    }

    private static String convertTransactionToString(Transaction transaction){
        return String.format("%s,%s,%s,%.2f,%.2f,%s",
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

    public static void loadAllDataFromFile() throws IOException, InvalidAccountException {
        System.out.println("Loadind data from files...");
        List<String[]> customers = readFromFile("customer");
         customers.stream()
                 .filter(row -> row.length >= 5)
                .map(row->

                {
                    if(row[5].equalsIgnoreCase("Regular")) {
                        return new RegularCustomer(
                                row[0],
                                row[1],
                                Integer.parseInt(row[2]),
                                row[3],
                                row[4],
                                row[5]);
                    }else {return new PremiumCustomer(
                            row[0],
                            row[1],
                            Integer.parseInt(row[2]),
                            row[3],
                            row[4],
                            row[5]);}
                }

        ).forEach(CustomerManagement::addCustomer);
        List<String[]> accounts = readFromFile("account");
        accounts.stream()
                .filter(row -> row.length >= 3)
                .map(row->

                        {
                            Customer customer1 = null;
                            try {
                                customer1 = CustomerManagement.findCustomerById(row[1].trim());
                            } catch (InvalidAccountException e) {
                                throw new RuntimeException(e);
                            }
                            double balances = ValidationUtils.parseMoney(row[3]);
                            if(row[2].equalsIgnoreCase("Savings")) {
                                return new SavingsAccount(row[0].trim(),customer1,balances);
                            }else {return new CheckingAccount(row[0].trim(),customer1,balances);}
                        }

                ).forEach(AccountManagement::addAccount);
        List<String[]> transactions = readFromFile("transaction");
        transactions.stream()
                .filter(row -> row.length >= 3)
                .map(row->
                        {
                            double amounts = ValidationUtils.parseMoney(row[3]);
                            double balanceAfters = ValidationUtils.parseMoney(row[4]);
                            return new Transaction(row[0], row[1].trim(), row[2], amounts,balanceAfters,row[5]);

                        }

                ).forEach(TransactionManagement::addTransaction);

        Customer.setCustomerCounter(customers.size());
        Account.setAccountCounter(accounts.size());
        Transaction.setTransactionCounter(transactions.size());

        System.out.printf("%d customers loaded from customer.txt\n",customers.size());
        System.out.printf("%d accounts loaded from account.txt\n",accounts.size());
        System.out.printf("%d transactions loaded from transaction.txt\n",transactions.size());
    }
}
