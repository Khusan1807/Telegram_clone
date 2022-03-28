package uz.pdp.services.authService;

import uz.pdp.dao.UsersDao;
import uz.pdp.enums.Status;
import uz.pdp.models.userModels.User;
import uz.pdp.session.Session;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:05 AM. 12/2/2021
 */
public class AuthService {


    public static void login() {
        String number = getStr("Enter phone number : +998 ");
        if (!checkNumber(number)) return;
        List<User> users = UsersDao.userList();
        if (users.isEmpty()) {
            println(PURPLE, "There is not any User in this Application!!!");
            println(PURPLE, "Please create new Account!!!");
            return;
        }
        for (User user : users) {
            if (user.getNumber().equals(number)) {
                Session.session = user;
                println(GREEN, "WELCOME!!!");
                return;
            }
        }
        println(RED,"User not found!!!");
    }

    public static void creatNewAccount() {
        String number = getStr("Enter phone number : +998 ");
        if (!checkNumber(number)) return;
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getNumber().equals(number)) {
                println(RED, "You aldeady have account!!!\tPlease login!!!");
                return;
            }
        }
        String username = getStr("Enter username  : ");
        if (createUsername(users, username)) {
            return;
        }
        String name = getStr("Enter profile name : ");
        User user = new User();
        user.setName(name);
        user.setNumber(number);
        user.setUserName(username);
        users.add(user);
        user.setContacts(new ArrayList<>());
        user.setStatus(Status.ACTIVE);
        UsersDao.writeUserList(users);
        println(GREEN, "Account successfully created!");

    }

    private static boolean createUsername(List<User> users, String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                println(RED, "This username already taken!!!\tPlease take other!!!");
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        Session.session = null;
        return;
    }

    public static boolean checkNumber(String str) {
        if (str.length() == 9) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) - '0' < 0 || str.charAt(i) - '0' > 9) {
                    println(RED, "Wrong number!");
                }
            }
            return true;
        } else {
            println(RED, "Invalit number!");
        }
        return false;
    }


    public static void SendingMenu() {
        println(GREEN, "Sent message for User -> 1");
        println(GREEN, "Sent message for Groups -> 2");
        println(GREEN, "Sent message for Channels -> 3");
    }
}
