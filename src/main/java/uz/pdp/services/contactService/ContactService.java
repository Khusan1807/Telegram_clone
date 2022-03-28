package uz.pdp.services.contactService;

import uz.pdp.dao.UsersDao;
import uz.pdp.enums.Status;
import uz.pdp.models.userModels.User;
import uz.pdp.services.messageService.MessageService;

import java.util.List;
import java.util.Objects;

import static uz.pdp.services.menuService.MenuService.activate;
import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:09 AM. 12/2/2021
 */

public class ContactService {


    public static void addContact() {
        String phoneNummer = getStr("Enter User number : +998 ");
        if (!checkNumber(phoneNummer)) {
            return;
        }
        User newContact = findByContactName(phoneNummer);
        if ((Objects.nonNull(newContact))) {
            if (checkAndSendMessage(newContact)) return;
            List<User> userList = UsersDao.userList();
            for (User user : userList) {
                if (user.getId().equals(session.getId())) {
                    user.getContacts().add(newContact);
                }
            }
            for (User user : userList) {
                if (user.getId().equals(newContact.getId())) {
                    user.getContacts().add(session);
                }
            }
            UsersDao.writeUserList(userList);
            println(GREEN, "Contact Save!!!");

        } else {
            println(RED, "Account not found!!!");
            return;
        }
    }

    private static boolean checkAndSendMessage(User newContact) {
        for (User contact : session.getContacts()) {
            if (contact.getId().equals(newContact.getId())) {
                println(CYAN, "This contact already have in your list!!!");
                String choice = getStr("Do want to start chat?\n   (YES/NO)   \n->");
                if (choice.startsWith("y")) {
                    MessageService.sendMessageForExistUser(session, newContact);
                }
                return true;
            }
        }
        return false;
    }

    public static void deleteContact() {
        if (Objects.nonNull(session.getContacts())) {
            showListContact();
            String name = getStr("Enter Contact name: ");
            List<User> users = UsersDao.userList();
            for (User user : users) {
                if (user.getId().equals(session.getId())) {
                    for (User contact : user.getContacts()) {
                        if (contact.getUserName().equalsIgnoreCase(name) &&
                                !contact.getStatus().equals(Status.DELETED)) {
                            contact.setStatus(Status.DELETED);
                            println(GREEN, "Contact Deleted.");
                        }
                    }
                }
            }
            UsersDao.writeUserList(users);

        } else {
            println(RED, "You have no contacts.");
        }
    }

    public static void showListContact() {
        if (Objects.nonNull(session.getContacts())) {
            List<User> users = UsersDao.userList();
            for (User user : users) {
                if (user.getId().equals(session.getId())) {
                    for (User contact : user.getContacts()) {
                        if (!contact.getStatus().equals(Status.DELETED)) {
                            if (contact.getStatus().equals(Status.BLOCKED)) {
                                println(RED, "Contact name " + contact.getUserName() + "   " + " Contact number " + contact.getNumber());
                            } else
                                println(CYAN, "Contact name " + contact.getUserName() + "   " + "Contact number " + contact.getNumber());
                        }
                    }
                }
            }

        } else {
            println(RED, "You have no contact.");
        }
    }

    public static boolean listContact() {
        boolean a = false;
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getId().equals(session.getId())) {
                for (User contact : user.getContacts()) {
                    if (contact.getStatus().equals(Status.ACTIVE)) {
                        println(CYAN, "Contact name: " + contact.getUserName() + "   " + "Contact number: " + contact.getNumber());
                        a = true;
                    }
                }
            }
        }

        return a;
    }

    public static void BlockContact() {
        if (Objects.nonNull(session.getContacts())) {
            showListContact();
            String name = getStr("Enter Contact name : ");
            if (findContactByUsername(name) == null) {
                println(RED, "Contact not found !!!");
                return;
            }
            List<User> users = UsersDao.userList();
            for (User user : users) {
                if (user.getId().equals(session.getId())) {
                    for (User contact : user.getContacts()) {
                        if (contact.getUserName().equalsIgnoreCase(name)) {
                            contact.setStatus(Status.BLOCKED);
                            println("Contact blocked!!!");
                        }
                    }
                }
            }
            UsersDao.writeUserList(users);

        } else {
            println(RED, "You have no contacts!!!");
        }

    }

    public static void UnblockContact() {
        if (Objects.nonNull(session.getContacts())) {
            showListContact();
            String name = getStr("Enter Contact name: ");
            for (User contact : session.getContacts()) {
                if (contact.getUserName().equalsIgnoreCase(name) && !contact.getStatus().equals(Status.DELETED)) {
                    activate(contact);
                    println(GREEN, "Contact unblocked!!!");
                }
            }
        } else {
            println(RED, "You have no contact.");
        }

    }

    private static User findContactByUsername(String username) {
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username))
                return user;
        }
        return null;
    }

    private static User findByContactName(String number) {
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getNumber().equals(number))
                return user;
        }
        return null;
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


}
