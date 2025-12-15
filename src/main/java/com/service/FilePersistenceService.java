package com.service;
import com.models.Account;
import com.models.Customer;
import com.models.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePersistenceService {
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
        return String.format("%s,%s,%s,%.2f,%s\n",
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
}
