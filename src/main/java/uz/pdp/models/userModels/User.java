package uz.pdp.models.userModels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.enums.Status;
import uz.pdp.models.BaseModel;
import uz.pdp.models.channelModel.ChannelModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Narzullayev Husan, чт 9:11. 02.12.2021
 */

@Getter
@Setter
@ToString(of = {"userName", "name", "status"})
public class User extends BaseModel {
    private String userName;
    private String name;
    private String number;
    private ArrayList<User> contacts;
    private Status status;

    public User() {
        super();
    }

    public User(String userName, String number) {
        this.userName = userName;
        this.number = number;
    }

    public User(String userName, String name, String number, Status status) {
        this();
        this.userName = userName;
        this.name = name;
        this.number = number;
        this.status = status;
    }

}
