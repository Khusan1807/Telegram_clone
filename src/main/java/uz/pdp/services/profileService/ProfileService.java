package uz.pdp.services.profileService;

import uz.pdp.dao.ChannelDao;
import uz.pdp.dao.GroupDao;
import uz.pdp.dao.RequestDao;
import uz.pdp.dao.UsersDao;
import uz.pdp.models.channelModel.ChannelModel;
import uz.pdp.models.groupModel.GroupModel;
import uz.pdp.models.requestModel.RequestModel;
import uz.pdp.models.userModels.User;

import java.util.List;

import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.GREEN;
import static uz.pdp.utils.Color.RED;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 2:37 PM. 12/2/2021
 */
public class ProfileService {
    public static void changeName() {
        String newName = getStr("Enter new name: ");
        List<User> userList = UsersDao.userList();
        for (User user : userList) {
            if (user.getId().equals(session.getId())) {
                user.setName(newName);
            }
        }
        UsersDao.writeUserList(userList);
        println(GREEN, "Successfully changed");
    }

    public static void changeUsername() {
        String username = getStr("Enter new ousername: ");
        List<User> userList = UsersDao.userList();
        for (User user : userList) {
            if (user.getId().equals(session.getId())) {
                if (!user.getUserName().equals(username)) {
                    user.setUserName(username);
                    return;
                } else {
                    println(RED, "This username is same with old one");
                    return;
                }
            }
        }
        UsersDao.writeUserList(userList);
        println(GREEN, "Successfully changed");
    }

    public static void showAllRequests() {
        List<RequestModel> requests = RequestDao.requestList();
        for (RequestModel request : requests) {
            if (request.getReceiver().getId().equals(session.getId())) {
                println("🔹  From : " + request.getSender().getUserName() + "\t to join : " +
                        request.getGroup().getGroupname());
                String choise = getStr("Do you want resieve?(yes/no) : ");
                if (choise.equals("y")) {
                    resieveRequest(request);
                } else requests.remove(request);
            }
        }
        RequestDao.writeRequestList(requests);
    }

    private static void resieveRequest(RequestModel request) {
        List<GroupModel> groupModels = GroupDao.groupList();
        for (GroupModel groupModel : groupModels) {
            if (groupModel.getId().equals(request.getGroup().getId())) {
                groupModel.getUsers().add(request.getSender());
            }
        }
        GroupDao.writeGroupList(groupModels);
    }

}
