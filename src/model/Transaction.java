package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private String id;
    private String sender;
    private String receiver;
    private double amount;
    private final TransactionType type;
    private final TransactionStatus status;
    private final LocalDateTime dateTime;

    public Transaction(double amount, String sender, String receiver, TransactionType type, TransactionStatus status) {
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.receiver = receiver;
        this.sender = sender;
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.dateTime = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");
        String formattedDate = dateTime.format(formatter);

        if (type == TransactionType.TRANSFER)
            return "[ID]." + id + "  [Type]." + type + "  [Amount]." + amount
                    + "  [From] : " + sender + "  [To] : " + receiver
                    + "  [Date]." + formattedDate;

        return "[ID]." + id + "  [Type]." + type + "  [Amount]." + amount + "  [Date]." + formattedDate;

    }
}
