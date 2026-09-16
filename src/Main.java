import controller.AppController;
import service.UserService;
import service.WalletService;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserService();
        WalletService walletService = new WalletService(userService);
        AppController app = new AppController(userService,walletService);

        app.run();
    }
}