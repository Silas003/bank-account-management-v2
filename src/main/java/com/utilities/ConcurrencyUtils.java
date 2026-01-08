package com.utilities;
import com.models.Account;
import com.models.exceptions.InsufficientFundsExceptions;
import com.models.exceptions.InvalidAccountException;
import com.models.exceptions.OverdraftLimitException;
import com.service.AccountManagement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyUtils implements Runnable{
    private Account account;
    private String transactionType;

    public ConcurrencyUtils(Account account,String transactionType){
        this.account = account;
        this.transactionType = transactionType;
    }
    public void run(){
        double amount = new Random().nextDouble(1000);
        String message = transactionType.equalsIgnoreCase("Deposit") ?
                String.format("%s %sing %.2f to %s\n",Thread.currentThread().getName(),transactionType,amount,account.getAccountNumber()):
                String.format("%s %sing %.2f from %s\n",Thread.currentThread().getName(),transactionType,amount,account.getAccountNumber());
        System.out.printf(message);
        try {
            account.processTransactions(amount,transactionType,null);
        } catch (OverdraftLimitException e) {
            throw new RuntimeException(e);
        }
    }

    public static void simulateConcurrentTransactions()  {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try{
            Account account = AccountManagement.findAccount("acc001");
            executorService.submit(new ConcurrencyUtils(account,"Deposit"));
            executorService.submit(new ConcurrencyUtils(account,"Withdraw"));
            executorService.submit(new ConcurrencyUtils(account,"Deposit"));
            executorService.submit(new ConcurrencyUtils(account,"Withdraw"));
            executorService.submit(new ConcurrencyUtils(account,"Deposit"));



        } catch (InvalidAccountException iae){
            System.out.println(iae.getMessage());
        }finally {
            executorService.shutdown();
            System.out.println("Thread-safe opeartions completed successfully.");
//            System.out.printf("Final Balance for ACC001: $%.2f",account.getBalance());

        }

    }
}
