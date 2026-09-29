# 🏦 Bank Account Simulator (Java)

A console banking app showing core Java concepts: classes, encapsulation, collections, custom exceptions, and switch expressions.

## Features
- Open accounts with an initial deposit
- Deposit, withdraw, and transfer between accounts
- Per-account transaction history / statements
- Input validation and a custom `InsufficientFundsException`

## Requirements
- JDK 14+ (uses arrow-style `switch`)

## Build & run
```bash
javac BankApp.java
java BankApp
```

## Sample session
```
=== Bank Menu ===
1) Open account  2) Deposit  3) Withdraw
4) Transfer      5) Statement  6) Exit
Choose: 1
Owner name: Alice
Amount: 500
Created account AC1001 for Alice
```

## Ideas for extension
- Persist accounts to a file or database
- Add savings/checking subclasses with interest
- Add PIN authentication

## License
MIT
