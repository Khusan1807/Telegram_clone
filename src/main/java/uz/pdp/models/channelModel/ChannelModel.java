package uz.pdp.models.channelModel;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.enums.Status;
import uz.pdp.models.BaseModel;
import uz.pdp.models.userModels.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static uz.pdp.session.Session.session;

/**
 * @author Narzullayev Husan, чт 9:12. 02.12.2021
 */
@Getter
@Setter

public class ChannelModel extends BaseModel {
    private String name;
    private User owner;
    private ArrayList<User> users;
    private Status status;
    private List<String> messageList;
    private Long time;

    public ChannelModel() {
        super();
    }

    public ChannelModel(String name) {
        this();
        this.owner = session;
        this.name = name;
        this.status = Status.ACTIVE;
        this.messageList = new ArrayList<>();
        this.time = new Date().getTime();
        this.users = new ArrayList<>();
    }

    public ChannelModel(String name, Status status) {
        this.name = name;
        this.status = status;
        this.messageList = new ArrayList<>();
    }
}
