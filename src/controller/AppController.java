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
                handleAuthMenu();
            } else {
                handleHomeMenu();
            }

        }
    }

    private void handleAuthMenu() {
        new AuthMenuPage().show();
        int choice = ConsoleAssistant.readIntInRange("\n \t Choose a Number : ", 0, 2);

        switch (choice) {
            case 1 -> handleLogin();
            case 2 -> handleRegister();

            case 0 -> System.exit(0);
        }
    }

    private void handleLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.show();
        String phoneNumber = ConsoleAssistant.readString("Enter phone number : ");
        String password = ConsoleAssistant.readPassword("Enter password : ");

        try {
            currentUser = userService.login(phoneNumber, password);
            ConsoleAssistant.clear();
            loginPage.success("\nLogin Done Successfully.");
        } catch (AppExceptions e) {
            ConsoleAssistant.clear();
            loginPage.error("\n ! Login Failed, Invalid phone number or password" + "\n ! If you don't have one yet, go to the signup page. \n");
        }
    }

    private void handleRegister() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.show();
        String name = ConsoleAssistant.readString("Enter Your Name : ");
        String phoneNumber = ConsoleAssistant.readString("Enter Your Phone number : ");
        String password = ConsoleAssistant.readPassword("Enter Your Password : ");

        try {
            userService.register(name, phoneNumber, password);
            ConsoleAssistant.clear();
            registerPage.success("\nRegistration Successful.");
        } catch (AppExceptions | IllegalArgumentException e) {
            ConsoleAssistant.clear();
            registerPage.error("\nRegistration Failed: " + e.getMessage());
        }
    }

    private void handleHomeMenu() {
        HomePage homePage = new HomePage(currentUser, walletService);
        homePage.show();
        new MenuPage().show();
        int choice = ConsoleAssistant.readIntInRange("Choose a Number : ", 0, 6);

        switch (choice) {
            case 1 -> handleDeposit();
            case 2 -> handleWithdraw();
            case 3 -> handleTransfer();
            case 4 -> handleHistory();
            case 5 -> {
                currentUser = null;
                ConsoleAssistant.clear();
            }
            case 6 -> handleSettings();
            case 0 -> System.exit(0);
        }
    }

    private void handleDeposit() {
        DepositPage depositPage = new DepositPage();
        depositPage.show();
        double amount = ConsoleAssistant.readDouble("Enter amount : ");

        try {
            walletService.deposit(currentUser, amount);
            ConsoleAssistant.clear();
            depositPage.success("Deposit successful. New balance: " + currentUser.getWallet().getBalance());
        } catch (AppExceptions e) {
            ConsoleAssistant.clear();
            depositPage.error(e.getMessage());
        }
    }

    private void handleWithdraw() {
        WithdrawPage withdrawPage = new WithdrawPage();
        withdrawPage.show();
        double amount = ConsoleAssistant.readDouble("Enter amount : ");

        try {
            walletService.withdraw(currentUser, amount);
            ConsoleAssistant.clear();
            withdrawPage.success("Withdraw successful. New balance: " + currentUser.getWallet().getBalance());
        } catch (AppExceptions e) {
            ConsoleAssistant.clear();
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
            ConsoleAssistant.clear();
            transferPage.success("Transfer successful. New balance: " + currentUser.getWallet().getBalance());
        } catch (AppExceptions e) {
            ConsoleAssistant.clear();
            transferPage.error(e.getMessage());
        }
    }

    private void handleHistory() {
        ConsoleAssistant.clear();
        new HistoryPage(walletService.getTransactionHistory(currentUser)).show();
    }

    private void handleSettings() {
        SettingPage settingPage = new SettingPage();
        settingPage.show();
        int choice = ConsoleAssistant.readIntInRange("Choose a Number : ", 0, 3);
        switch (choice) {

            case 1 -> handleChangePassword(settingPage);
            case 2 -> handleChangeName(settingPage);
            case 3 -> handleDeleteUser(settingPage);
            case 0 -> {
                ConsoleAssistant.clear();
            }

        }
    }

    private void handleChangePassword(SettingPage settingPage) {
        do {
            String currentPassword = ConsoleAssistant.readPassword("Enter Current Password : ");
            String newPassword = ConsoleAssistant.readPassword("Enter New Password : ");

            try {
                userService.changePassword(currentUser, currentPassword, newPassword);
                ConsoleAssistant.clear();
                settingPage.success("Password Updated Successfully.");
                return;
            } catch (AuthenticationException e) {
                settingPage.error(e.getMessage());
            }
        } while (ConsoleAssistant.readIntInRange("Again ? Press 1 , for Back press 0.", 0, 1) == 1);
        ConsoleAssistant.clear();
    }

    private void handleChangeName(SettingPage settingPage) {
        do {
            try {
                String password = ConsoleAssistant.readPassword("Enter Your Password : ");
                String newName = ConsoleAssistant.readString("Enter new Name : ");
                userService.changeName(currentUser, newName, password);
                ConsoleAssistant.clear();
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
                String password = ConsoleAssistant.readPassword("Enter Your Password : ");
                userService.deleteUser(currentUser.getPhoneNumber(), password);
                ConsoleAssistant.clear();
                currentUser = null;
                settingPage.success("Your wallet was deleted successfully.");
                return;
            } catch (AuthenticationException e) {
                settingPage.error(e.getMessage());
            }
        } while (ConsoleAssistant.readIntInRange("Again ? Press 1 , for Back press 0 : ", 0, 1) == 1);
        ConsoleAssistant.clear();
    }

}