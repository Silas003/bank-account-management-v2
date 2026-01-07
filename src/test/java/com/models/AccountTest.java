package com.models;
import com.models.exceptions.IllegalAmountException;
import com.models.exceptions.InsufficientFundsExceptions;
import com.models.exceptions.OverdraftLimitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account Behavior Tests (ACC + increment, exact messages)")
class AccountTest {

    private SavingsAccount savingsAccount;
    private CheckingAccount checkingAccount;

    @BeforeEach
    void setUpTest() {

        savingsAccount = new SavingsAccount(null, 1000.0);
        checkingAccount = new CheckingAccount(null, 2000.0);
    }

    // ========== ACCOUNT CREATION & NUMBERING ==========

    @Test
    @DisplayName("SavingsAccount initializes with type, status, balance, and ACC00X number")
    void createSavingsAccountTest() {
        assertEquals("Savings", savingsAccount.getAccountType(), "Type should be 'Savings'");
        assertEquals("active", savingsAccount.getStatus(), "Constructor should set status='active'");
        assertEquals(1000.0, savingsAccount.getBalance(), 1e-6, "Initial balance should match");
        assertNotNull(savingsAccount.getAccountNumber(), "Account number should be generated");
        assertTrue(savingsAccount.getAccountNumber().matches("^ACC00\\d+$"),
                "Account number must match 'ACC00<digits>'");
    }

    @Test
    @DisplayName("CheckingAccount initializes with type, status, balance, and ACC00X number")
    void createCheckingAccountTest() {
        assertEquals("Checking", checkingAccount.getAccountType(), "Type should be 'Checking'");
        assertEquals("active", checkingAccount.getStatus(), "Constructor should set status='active'");
        assertEquals(2000.0, checkingAccount.getBalance(), 1e-6, "Initial balance should match");
        assertNotNull(checkingAccount.getAccountNumber(), "Account number should be generated");
        assertTrue(checkingAccount.getAccountNumber().matches("^ACC00\\d+$"),
                "Account number must match 'ACC00<digits>'");
    }

    @Test
    @DisplayName("AccountCounter increments across mixed Savings and Checking creations")
    void accountCounterIncrementsTest() {
        int before = savingsAccount.getAccountCounter();
        SavingsAccount s2 = new SavingsAccount(null, 0.0);
        CheckingAccount c2 = new CheckingAccount(null, 0.0);
        int after = c2.getAccountCounter();
        assertTrue(after >= before + 2, "Counter should increase by 2 after two new accounts");
    }

    @Test
    @DisplayName("setAccountNumber regenerates a new ACC number")
    void setAccountNumberRegeneratesTest() {
        String original = savingsAccount.getAccountNumber();
        savingsAccount.setAccountNumber();
        String regenerated = savingsAccount.getAccountNumber();
        assertNotEquals(original, regenerated, "setAccountNumber should assign a new unique number");
        assertTrue(regenerated.matches("^ACC00\\d+$"), "Regenerated must match 'ACC00<digits>'");
    }

    // ========== TYPE-SPECIFIC PROPERTIES ==========

    @Test
    @DisplayName("SavingsAccount has interestRate 3.5% and minimumBalance 500")
    void savingsAccountPropertiesTest() {
        double interest = savingsAccount.calculateInterest();
        assertEquals(35.0, interest, 1e-6, "Interest should be balance * 3.5%");
        assertEquals(500.0, savingsAccount.getMinimumBalance(), 1e-6, "Minimum balance should be 500");
    }

    @Test
    @DisplayName("CheckingAccount has overdraftLimit=1000 and monthlyFee=10")
    void checkingAccountPropertiesTest() {
        assertEquals(1000.0, checkingAccount.getOverdraftLimit(), 1e-6, "Overdraft limit should be 1000");
        assertEquals(10.0, checkingAccount.getMonthlyFee(), 1e-6, "Monthly fee should be 10");
    }

    // ========== DEPOSIT TESTS (exact messages) ==========

    @Test
    @DisplayName("SavingsAccount deposit valid amount updates balance")
    void depositSavingsValidAmountTest() throws IllegalAmountException {
        boolean result = savingsAccount.deposit(500.0);
        assertTrue(result, "deposit should return true on success");
        assertEquals(1500.0, savingsAccount.getBalance(), 1e-6, "Balance should reflect deposit");
    }

    @Test
    @DisplayName("CheckingAccount deposit valid amount updates balance")
    void depositCheckingValidAmountTest() throws IllegalAmountException {
        boolean result = checkingAccount.deposit(1000.0);
        assertTrue(result, "deposit should return true on success");
        assertEquals(3000.0, checkingAccount.getBalance(), 1e-6, "Balance should reflect deposit");
    }




    // ========== WITHDRAWAL TESTS (exact messages) ==========

    @Test
    @DisplayName("SavingsAccount withdraw valid amount updates balance")
    void withdrawSavingsValidAmountTest() throws IllegalAmountException, InsufficientFundsExceptions {
        savingsAccount.withdraw(200.0);
        assertEquals(800.0, savingsAccount.getBalance(), 1e-6, "Balance should reflect withdrawal");
    }


    @Test
    @DisplayName("CheckingAccount withdraw valid amount within overdraft updates balance")
    void withdrawCheckingValidAmountTest() throws Exception {
        checkingAccount.withdraw(500.0);
        assertEquals(1500.0, checkingAccount.getBalance(), 1e-6, "Balance should reflect withdrawal");
    }

    @Test
    @DisplayName("CheckingAccount exceeding overdraft throws OverdraftLimitException")
    void withdrawCheckingExceedsOverdraftThrowsTest() {
        assertThrows(OverdraftLimitException.class, () -> checkingAccount.withdraw(3201.0));
    }

    @Test
    @DisplayName("CheckingAccount within overdraft limit does not throw and updates balance")
    void withdrawCheckingWithinOverdraftTest() throws Exception {

        checkingAccount.withdraw(2800.0);
        assertEquals(-800.0, checkingAccount.getBalance(), 1e-6, "Balance should become negative within overdraft limit");
    }

    // ========== Overdraft helper (exact messages) ==========

    @Test
    @DisplayName("hasOverdraftLimitExceeded(amount<0) throws IllegalAmountException ")
    void hasOverdraftLimitExceededNegativeAmountTest() {
        assertThrows(OverdraftLimitException.class, () -> checkingAccount.hasOverdraftLimitExceeded(3300));
    }

    @Test
    @DisplayName("hasOverdraftLimitExceeded exceeding limit throws OverdraftLimitException ")
    void hasOverdraftLimitExceededExceedsLimitTest() {
        OverdraftLimitException ex = assertThrows(OverdraftLimitException.class, () -> checkingAccount.hasOverdraftLimitExceeded(3501.0));
        assertEquals("You can't withdraw more than your overdraft limit", ex.getMessage());
    }

    @Test
    @DisplayName("hasOverdraftLimitExceeded within limit returns false")
    void hasOverdraftLimitExceededWithinLimitTest() throws IllegalAmountException, OverdraftLimitException {
        boolean result = checkingAccount.hasOverdraftLimitExceeded(2500.0);
        assertFalse(result, "Method returns false when no exception (within limit)");
    }

    // ========== processTransactions (exact messages & returns) ==========

    @Test
    @DisplayName("SavingsAccount processTransactions Deposit/Withdrawal work; unknown type returns false")
    void savingsProcessTransactionsPathsTest() throws IllegalAmountException, InsufficientFundsExceptions {
        boolean depositResult = savingsAccount.processTransactions(200.0, "Deposit");
        assertTrue(depositResult);
        assertEquals(1200.0, savingsAccount.getBalance(), 1e-6);

        boolean withdrawalResult = savingsAccount.processTransactions(100.0, "Withdrawal");
        assertTrue(withdrawalResult);
        assertEquals(1100.0, savingsAccount.getBalance(), 1e-6);

        boolean unknownResult = savingsAccount.processTransactions(123.0, "Transfer");
        assertFalse(unknownResult, "SavingsAccount returns false for unknown transaction type");
    }

    @Test
    @DisplayName("CheckingAccount processTransactions Deposit/Withdrawal work; unknown type returns false")
    void checkingProcessTransactionsPathsTest() throws IllegalAmountException, OverdraftLimitException {
        boolean depositResult = checkingAccount.processTransactions(300.0, "Deposit");
        assertTrue(depositResult);
        assertEquals(2300.0, checkingAccount.getBalance(), 1e-6);

        boolean withdrawalResult = checkingAccount.processTransactions(200.0, "Withdrawal");
        assertTrue(withdrawalResult);
        assertEquals(2100.0, checkingAccount.getBalance(), 1e-6);

        boolean unknownResult = checkingAccount.processTransactions(123.0, "Other");
        assertFalse(unknownResult, "CheckingAccount returns false for unknown transaction type");
    }
}
