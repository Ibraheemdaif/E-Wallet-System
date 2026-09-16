package service;

import exception.AuthenticationException;
import model.*;

import java.util.HashMap;
import java.util.Map;


public class UserService {
    private final Map<String, User> users;

    public UserService() {
        this.users =new HashMap<>();
    }

    public  User findUserByPhoneNumber(String phone) {
        return users.get(phone);
    }


    public void register(String name, String phone, String password) {
        if (findUserByPhoneNumber(phone) != null)
            throw new AuthenticationException("This phone number is already registered.");
        User newUser = new User(phone, password, name);
        users.put(phone, newUser);
    }

    public User login(String phone, String password) {
        User user = findUserByPhoneNumber(phone);
        if (user == null)
            throw new AuthenticationException("This phone number is not registered.");
        if (!user.isPasswordCorrect(password))
            throw new AuthenticationException("Invalid password, pls enter the correct password.");

        return user;
    }

    public void deleteUser(String phone, String password) {
        User user = findUserByPhoneNumber(phone);
        if (user == null)
            throw new AuthenticationException("This phone number is not exist.");
        if (!user.isPasswordCorrect(password))
            throw new AuthenticationException("Invalid password.");
        users.remove(phone);
    }

    public void changePassword(User currentUser, String currentPassword, String newPassword) {
            currentUser.updatePassword(currentPassword,newPassword);
    }

    public void changeName(User currentUser, String newName, String password) {
        if (currentUser.isPasswordCorrect(password)) {
            currentUser.setName(newName);
            return;
        }
        throw new AuthenticationException("Invalid password, pls enter the correct password.");
    }



}
