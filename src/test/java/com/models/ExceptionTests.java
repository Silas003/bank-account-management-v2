
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
    @DisplayName("Checking: processTransactions Withdrawal exceeding overdraft")
    void checkingProcessTransactionsWithdrawalExceedsOverdraftTest() {
        OverdraftLimitException ex = assertThrows(OverdraftLimitException.class, () -> checkingAccount.processTransactions(1600.0, "Withdrawal"));
        assertEquals("You can't withdraw more than your overdraft limit", ex.getMessage());
    }

    @Test
    @DisplayName("Checking: processTransactions unknown type returns false")
    void checkingProcessTransactionsUnknownTypeTest() throws Exception {
        boolean result = checkingAccount.processTransactions(100.0, "other");
        assertFalse(result);
    }

    // ===================== SavingsAccount Tests =====================

    @Test
    @DisplayName("Savings: withdraw more than balance -> InsufficientFundsExceptions")
    void savingsWithdrawBelowZeroTest() {
        InsufficientFundsExceptions ex = assertThrows(InsufficientFundsExceptions.class, () -> savingsAccount.withdraw(2000.0));
        assertEquals("You can't withdraw below minimum Balance of $500.Current Balance: $"+savingsAccount.getBalance(), ex.getMessage());
    }

    @Test
    @DisplayName("Savings: withdraw from zero balance -> CURRENT behavior allows negative")
    void savingsWithdrawFromZeroTest() throws Exception {
        SavingsAccount zero = new SavingsAccount(null, 0.0);
        assertThrows(Exception.class, () -> zero.withdraw(25.0));
    }


    @Test
    @DisplayName("Savings: processTransactions unknown type returns false")
    void savingsProcessTransactionsUnknownTypeTest() throws Exception {
        boolean result = savingsAccount.processTransactions(100.0, "other");
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
