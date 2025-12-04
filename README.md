# Bank Account Management System

A **Java-based console application** for managing bank accounts, customers, and transactions. This system supports **Savings** and **Checking** accounts for **Regular** and **Premium** customers, providing features like account creation, transaction processing, and transaction history viewing.

---

## Features
-  **Account Management**
- Create accounts for Regular and Premium customers.
- Supports Savings and Checking account types.
- View all accounts with details (balance, status, account-specific info).

-  **Transaction Management**
- Process deposits and withdrawals.
- Validate transactions with overdraft and minimum balance rules.
- Maintain transaction history with timestamps.

-  **Customer Management**
- Regular and Premium customer tiers.
- Premium customers enjoy fee waivers and higher minimum balance requirements.

-  **Input Validation**
- Robust validation for names, age, contact, addresses, and transaction amounts.
- Retry logic for invalid inputs.

---

## How It Works
1. **Start the Application**
    - Run `Main.java` to launch the console-based interface.
    - The main menu provides options for account creation, viewing accounts, processing transactions, and viewing transaction history.

2. **Account Creation**
    - Collects customer details (name, age, contact, address).
    - Prompts for customer type (Regular or Premium) and account type (Savings or Checking).
    - Validates initial deposit based on account and customer type.

3. **Transaction Processing**
    - Validates account number and transaction type.
    - Displays transaction summary for confirmation.
    - Updates account balance and records transaction with timestamp.

4. **Transaction History**
    - Displays all transactions for a given account.
    - Shows totals for deposits, withdrawals, and net change.

---

## Business Rules
- **Savings Account**
    - Minimum balance: $500
    - Interest rate: 3.5%

- **Checking Account**
    - Overdraft limit: $1,000
    - Monthly fee: $10 (waived for Premium customers)

- **Premium Customer**
    - Minimum initial deposit: $10,000
    - Monthly fees waived

---

## Technologies Used
- **Java SE**
- **OOP Principles** (Inheritance, Polymorphism, Encapsulation, Abstraction)
- **Collections** (Array, ArrayList)
- **Date & Time API** (for transaction timestamps)

---

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/bank-account-management.git
   ```
2. Navigate to the project directory:
   ```bash
   cd bank-account-management
   ```
3. Compile and run:
   ```bash
   javac Main.java
   java Main
   ```

---

## Future Enhancements
-  Replace fixed-size arrays with dynamic collections or database integration.
-  Implement monthly fee deduction logic.
-  Add interest calculation and periodic updates.
-  Improve UI with a graphical interface or web-based front end.

---

