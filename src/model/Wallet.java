package model;
import java.util.*;
import exception.*;


public class Wallet {
    private double balance;
    private final List<Transaction> transactions;

    public Wallet() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return this.balance;
    }
    public void deposit (double amount) {
        if (amount <= 0.0)
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        this.balance += amount;
    }

    public void withdraw (double amount) {
        if (amount <= 0)
            throw new InvalidAmountException("Withdraw amount must be greater than zero.");
        if (balance < amount)
            throw new InsufficientBalanceException("Insufficient balance. Current balance: " + this.balance);
        balance -= amount;
    }

    public void addTransaction (Transaction transaction) {
        if (transaction != null)
            this.transactions.add(transaction);
    }

    public  List<Transaction> getTransactions () {
        return Collections.unmodifiableList(transactions);
    }



}
