package com.service;
import com.models.Account;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

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
        Files.write(path,List.of(convertToString(account)),StandardCharsets.UTF_8,StandardOpenOption.APPEND);
    }

    public static void readFromFile(String filepath) throws IOException{
        Path path = getOrCreateFile(filepath);
        List<String> readData = Files.readAllLines(path);
        
        readData.stream().map(p->p.trim().split(","))
                .forEach(p-> System.out.println(Arrays.toString(p)));
    }
    private static String convertToString(Account account){
        return String.format("%s,%s,%s,$%.2f,%s,%s",
                account.getAccountNumber(),
                account.getCustomer(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getAccountSpecificDetails());
    }
}
