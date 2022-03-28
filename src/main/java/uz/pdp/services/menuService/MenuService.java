package uz.pdp.services.menuService;

import uz.pdp.dao.ChannelDao;
import uz.pdp.dao.GroupDao;
import uz.pdp.dao.RequestDao;
import uz.pdp.dao.UsersDao;
import uz.pdp.enums.Status;
import uz.pdp.menus.Menu;
import uz.pdp.models.channelModel.ChannelModel;
import uz.pdp.models.groupModel.GroupModel;
import uz.pdp.models.requestModel.RequestModel;
import uz.pdp.models.userModels.User;
import uz.pdp.services.messageService.MessageService;

import java.util.List;
import java.util.Objects;

import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:08 AM. 12/2/2021
 */
public class MenuService {
    public static void chatCouters() {
        showNumberOfChannels();
        showNumberOfGroups();
        showNumberOfContacts();
        showNumberOfBlockedContacts();
    }

    private static void showNumberOfBlockedContacts() {
        int counter = 0;
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getId().equals(session.getId())) {
                for (User contact : user.getContacts()) {
                    if (contact.getStatus().equals(Status.BLOCKED)) {
                        counter++;
                    }
                }
            }
        }
        println(RED, "Blocked Contacts     |" + counter);
    }

    public static void showNumberOfChannels() {
        int counter = 0;
        List<ChannelModel> channels = ChannelDao.channelList();
        for (ChannelModel channel : channels) {
            for (User user : channel.getUsers()) {
                if (user.getId().equals(session.getId())) {
                    counter++;
                }
            }
        }
        println(YELLOW, "Channels     | " + counter);
    }

    public static void showNumberOfGroups() {
        List<GroupModel> groups = GroupDao.groupList();
        int counter = 0;
        for (GroupModel group : groups) {
            for (User user : group.getUsers()) {
                if (user.getId().equals(session.getId())) {
                    counter++;
                }
            }
        }
        println(CYAN, "Groups     | " + counter);
    }

    public static void showNumberOfContacts() {
        int counter = 0;
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getId().equals(session.getId())) {
                counter = user.getContacts().size();
            }
        }
        println(PURPLE, "Contacts     |" + counter);
    }

    public static void search() {
        Menu.searchMenu();
        String opt = getStr("?: ").toLowerCase();
        if (opt.startsWith("b")) return;
        switch (opt) {
            case "1" -> searchUser();
            case "2" -> searchGroup();
            case "3" -> searchChannel();
            default -> println(RED, "Wrong Selection!!");
        }
    }

    private static void searchUser() {
        String username = getStr("Enter username: ").toLowerCase();
        User user = findUserByUserName(username);
        if (Objects.isNull(user)) {
            println(RED, "User not found");
            return;
        }
        for (User contact : session.getContacts()) {
            if (contact.getId().equals(user.getId())) {
                println(CYAN, "This Contact have already in your list");
                String choice = getStr("Do want to start chat?\n   (YES/NO)   \n->");
                if (choice.startsWith("y")) {
                    activate(user);
                    MessageService.sendMessageForExistUser(session, user);
                    return;
                }
            }
        }


//        List<User> users = UsersDao.userList();
//        for (User user1 : users) {
//            if (user1==user){
//                user1.getContacts().add(user);
//            }
//        }
        String choice = getStr("Do want to start chat?\n   (YES/NO)   \n->");
        if (choice.startsWith("y")) {
            MessageService.sendMessage(user);
        }
    }

    public static void activate(User user) {
        List<User> users = UsersDao.userList();
        for (User user1 : users) {
            if (user1.getId().equals(session.getId())) {
                for (User user1Contact : user1.getContacts()) {
                    if (user1Contact.getId().equals(user.getId())) {
                        user1Contact.setStatus(Status.ACTIVE);
                    }
                }
            }
        }
        UsersDao.writeUserList(users);
    }

    private static void searchGroup() {
        String nameOfGr = getStr("Enter Group name: ").toLowerCase();
        GroupModel group = findGroupByName(nameOfGr);
        if (Objects.isNull(group)) {
            println(RED, "Group not found");
            return;
        }
        String choice = getStr("Do want to join to group?\n   (YES/NO)   \n->");
        if (choice.startsWith("y")) {
            RequestModel request = new RequestModel(session, group.getOwner(), group);
            List<RequestModel> requestModelList = RequestDao.requestList();
            requestModelList.add(request);
            RequestDao.writeRequestList(requestModelList);
            println("You have sent request!!");
        }
    }

    private static void searchChannel() {
        String nameOfChannel = getStr("Enter Channel name: ").toLowerCase();
        ChannelModel channel = findChannelByName(nameOfChannel);
        if (Objects.isNull(channel)) {
            println(RED, "Channel not found");
            return;
        }
        if (checkAlreadyJoinedChannel(channel)) {
            println(PURPLE, "You already joined to this channel!!!");
            return;
        }
        String choice = getStr("Do want to join to channel?\n   (YES/NO)   \n->");
        if (choice.startsWith("y")) {
            List<ChannelModel> channels = ChannelDao.channelList();
            for (ChannelModel channelModel : channels) {
                if (channelModel.getId().equals(channel.getId())) {
                    channelModel.getUsers().add(session);
                }
            }
            ChannelDao.writeChannelList(channels);
            println("Successfully joined!!!");
        }
    }

    private static boolean checkAlreadyJoinedChannel(ChannelModel channel) {
        List<ChannelModel> channels = ChannelDao.channelList();
        for (ChannelModel channelModel : channels) {
            for (User user : channelModel.getUsers()) {
                if (user.getId().equals(session.getId())) return true;
            }
        }
        return false;
    }

    private static ChannelModel findChannelByName(String nameOfChannel) {
        List<ChannelModel> channelModelList = ChannelDao.channelList();
        for (ChannelModel channel : channelModelList) {
            if (channel.getName().equalsIgnoreCase(nameOfChannel)) {
                return channel;
            }
        }
        return null;
    }

    private static GroupModel findGroupByName(String nameOfGr) {
        List<GroupModel> groupModelList = GroupDao.groupList();
        for (GroupModel group : groupModelList) {
            if (group.getGroupname().equalsIgnoreCase(nameOfGr)) {
                return group;
            }
        }
        return null;
    }

    public static User findUserByUserName(String username) {
        List<User> userList = UsersDao.userList();
        for (User user : userList) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
}
