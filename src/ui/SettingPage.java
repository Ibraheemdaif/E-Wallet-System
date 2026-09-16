package ui;

public class SettingPage implements Page{
    @Override
    public void show() {
        displayHeader("Settings");
        System.out.println("\t [1].Change Password \t\t [2].Change Name \n \t [3].Delete your Account \t [0].Back\n\n");
    }


}
