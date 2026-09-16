package ui;

public class MenuPage implements Page {
    @Override
    public void show() {
        subHeader("Wallet Operetions Menu");
        System.out.println("\t [1].Deposit  \t [2].Withdraw \t [3].Transfer \t [4].History" );
        System.out.println("\n\t [5].Logout   \t [6].Settings \t [0].Exit \n");
    }
}