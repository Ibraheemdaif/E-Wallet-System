package model;

import exception.AuthenticationException;

import java.util.Objects;

public class User {
    private String name;
    private  String phoneNumber;
    private String password;
    private final Wallet wallet;

    public User(String phoneNumber, String password, String name) {
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException();
        if (password == null || password.isBlank())
            throw new IllegalArgumentException();
        if (name == null || name.isBlank())
            throw new IllegalArgumentException();
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.name = name;
        wallet = new Wallet();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setName(String name) {this.name = name;}

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void updatePassword(String currentPassword, String newPassword) {

        if (isPasswordCorrect(currentPassword)) {
            this.password = newPassword;
        } else {
            throw new AuthenticationException("Entered Password is incorrect.");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phoneNumber);
    }
}
