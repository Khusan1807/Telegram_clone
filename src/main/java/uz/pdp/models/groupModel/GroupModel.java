package uz.pdp.models.groupModel;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.models.BaseModel;
import uz.pdp.models.messageModel.MessageModel;
import uz.pdp.models.userModels.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static uz.pdp.session.Session.session;

/**
 * @author Narzullayev Husan, чт 10:22. 02.12.2021
 */
@Getter
@Setter
public class GroupModel extends BaseModel {
    private String groupname;
    private User owner;
    private ArrayList<User> users;
    private ArrayList<MessageModel> messageList;
    private Long time;

    public GroupModel() {
        super();
    }

    public GroupModel(String groupname) {
        this();
        this.owner = session;
        this.groupname = groupname;
        this.users = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.time = new Date().getTime();
    }

    @Override
    public String toString() {
        return "GroupModel{" +
                "groupname='" + groupname + '\'' +
                '}' + "\n";
    }
}
