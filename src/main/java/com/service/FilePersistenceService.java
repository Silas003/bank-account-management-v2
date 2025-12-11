package com.service;
import com.models.Account;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
            write.write(convertToString(account));
            write.newLine();
        }

    }

    public static ArrayList<List> readFromFile(String filepath) throws IOException {
        Path path = getOrCreateFile(filepath);
        List<String> readData = Files.readAllLines(path);

//        readData.stream().map(p->Arrays.asList(p.split(",")))
//                .forEach(System.out::println);
        return readData.stream().map(p->Arrays.asList(p.split(",")))
                .collect(Collectors.toCollection(ArrayList::new));
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
