# Bank Account Management System

A **Java-based console application** for managing bank accounts, customers, and transactions. This system supports **Savings** and **Checking** accounts for **Regular** and **Premium** customers, offering features like account creation, transaction processing, transaction history viewing, **account statements**, and **automated test execution**.

---

## Features

###  Account Management
- Create accounts for **Regular** and **Premium** customers.
- Supports **Savings** and **Checking** account types.
- View all accounts with details (balance, status, and account-specific info).

###  Transaction Management
- Process **deposits** and **withdrawals**.
- Validate transactions with **overdraft** and **minimum balance** rules.
- Maintain transaction history with **timestamps**.

###  Customer Management
- Manage **Regular** and **Premium** customer tiers.
- Premium customers enjoy **fee waivers** and **higher minimum balance requirements**.

###  Account Statements
- Generate **detailed account statements** showing:
  - Customer details
  - Account type and balance
  - Complete transaction history with timestamps
  - Totals for deposits, withdrawals, and net change

###  Automated Testing
- Run **unit tests** and **integration tests** directly from the main menu using:
  ```java
  CustomRunner.runAllTestsInPackage();
  ```
- Ensures system reliability and correctness.

###  Input Validation
- Robust validation for **names, age, contact, addresses, and transaction amounts**.
- Retry logic for invalid inputs.

---

## How It Works

1. **Start the Application**
   - Run `TestRunner.java` in the `test` directory to launch the console interface.
   - The main menu provides options for:
     - **Manage Accounts** (create/view)
     - **Perform Transactions**
     - **Generate Account Statements**
     - **Run Tests**
     - **Exit**

2. **Account Creation**
   - Collect customer details (**name, age, contact, address**).
   - Prompt for **customer type** (Regular or Premium) and **account type** (Savings or Checking).
   - Validate initial deposit based on account and customer type.

3. **Transaction Processing**
   - Validate account number and transaction type.
   - Display transaction summary for confirmation.
   - Update account balance and record transaction with timestamp.

4. **Account Statement Generation**
   - Displays all transactions for a given account.
   - Shows totals for deposits, withdrawals, and net change.
   - Includes customer and account details for clarity.

5. **Running Tests**
   - Select **Run Tests** from the main menu.
   - Executes all test cases in the project using `CustomRunner`.

---

## Business Rules

- **Savings Account**
  - Minimum balance: **$500**
  - Interest rate: **3.5%**

- **Checking Account**
  - Overdraft limit: **$1,000**
  - Monthly fee: **$10** (waived for Premium customers)

- **Premium Customer**
  - Minimum initial deposit: **$10,000**
  - Monthly fees waived

---

## Technologies Used
- **Java SE**
- **OOP Principles** (Inheritance, Polymorphism, Encapsulation, Abstraction)
- **Collections** (Array, ArrayList)
- **Date & Time API** (for transaction timestamps)
- **JUnit** (for automated testing)

---

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/Silas003/bank-account-management-v2.git
   ```
2. Navigate to the project directory:
   ```bash
   cd bank-account-management-v2/src/test
   ```
3. Compile and run:
   ```bash
   javac TestRunner.java
   java TestRunner
   ```

---

## Future Enhancements
- Replace fixed-size arrays with database integration.
- Implement monthly fee deduction logic.
- Add interest calculation and periodic updates.
- Export account statements to PDF or Excel.

---

### Main Menu Flow
The `AppHandler` class manages the main menu:
- Manage Accounts
- Perform Transaction
- Generate Account Statements
- Run Tests
- Exit

---
