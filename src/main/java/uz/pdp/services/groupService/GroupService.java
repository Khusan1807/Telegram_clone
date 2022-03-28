package uz.pdp.services.groupService;

import uz.pdp.dao.GroupDao;
import uz.pdp.dao.UsersDao;
import uz.pdp.models.groupModel.GroupModel;
import uz.pdp.models.messageModel.MessageModel;
import uz.pdp.models.userModels.User;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.dao.GroupDao.groupList;
import static uz.pdp.services.contactService.ContactService.listContact;
import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.print;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:08 AM. 12/2/2021
 */
public class GroupService {
    public static void creatNewGroup() {
        GroupModel group = createGroup();
        println("Add people for this group :  ");
        if (listContact()) {
            addContactForGroup(group);
        } else {
            println(PURPLE, "You have not any Contacts, so only you have in group!!!");
        }
        println(GREEN, " The group successfully created");
    }

    private static void addContactForGroup(GroupModel group) {
        String username = getStr("Enter username(skip ->1) : ");
        if (username.equals("1")) return;
        User user = checkHaveUserInContactsList(username);
        if (user == null) {
            println("Something went wrong!!!");
            return;
        }
        List<GroupModel> groups = GroupDao.groupList();
        for (GroupModel groupModel : groups) {
            if (groupModel.getId().equals(group.getId())) {
                groupModel.getUsers().add(user);
            }
        }
        GroupDao.writeGroupList(groups);
        addContactForGroup(group);
    }

    private static User checkHaveUserInContactsList(String username) {
        List<User> users = UsersDao.userList();
        for (User user : users) {
            if (user.getId().equals(session.getId())) {
                for (User contact : user.getContacts()) {
                    if (contact.getUserName().equalsIgnoreCase(username)) {
                        return contact;
                    }
                }
            }
        }
        return null;
    }

    private static GroupModel createGroup() {
        String groupName = getStr(" Group name : ");
        GroupModel groupModel = new GroupModel(groupName);
        groupModel.setOwner(session);
        groupModel.getUsers().add(session);
        ArrayList<GroupModel> groupModels = (ArrayList<GroupModel>) groupList();
        groupModels.add(groupModel);
        GroupDao.writeGroupList(groupModels);
        return groupModel;
    }


    public static void showMessageGroup() {
        if (listOfGroupsOfSession()) {
            println(RED, "You have not any groups!!!");
            return;
        }
        String groupname = getStr("enter group name : ");
        List<GroupModel> groups = GroupDao.groupList();
        for (GroupModel group : groups) {
            if (group.getGroupname().equalsIgnoreCase(groupname)) {
                List<MessageModel> groupMessage = group.getMessageList();
                for (MessageModel messageModel : groupMessage) {
                    println("From : " + messageModel.getSender().getUserName() +
                            "\t Message : " + messageModel.getText() +
                            "       | in " + messageModel.getData());
                }
            }
        }
    }

    private static boolean listOfGroupsOfSession() {
        boolean a = true;
        List<GroupModel> groups = GroupDao.groupList();
        for (GroupModel group : groups) {
            List<User> groupsUsers = group.getUsers();
            for (User groupsUser : groupsUsers) {
                if (groupsUser.getId().equals(session.getId())) {
                    a = false;
                    println("👨‍👧‍👧 " + group.getGroupname());
                }
            }
        }
        return a;
    }

    public static void sentMessageGroup() {
        if (listOfGroupsOfSession()) {
            println(RED, "You have not any groups!!!");
            return;
        }
        List<GroupModel> groups = GroupDao.groupList();
        String choice = getStr("Enter group name :  ");
        for (GroupModel groupModel : groups) {
            GroupModel group = checkMamberOfGroupByGroupname(choice);
            if (group == null) {
                println(RED, "Wrong choise!!!");
                return;
            }
            if (groupModel.getId().equalsIgnoreCase(group.getId())) {
                String text = getStr("📜 Enter text : ");
                MessageModel tempMessage = new MessageModel(session, text);
                groupModel.getMessageList().add(tempMessage);
                print(CYAN, tempMessage.getSender().getUserName() + ":\t");
                print(GREEN, tempMessage.getText() + "\t\t\t\n");
                println(GREEN, "Message successfully sent!!!");
            }

        }
        GroupDao.writeGroupList(groups);
    }

    private static GroupModel checkMamberOfGroupByGroupname(String choice) {
        List<GroupModel> groups = GroupDao.groupList();
        for (GroupModel group : groups) {
            if (group.getGroupname().equalsIgnoreCase(choice)) {
                List<User> groupsUsers = group.getUsers();
                for (User groupsUser : groupsUsers) {
                    if (groupsUser.getId().equals(session.getId())) {
                        return group;
                    }
                }
            }
        }
        return null;
    }


    public static void DeleteGroup() {
        int j = 0;
        if (groupList().isEmpty()) {
            println(RED, "The group does not exist");
        } else {
            println(groupList());
            String choice = getStr("-> ");
            for (GroupModel groupModel : groupList()) {
                if (groupModel.getGroupname().equals(choice)) {
                    groupList().remove(groupModel);
                    println(GREEN, " The group was successfully deleted");
                    j++;
                }
            }
            if (j == 0) {
                println(RED, " Not found ");
            }
        }
    }

    public static void RenameGroup() {
        if (groupList().isEmpty()) {
            println(RED, "The group does not exist");
        } else {
            println(groupList());
            String choice = getStr("-> ");
            String newGroupName = getStr("New name : ");
            for (GroupModel groupModel : groupList()) {
                if (groupModel.getGroupname().equals(choice)) {
                    groupModel.setGroupname(newGroupName);
                    println(GREEN, " The group name was successfully changed");
                }
            }
        }
    }


}
