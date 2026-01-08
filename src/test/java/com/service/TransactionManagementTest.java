
package com.service;

import com.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransactionManagement Tests")
class TransactionManagementTest {

    private TransactionManagement management;

    @BeforeEach
    void setUpTest() {
        management = new TransactionManagement();
        // Clear transactions to ensure clean state for each test
        management.transactions.clear();
        // Note: transactionCount is private, so we can't reset it directly
        // Tests will need to account for any existing count or use relative counts
    }

    // Helper to create domain-relevant transactions with explicit fields
    private Transaction newTransaction(
            String accountNumber,
            String type,
            double amount,
            double balanceAfter,
            String timestamp
    ) {
        return new Transaction(accountNumber, type, amount, balanceAfter, timestamp);
    }

    // ===================== addTransaction =====================

    @Test
    @DisplayName("addTransaction should store transaction and increment count")
    void addTransactionStoresAndCountsTest() {
        Transaction initialDepositForAcc001 =
                newTransaction("ACC001", "Deposit", 100.0, 1100.0, "2025-12-08T09:00:00Z");
        Transaction withdrawalForAcc001 =
                newTransaction("ACC001", "Withdrawal", 50.0, 1050.0, "2025-12-08T09:05:00Z");

        int initialCount = management.getTransactionCount();
        int initialSize = management.transactions.size();

        management.addTransaction(initialDepositForAcc001);
        assertEquals(initialCount + 1, management.getTransactionCount(), "Count should increase by 1 after first add");
        assertEquals(initialSize + 1, management.transactions.size(), "Transactions list should grow by 1");
        assertTrue(management.transactions.contains(initialDepositForAcc001), "Transactions list should contain the first transaction");

        management.addTransaction(withdrawalForAcc001);
        assertEquals(initialCount + 2, management.getTransactionCount(), "Count should increase by 2 after second add");
        assertEquals(initialSize + 2, management.transactions.size(), "Transactions list should grow by 2");
        assertTrue(management.transactions.contains(withdrawalForAcc001), "Transactions list should contain the second transaction");
    }

    @Test
    @DisplayName("addTransaction should allow up to capacity (200 transactions)")
    void addTransactionCapacityBoundaryAllows200Test() {
        int initialCount = management.getTransactionCount();
        int initialSize = management.transactions.size();

        // Add transactions up to the limit relative to current count
        int transactionsToAdd = 200 - initialCount;
        for (int index = 0; index < transactionsToAdd; index++) {
            Transaction depositTx =
                    newTransaction("ACC" + index, "Deposit", index, index + 100.0, "2025-12-08T09:00:00Z");
            management.addTransaction(depositTx);
        }
        assertEquals(200, management.getTransactionCount(), "Count should be 200 after filling capacity");
        assertTrue(management.transactions.size() >= 200, "Transactions list should have at least 200 items");
    }



    // ===================== viewTransactionByAccount =====================

    @Test
    @DisplayName("viewTransactionByAccount should return only matching account transactions")
    void viewTransactionByAccountFiltersTest() {
        // Use unique account numbers to avoid conflicts with other tests
        String testAcc123 = "TEST-ACC123";
        String testAcc999 = "TEST-ACC999";
        String testAcc888 = "TEST-ACC888";

        Transaction depositTxForAcc123 =
                newTransaction(testAcc123, "Deposit", 200.0, 1200.0, "2025-12-08T09:00:00Z");
        Transaction depositTxForAcc999 =
                newTransaction(testAcc999, "Deposit", 50.0, 1050.0, "2025-12-08T09:02:00Z");
        Transaction withdrawalTxForAcc123 =
                newTransaction(testAcc123, "Withdrawal", 75.0, 1125.0, "2025-12-08T09:04:00Z");
        Transaction smallDepositTxForAcc888 =
                newTransaction(testAcc888, "Deposit", 10.0, 1010.0, "2025-12-08T09:06:00Z");

        management.addTransaction(depositTxForAcc123);
        management.addTransaction(depositTxForAcc999);
        management.addTransaction(withdrawalTxForAcc123);
        management.addTransaction(smallDepositTxForAcc888);

        List<Transaction> transactionsForAcc123 = management.viewTransactionByAccount("ACC123");
        assertEquals(2, transactionsForAcc123.size(), "ACC123 should have exactly 2 transactions");
        assertTrue(transactionsForAcc123.contains(depositTxForAcc123), "List should include the deposit for ACC123");
        assertTrue(transactionsForAcc123.contains(withdrawalTxForAcc123), "List should include the withdrawal for ACC123");

        List<Transaction> transactionsForAcc999 = management.viewTransactionByAccount("ACC999");
        assertEquals(1, transactionsForAcc999.size(), "ACC999 should have exactly 1 transaction");
        assertTrue(transactionsForAcc999.contains(depositTxForAcc999), "List should include the deposit for ACC999");
    }

    @Test
    @DisplayName("viewTransactionByAccount should return empty list when no matches")
    void viewTransactionByAccountNoMatchesReturnsEmptyTest() {
        Transaction depositTxForAcc001 =
                newTransaction("ACC001", "Deposit", 100.0, 1100.0, "2025-12-08T09:00:00Z");
        Transaction withdrawalTxForAcc002 =
                newTransaction("ACC002", "Withdrawal", 50.0, 950.0, "2025-12-08T09:05:00Z");

        management.addTransaction(depositTxForAcc001);
        management.addTransaction(withdrawalTxForAcc002);

        List<Transaction> transactionsForUnknownAcc = management.viewTransactionByAccount("UNKNOWN");
        assertNotNull(transactionsForUnknownAcc, "Should return a non-null list");
        assertTrue(transactionsForUnknownAcc.isEmpty(), "List should be empty when there are no matches");
    }

    // ===================== getTransactionCount =====================

    @Test
    @DisplayName("getTransactionCount reflects number of added transactions")
    void getTransactionCountReflectsAddsTest() {
        int initialCount = management.getTransactionCount();

        Transaction depositTxForAccA =
                newTransaction("TEST-ACC-A", "Deposit", 10.0, 1010.0, "2025-12-08T09:00:00Z");
        management.addTransaction(depositTxForAccA);
        assertEquals(initialCount + 1, management.getTransactionCount(), "Count should increase by 1 after one add");

        Transaction depositTxForAccB =
                newTransaction("TEST-ACC-B", "Deposit", 20.0, 1020.0, "2025-12-08T09:01:00Z");
        management.addTransaction(depositTxForAccB);
        assertEquals(initialCount + 2, management.getTransactionCount(), "Count should increase by 2 after two adds");
    }

    // ===================== Defensive / Edge considerations =====================

    @Test
    @DisplayName("viewTransactionByAccount handles zero transactions gracefully")
    void viewTransactionByAccountWhenEmptyTest() {
        List<Transaction> transactionsForAnyAcc = management.viewTransactionByAccount("ANY");
        assertNotNull(transactionsForAnyAcc, "Should return a non-null list even when empty");
        assertTrue(transactionsForAnyAcc.isEmpty(), "No transactions yet => empty result");
    }

    @Test
    @DisplayName("addTransaction stores exact instance; toString returns account number")
    void addTransactionStoresInstanceAndToStringTest() {
        Transaction depositTxForAcc777 =
                newTransaction("ACC777", "Deposit", 300.0, 1300.0, "2025-12-08T09:11:00Z");

        management.addTransaction(depositTxForAcc777);

        // Find the transaction in the list (may not be at index 0 if other tests added transactions)
        assertTrue(management.transactions.contains(depositTxForAcc777),
                "Stored instance should be in the transactions list");
        Transaction found = management.transactions.stream()
                .filter(t -> t.getAccountNumber().equals("ACC777"))
                .findFirst()
                .orElse(null);
        assertNotNull(found, "Transaction should be found");
        assertEquals("ACC777", found.toString(),
                "toString() should return the account number");
    }
}
