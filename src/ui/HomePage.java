package ui;

import model.User;
import service.WalletService;

public class HomePage implements Page {
    private final User currentUser;
    private final WalletService walletService;

    public HomePage(User currentUser, WalletService walletService ) {
        this.currentUser = currentUser;
        this.walletService = walletService;
    }

    @Override
    public void show() {
        displayHeader("Welcome, " + currentUser.getName());
        System.out.println("  Current Balance : " + currentUser.getWallet().getBalance());
        System.out.println("  Total Deposits  : " + walletService.getTotalDepositAmount(currentUser));
        System.out.println("  Total Withdraws : " + walletService.getTotalWithdrawAmount(currentUser) + "\n\n");

    }
}