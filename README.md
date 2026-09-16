# E-Wallet System

A Java console application that simulates a small electronic wallet. It allows users to create accounts, log in, manage balances, transfer funds to registered users, and review transactions during the current application session.

Developed by **Ibraheem Daif**.

## Overview

This is a learning-focused project built to apply core Java concepts in a practical application. It uses object-oriented design, a layered project structure, collections, custom exceptions, enums, streams, and console input/output.

The application is not intended for real financial use. Account and transaction data are stored in memory and are lost when the application closes.

## Core Features

- **Authentication and accounts:** Register, log in, log out, update account details, and delete an account after password verification.
- **Wallet operations:** Deposit and withdraw funds with invalid-amount and insufficient-balance validation.
- **Fund transfers:** Transfer funds to another registered phone number while preventing self-transfers and unknown recipients.
- **Account summary:** Display the current balance and totals for deposits and withdrawals.
- **Transaction history:** Display recorded transactions with an ID, type, amount, timestamp, and transfer parties when applicable.
- **Settings:** Change the account name or password.
- **Console experience:** Read full-line text input, validate menu choices, clear the terminal, and mask password input when `System.console()` is available.

## Application Flow

```text
Start
  │
  ├── Register or log in
  │
  └── Wallet menu
       ├── Deposit
       ├── Withdraw
       ├── Transfer
       ├── View history
       ├── Settings
       │    ├── Change password
       │    ├── Change name
       │    └── Delete account
       └── Log out or exit
```

## Architecture

The project follows a **layered structure** rather than a strict MVC implementation:

- `model` contains the core data objects.
- `service` contains user and wallet business logic.
- `ui` contains formatted console screens.
- `controller` coordinates user actions, screens, and services.
- `exception` contains domain-specific runtime exceptions.
- `console` contains shared input and terminal utilities.

Services are instantiated in `Main` and passed to the classes that need them. This avoids storing application services in global static fields.

## Project Structure

```text
src/
├── Main.java
├── console/
│   └── ConsoleAssistant.java
├── controller/
│   └── AppController.java
├── exception/
│   ├── AppExceptions.java
│   ├── AuthenticationException.java
│   ├── InsufficientBalanceException.java
│   ├── InvalidAmountException.java
│   └── InvalidTransferException.java
├── model/
│   ├── Transaction.java
│   ├── TransactionStatus.java
│   ├── TransactionType.java
│   ├── User.java
│   └── Wallet.java
├── service/
│   ├── UserService.java
│   └── WalletService.java
└── ui/
    ├── AuthMenuPage.java
    ├── DepositPage.java
    ├── HistoryPage.java
    ├── HomePage.java
    ├── LoginPage.java
    ├── MainPage.java
    ├── MenuPage.java
    ├── Page.java
    ├── RegisterPage.java
    ├── SettingPage.java
    ├── TransferPage.java
    └── WithdrawPage.java
```

## Java Concepts Used

| Concept | Implementation |
| --- | --- |
| Object-oriented programming | `User`, `Wallet`, and `Transaction` model classes |
| Encapsulation | Private fields with controlled methods such as password verification |
| Collections | `HashMap` for users and `ArrayList` for transaction records |
| Custom exceptions | Authentication, invalid amount, balance, and transfer validation |
| Enums | Transaction type and transaction status |
| Streams | Deposit and withdrawal total calculations |
| Dependency injection | Services are created in `Main` and supplied to dependent classes |

## Requirements

- Java 17 or a newer compatible version.
- PowerShell, Command Prompt, or another terminal.

## Build and Run

Open PowerShell in the project directory. Recompile after every source-code change:

```powershell
javac -encoding UTF-8 -d "out\production\E-wallet System" (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName)
```

Run the application:

```powershell
java -cp "out\production\E-wallet System" Main
```

If the default Java installation does not work, configure JDK 17 for the current PowerShell session:

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.18.8-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

Then run the compile and execution commands again.

## Current Limitations

- Users and transactions are stored only in memory; all data is lost when the program closes.
- The project does not use a database or file storage.
- Passwords are not securely hashed. Masking input only hides the characters while typing in supported terminals.
- Money values use `double`; a production financial system should use `BigDecimal`.
- There are no automated tests at the moment.

## Future Improvements

- Persist data using files or a database.
- Add JUnit tests for authentication and wallet operations.
- Replace `double` with `BigDecimal` for money values.
- Hash passwords with a secure password-hashing algorithm.
- Add an Admin role for user management and transaction review.
