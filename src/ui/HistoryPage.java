package ui;

import model.Transaction;
import java.util.List;

public class HistoryPage implements Page {
    private final List<Transaction> transactions;

    public HistoryPage(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void show() {
        displayHeader("Transaction History");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        transactions.forEach(System.out::println);
    }
}