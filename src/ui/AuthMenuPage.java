package ui;

public class AuthMenuPage implements Page{
    @Override
    public void show() {
        displayHeader("Authentication Page");
        System.out.println("\t [1].LogIn \t [2].SignUp \t [0].Exit");
    }
}
