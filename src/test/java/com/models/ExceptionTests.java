
package com.models;

import com.models.exceptions.IllegalAmountException;
import com.models.exceptions.InsufficientFundsExceptions;
import com.models.exceptions.OverdraftLimitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exception Tests")
class ExceptionTests {

    private SavingsAccount savingsAccount;
    private CheckingAccount checkingAccount;

    @BeforeEach
    void setUpTest() {
        savingsAccount = new SavingsAccount(null, 1000.0);
        checkingAccount = new CheckingAccount(null, 500.0);
    }

    // ===================== CheckingAccount Tests =====================

    @Test
    @DisplayName("Checking: deposit(amount<=0) -> IllegalAmountException")
    void checkingDepositInvalidAmountTest() {
        IllegalAmountException exZero = assertThrows(IllegalAmountException.class, () -> checkingAccount.deposit(0.0));
        assertEquals("Deposit amount cannot be zero", exZero.getMessage());

        IllegalAmountException exNeg = assertThrows(IllegalAmountException.class, () -> checkingAccount.deposit(-1.0));
        assertEquals("Deposit amount cannot be zero", exNeg.getMessage());
    }

    @Test
    @DisplayName("Checking: withdraw(amount<=0) -> IllegalAmountException")
    void checkingWithdrawInvalidAmountTest() {
        IllegalAmountException exZero = assertThrows(IllegalAmountException.class, () -> checkingAccount.withdraw(0.0));
        assertEquals("Withdrawal amount cannot be zero", exZero.getMessage());

        IllegalAmountException exNeg = assertThrows(IllegalAmountException.class, () -> checkingAccount.withdraw(-5.0));
        assertEquals("Withdrawal amount cannot be zero", exNeg.getMessage());
    }

    @Test
    @DisplayName("Checking: hasOverdraftLimitExceeded(amount<0) -> IllegalAmountException")
    void checkingHasOverdraftNegativeAmountTest() {
        IllegalAmountException ex = assertThrows(IllegalAmountException.class, () -> checkingAccount.hasOverdraftLimitExceeded(-10.0));
        assertEquals("Amount cannot be less than 0", ex.getMessage());
    }

    @Test
    @DisplayName("Checking: withdraw exceeding overdraft -> OverdraftLimitException")
    void checkingWithdrawExceedOverdraftTest() {
        OverdraftLimitException ex = assertThrows(OverdraftLimitException.class, () -> checkingAccount.withdraw(1600.0));
        assertEquals("You can't withdraw more than your overdraft limit", ex.getMessage());
    }

    @Test
    @DisplayName("Checking: within overdraft limit -> no exception")
    void checkingWithdrawWithinOverdraftTest() throws Exception {
        checkingAccount.withdraw(1200.0);
        assertEquals(-700.0, checkingAccount.getBalance(), 1e-6);
    }

    @Test
    @DisplayName("Checking: processTransactions Deposit invalid amount")
    void checkingProcessTransactionsDepositInvalidAmountTest() {
        IllegalAmountException exZero = assertThrows(IllegalAmountException.class, () -> checkingAccount.processTransactions(0.0, "Deposit"));
        assertEquals("Deposit amount cannot be zero", exZero.getMessage());

        IllegalAmountException exNeg = assertThrows(IllegalAmountException.class, () -> checkingAccount.processTransactions(-10.0, "Deposit"));
        assertEquals("Deposit amount cannot be zero", exNeg.getMessage());
    }

    @Test
    @DisplayName("Checking: processTransactions Withdrawal exceeding overdraft")
    void checkingProcessTransactionsWithdrawalExceedsOverdraftTest() {
        OverdraftLimitException ex = assertThrows(OverdraftLimitException.class, () -> checkingAccount.processTransactions(1600.0, "Withdrawal"));
        assertEquals("You can't withdraw more than your overdraft limit", ex.getMessage());
    }

    @Test
    @DisplayName("Checking: processTransactions unknown type returns false")
    void checkingProcessTransactionsUnknownTypeTest() throws Exception {
        boolean result = checkingAccount.processTransactions(100.0, "Transfer");
        assertFalse(result);
    }

    // ===================== SavingsAccount Tests =====================

    @Test
    @DisplayName("Savings: deposit(amount<=0) -> IllegalAmountException")
    void savingsDepositInvalidAmountTest() {
        IllegalAmountException exZero = assertThrows(IllegalAmountException.class, () -> savingsAccount.deposit(0.0));
        assertEquals("Deposit amount cannot be zero", exZero.getMessage());

        IllegalAmountException exNeg = assertThrows(IllegalAmountException.class, () -> savingsAccount.deposit(-1.0));
        assertEquals("Deposit amount cannot be zero", exNeg.getMessage());
    }

    @Test
    @DisplayName("Savings: withdraw(amount<=0) -> IllegalAmountException")
    void savingsWithdrawInvalidAmountTest() {
        IllegalAmountException exZero = assertThrows(IllegalAmountException.class, () -> savingsAccount.withdraw(0.0));
        assertEquals("Deposit amount cannot be zero", exZero.getMessage());

        IllegalAmountException exNeg = assertThrows(IllegalAmountException.class, () -> savingsAccount.withdraw(-5.0));
        assertEquals("Deposit amount cannot be zero", exNeg.getMessage());
    }

    @Test
    @DisplayName("Savings: withdraw more than balance -> InsufficientFundsExceptions")
    void savingsWithdrawBelowZeroTest() {
        InsufficientFundsExceptions ex = assertThrows(InsufficientFundsExceptions.class, () -> savingsAccount.withdraw(2000.0));
        assertEquals("You can't withdraw below a balance of 0", ex.getMessage());
    }

    @Test
    @DisplayName("Savings: withdraw from zero balance -> CURRENT behavior allows negative")
    void savingsWithdrawFromZeroTest() throws Exception {
        SavingsAccount zero = new SavingsAccount(null, 0.0);
        assertDoesNotThrow(() -> zero.withdraw(25.0));
        assertEquals(-25.0, zero.getBalance(), 1e-6);
    }

    @Test
    @DisplayName("Savings: processTransactions Deposit invalid amount")
    void savingsProcessTransactionsDepositInvalidAmountTest() {
        IllegalAmountException exZero = assertThrows(IllegalAmountException.class, () -> savingsAccount.processTransactions(0.0, "Deposit"));
        assertEquals("Deposit amount cannot be zero", exZero.getMessage());

        IllegalAmountException exNeg = assertThrows(IllegalAmountException.class, () -> savingsAccount.processTransactions(-10.0, "Deposit"));
        assertEquals("Deposit amount cannot be zero", exNeg.getMessage());
    }

    @Test
    @DisplayName("Savings: processTransactions Withdrawal below zero")
    void savingsProcessTransactionsWithdrawalBelowZeroTest() {
        SavingsAccount acc = new SavingsAccount(null, 100.0);
        InsufficientFundsExceptions ex = assertThrows(InsufficientFundsExceptions.class, () -> acc.processTransactions(150.0, "Withdrawal"));
        assertEquals("You can't withdraw below a balance of 0", ex.getMessage());
    }

    @Test
    @DisplayName("Savings: processTransactions unknown type returns false")
    void savingsProcessTransactionsUnknownTypeTest() throws Exception {
        boolean result = savingsAccount.processTransactions(100.0, "Transfer");
        assertFalse(result);
    }

    // ===================== Exception Message Constructor Tests =====================

    @Test
    @DisplayName("IllegalAmountException message consistency")
    void illegalAmountMessageCtorTest() {
        IllegalAmountException ex = new IllegalAmountException("Amount must be positive");
        assertEquals("Amount must be positive", ex.getMessage());
    }

    @Test
    @DisplayName("InsufficientFundsExceptions message consistency")
    void insufficientFundsMessageCtorTest() {
        InsufficientFundsExceptions ex = new InsufficientFundsExceptions("Not enough funds");
        assertEquals("Not enough funds", ex.getMessage());
    }

    @Test
    @DisplayName("OverdraftLimitException message consistency")
    void overdraftLimitMessageCtorTest() {
        OverdraftLimitException ex = new OverdraftLimitException("Limit exceeded");
        assertEquals("Limit exceeded", ex.getMessage());
    }
}
