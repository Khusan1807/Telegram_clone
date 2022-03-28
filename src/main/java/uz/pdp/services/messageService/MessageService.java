package uz.pdp.services.messageService;

import uz.pdp.dao.MessageDao;
import uz.pdp.dao.UsersDao;
import uz.pdp.enums.Status;
import uz.pdp.models.messageModel.MessageModel;
import uz.pdp.models.userModels.User;

import java.util.List;
import java.util.Objects;

import static uz.pdp.dao.MessageDao.messageList;
import static uz.pdp.dao.MessageDao.writeMessageList;
import static uz.pdp.services.contactService.ContactService.listContact;
import static uz.pdp.services.menuService.MenuService.findUserByUserName;
import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:07 AM. 12/2/2021
 */
public class MessageService {

    public static void showMessage() {
        listContact();
        if (Objects.nonNull(session.getContacts())) {
            String contactName = getStr("Enter contact name: ");
            boolean a = false;
            List<User> users = UsersDao.userList();
            for (User user : users) {
                if (user.getId().equals(session.getId())) {
                    for (User contact : user.getContacts()) {
                        if (contact.getUserName().equalsIgnoreCase(contactName)
                                && !contact.getStatus().equals(Status.BLOCKED)
                                && !contact.getStatus().equals(Status.DELETED)) {
                            a = true;
                            printMessage(contactName);
                        }
                    }
                }
            }

            if (!a) {
                println(RED, "Something went Wrong!!!");
            }
        } else {
            println(RED, "You have not contact");
        }
    }

    private static void printMessage(String contactName) {
        println(CYAN, "Your chat with " + findUserByUserName(contactName).getUserName());
        for (MessageModel message : messageList()) {
            if (message.getSender().getUserName().equalsIgnoreCase(session.getUserName())
                    && message.getReceiver().getUserName().equalsIgnoreCase(contactName)) {
                String mes = "📧 " + message.getText() + "\t    | in" + message.getData();
                println(PURPLE, mes);
            }
            if (message.getSender().getUserName().equalsIgnoreCase(contactName)
                    && message.getReceiver().getId().equals(session.getId())) {
                String mes = "📧 " + message.getText() + "\t    | in" + message.getData();
                println(YELLOW, mes);
            }
        }
    }

    public static void sendMessage() {
        if (!listContact()) {
            println(RED, "You have not contacts yet!!!");
            return;
        }
        if (Objects.nonNull(session.getContacts())) {
            String contactName = getStr("Enter contact name: ");
            boolean a = false;
            for (User contact : session.getContacts()) {
                if (contact.getUserName().equalsIgnoreCase(contactName)) {
                    a = true;
                    printMessage(contactName);
                    String text = getStr("Enter message: ");
                    MessageModel messageM = new MessageModel(session, contact, text);
                    List<MessageModel> messages = messageList();
                    messages.add(messageM);
                    writeMessageList(messages);
                    printMessage(contactName);
                }
            }
            if (!a) {
                println(RED, "Something went Wrong!!!");
            }
        } else {
            println(RED, "You have not contact");
        }

    }

    public static void sendMessage(User receiver) {
        List<User> userList = UsersDao.userList();
        for (User user : userList) {
            if (user.getId().equals(session.getId())) {
                user.getContacts().add(receiver);
            }
        }
        for (User user : userList) {
            if (user.getId().equals(receiver.getId())) {
                user.getContacts().add(session);
            }
        }
        String text = getStr("Enter message: ");
        MessageModel messageM = new MessageModel(session, receiver, text);
        List<MessageModel> messages = messageList();
        messages.add(messageM);
        writeMessageList(messages);
        UsersDao.writeUserList(userList);
        println("Successfully sent!!!");

    }

    public static void sendMessageForExistUser(User session, User user) {
        String text = getStr("Enter meesage  : ");
        MessageModel message = new MessageModel(session, user, text);
        List<MessageModel> messages = MessageDao.messageList();
        messages.add(message);
        MessageDao.writeMessageList(messages);
        println(GREEN, "Message successfully sent!");
    }
}
