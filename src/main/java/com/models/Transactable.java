
package com.models;

/*
 * Interface defining the contract for objects that can process financial transactions.
 *
 * This interface provides a standardized way for account types to handle deposits
 * and withdrawals. Implementing classes must provide logic for processing transactions
 * based on their specific business rules (e.g., minimum balance requirements,
 * overdraft limits, etc.).
 */
public interface Transactable {

    /*
     * Processes a financial transaction (deposit or withdrawal) on the implementing object.
     *
     * The implementation should validate the transaction based on account-specific
     * rules before executing. For withdrawals, this may include checking minimum balance
     * requirements, overdraft limits, or other account-specific constraints.
     *
     * @param amount The transaction amount. Must be positive for both deposits and withdrawals.
     * @param type The transaction type, case-insensitive. Expected values: "Deposit" or "Withdrawal"
     * @return true if the transaction was processed successfully, false otherwise
     */
    boolean processTransactions(double amount, String type);
}
