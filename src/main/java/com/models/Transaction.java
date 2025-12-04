
package com.models;

/*
 * Represents a financial transaction in the bank account management system.
 *
 * This class encapsulates all information related to a single transaction,
 * including the transaction type (deposit or withdrawal), amount, account involved,
 * and timestamp.
 */
public class Transaction {

    // Static counter for generating unique transaction IDs
    static int transactionCounter;

    // Unique identifier for this transaction in format "TNX00" + counter
    private String transactionId;

    // Transaction type: "Deposit" or "Withdrawal"
    private String type;

    // Account number associated with this transaction
    private String accountNumber;

    // Transaction amount in dollars
    private double amount;

    // Account balance after this transaction was processed
    private double balanceAfter;

    // Timestamp when the transaction occurred (formatted as string)
    private String timeStamp;

    /*
     * Returns a string representation of the transaction.
     * Currently returns only the account number.
     */
    @Override
    public String toString() {
        return String.format("%s", getAccountNumber());
    }

    // Retrieves the unique transaction identifier
    public String getTransactionId() {
        return transactionId;
    }

    // Generates a unique transaction ID based on the current transaction counter
    private void generateUniqueId() {
        this.transactionId = "TNX00" + ++transactionCounter;
    }

    /*
     * Retrieves the transaction type.
     * Note: There is a typo in the method name (TranssactionType instead of TransactionType).
     */
    public String getTranssactionType() {
        return type;
    }

    // Retrieves the account number associated with this transaction
    public String getAccountNumber() {
        return this.accountNumber;
    }

    // Retrieves the transaction amount
    public double getAmount() {
        return this.amount;
    }

    /*
     * Retrieves the account balance after this transaction was processed.
     * Represents the account's balance immediately after the transaction.
     */
    public double getBalanceAfter() {
        return this.balanceAfter;
    }

    // Retrieves the transaction type
    public String getType() {
        return this.type;
    }

    // Retrieves the timestamp when the transaction occurred
    public String getTimeStamp() {
        return this.timeStamp;
    }

    // Sets the account number associated with this transaction
    private void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Sets the transaction amount
    private void setAmount(double amount) {
        this.amount = amount;
    }

    // Sets the account balance after the transaction
    private void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    // Sets the transaction type
    private void setType(String type) {
        this.type = type;
    }

    /*
     * Default constructor that initializes a transaction and generates a unique ID.
     * Increments the static transaction counter.
     */
    Transaction() {
        generateUniqueId();
    }

    // Sets the timestamp for when the transaction occurred
    private void setTimeStamp(String dateTime) {
        this.timeStamp = dateTime;
    }

    /*
     * Constructs a transaction with all required information.
     * Creates a new transaction record with the provided details.
     */
    public Transaction(String accountNumber, String type, double amount, double balanceAfter, String dateTime) {
        this();
        setAccountNumber(accountNumber);
        setAmount(amount);
        setType(type);
        setBalanceAfter(balanceAfter);
        setTimeStamp(dateTime);
    }

    /*
     * Returns this transaction object for display purposes.
     */
    public Transaction displayTransactionDetails() {
        return this;
    }
}
