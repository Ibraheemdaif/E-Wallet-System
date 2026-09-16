package service;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import exception.InvalidTransferException;
import model.*;
import java.util.List;

public class WalletService {
    private final UserService userService;
    public WalletService(UserService userService) {
        this.userService = userService;
    }

    public void deposit(User currentUser, double amount) throws InvalidAmountException {
        currentUser.getWallet().deposit(amount);

        Transaction transaction = new Transaction(
                amount,
                currentUser.getPhoneNumber(),
                null,
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCESS
        );
        currentUser.getWallet().addTransaction(transaction);

    }


    public void withdraw(User currentUser, double amount) throws InvalidAmountException{
        currentUser.getWallet().withdraw(amount);

        Transaction transaction = new Transaction(
                amount,
                currentUser.getPhoneNumber(),
                null,
                TransactionType.WITHDRAW,
                TransactionStatus.SUCCESS
        );
        currentUser.getWallet().addTransaction(transaction);

    }

    public void showBalance(User currentUser) {
        System.out.println(currentUser.getWallet().getBalance());
    }

    public void transfer(User sender,String receiverPhone, double amount)
    throws InsufficientBalanceException, InvalidAmountException {
        if (sender.getPhoneNumber().equals(receiverPhone))
            throw new InvalidTransferException("You cannot transfer money to your own wallet.");

        User receiver = userService.findUserByPhoneNumber(receiverPhone);
        if (receiver == null)
            throw new InvalidTransferException("Receiver phone number not found in the system.");

        sender.getWallet().withdraw(amount);
        receiver.getWallet().deposit(amount);

        Transaction transaction = new Transaction(
                amount,
                sender.getPhoneNumber(),
                receiver.getPhoneNumber(),
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS
        );
        sender.getWallet().addTransaction(transaction);
        receiver.getWallet().addTransaction(transaction);

    }

    public List<Transaction> getTransactionHistory(User currentUser) {
       return currentUser.getWallet().getTransactions();
    }

    public double getTotalDepositAmount(User currentUser) {
        return currentUser.getWallet().getTransactions().stream()
                .filter(tx -> tx.getType() == TransactionType.DEPOSIT)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalWithdrawAmount(User currentUser) {
        return currentUser.getWallet().getTransactions().stream()
                .filter(tx -> tx.getType() == TransactionType.WITHDRAW)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
