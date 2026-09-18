package controller;

import console.ConsoleAssistant;
import exception.AppExceptions;
import exception.AuthenticationException;
import model.User;
import service.UserService;
import service.WalletService;
import ui.*;

public class AppController {
    private User currentUser;
    private final UserService userService;
    private final WalletService walletService;

    public AppController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    public void run() {
        new MainPage().show();
        ConsoleAssistant.pause();
        while (true) {
            if (currentUser == null) {
                ConsoleAssistant.clear();
                handleAuthMenu();
            } else {
                handleHomeMenu();
            }

        }
    }

    private void handleAuthMenu() {
        new AuthMenuPage().show();
        int choice = ConsoleAssistant.readIntInRange("\n \t Choose an Option : ", 0, 2);
        ConsoleAssistant.clear();
        switch (choice) {
            case 1 -> handleLogin();
            case 2 -> handleRegister();
            case 0 -> System.exit(0);
        }
    }

    private void handleLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.show();
        String phoneNumber = ConsoleAssistant.readString("\n\tEnter phone number : ");
        String password = ConsoleAssistant.readPassword("\n\tEnter password : ");

        try {
            currentUser = userService.login(phoneNumber, password);
            loginPage.success("\nLogin Done Successfully.");
        } catch (AppExceptions e) {
            loginPage.error("\n ! Login Failed, Invalid phone number or password" + "\n ! If you don't have one yet, go to the signup page. \n");
        }
    }

    private void handleRegister() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.show();
        String name = ConsoleAssistant.readString("\n\tEnter Your Name : ");
        String phoneNumber = ConsoleAssistant.readString("\n\tEnter Your Phone number : ");
        String password = ConsoleAssistant.readPassword("\n\tEnter Your Password : ");

        try {
            userService.register(name, phoneNumber, password);
            registerPage.success("\nRegistration Successful.");
        } catch (AppExceptions | IllegalArgumentException e) {
            registerPage.error("\nRegistration Failed: " + e.getMessage());
        }
    }

    private void handleHomeMenu() {
        HomePage homePage = new HomePage(currentUser, walletService);
        homePage.show();
        new MenuPage().show();
        int choice = ConsoleAssistant.readIntInRange("\nChoose a Number of option : ", 0, 6);

        switch (choice) {
            case 1 -> handleDeposit();
            case 2 -> handleWithdraw();
            case 3 -> handleTransfer();
            case 4 -> handleHistory();
            case 5 -> currentUser = null;

            case 6 -> handleSettingsMenu();
            case 0 -> System.exit(0);
        }
    }

    private void handleDeposit() {
        DepositPage depositPage = new DepositPage();
        depositPage.show();
        double amount = ConsoleAssistant.readDouble("Enter amount : ");

        try {
            walletService.deposit(currentUser, amount);
            depositPage.success("Deposit successful. New balance: " + currentUser.getWallet().getBalance());
        } catch (AppExceptions e) {
            depositPage.error(e.getMessage());
        }
    }

    private void handleWithdraw() {
        WithdrawPage withdrawPage = new WithdrawPage();
        withdrawPage.show();
        double amount = ConsoleAssistant.readDouble("Enter amount : ");

        try {
            walletService.withdraw(currentUser, amount);
            withdrawPage.success("Withdraw successful. New balance: " + currentUser.getWallet().getBalance());
        } catch (AppExceptions e) {
            withdrawPage.error(e.getMessage());
        }
    }

    private void handleTransfer() {
        TransferPage transferPage = new TransferPage();
        transferPage.show();
        String receiverPhone = ConsoleAssistant.readString("Enter receiver phone number : ");
        double amount = ConsoleAssistant.readDouble("Enter amount : ");

        try {
            walletService.transfer(currentUser, receiverPhone, amount);
            transferPage.success("Transfer successful. New balance: " + currentUser.getWallet().getBalance());
        } catch (AppExceptions e) {
            transferPage.error(e.getMessage());
        }
    }

    private void handleHistory() {
        ConsoleAssistant.clear();
        new HistoryPage(walletService.getTransactionHistory(currentUser)).show();
        ConsoleAssistant.pause("\n\nPress Enter to back to home...... ");
        ConsoleAssistant.clear();
    }

    private void handleSettingsMenu() {
        ConsoleAssistant.clear();
        SettingPage settingPage = new SettingPage();
        settingPage.show();
        int choice = ConsoleAssistant.readIntInRange("Choose a Number : ", 0, 3);
        switch (choice) {

            case 1 -> handleChangePassword(settingPage);
            case 2 -> handleChangeName(settingPage);
            case 3 -> handleDeleteUser(settingPage);
            case 0 -> ConsoleAssistant.clear();

        }
    }

    private void handleChangePassword(SettingPage settingPage) {
        do {
            String currentPassword = ConsoleAssistant.readPassword("\n\nEnter Current Password : ");
            String newPassword = ConsoleAssistant.readPassword("\nEnter New Password : ");

            try {
                userService.changePassword(currentUser, currentPassword, newPassword);
                // ConsoleAssistant.clear();
                settingPage.success("Password Updated Successfully.");
                return;
            } catch (AuthenticationException e) {
                settingPage.error(e.getMessage());
            }
        } while (ConsoleAssistant.readIntInRange("\nAgain ? Press 1 , for Back press 0.", 0, 1) == 1);
        ConsoleAssistant.clear();
    }

    private void handleChangeName(SettingPage settingPage) {
        do {
            try {
                String password = ConsoleAssistant.readPassword("\nEnter Your Password : ");
                String newName = ConsoleAssistant.readString("\nEnter new Name : ");
                userService.changeName(currentUser, newName, password);
                settingPage.success("Name Updated Successfully.");
                return;
            } catch (AuthenticationException e) {
                settingPage.error(e.getMessage());
            }
        } while (ConsoleAssistant.readIntInRange("Again ? Press 1 , for Back press 0 : ", 0, 1) == 1);
        ConsoleAssistant.clear();
    }

    private void handleDeleteUser(SettingPage settingPage) {
        do {
            try {
                String password = ConsoleAssistant.readPassword("\nEnter Your Password : ");
                userService.deleteUser(currentUser.getPhoneNumber(), password);
                currentUser = null;
                settingPage.success("Your wallet was deleted successfully.");
                return;
            } catch (AuthenticationException e) {
                settingPage.error(e.getMessage());
            }
        } while (ConsoleAssistant.readIntInRange("\nAgain ? Press 1 , for Back press 0 : ", 0, 1) == 1);
        ConsoleAssistant.clear();
    }

}