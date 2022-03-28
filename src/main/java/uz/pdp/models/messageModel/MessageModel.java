package uz.pdp.models.messageModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.models.BaseModel;
import uz.pdp.models.userModels.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Narzullayev Husan, чт 10:34. 02.12.2021
 */
@Getter
@Setter
@ToString(of = {"sender", "time", "text", "receiver"})
public class MessageModel extends BaseModel {
    private User sender;
    private User receiver;
    private String text;
    private String data;
    Calendar calendar = new GregorianCalendar();


    public MessageModel() {
        super();
    }

    public MessageModel(User sender, String text) {
        this();
        this.sender = sender;
        this.text = text;
        this.data = calendar.getTime().toString();
    }

    public MessageModel(User sender, User receiver, String text) {
        this();
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.data = calendar.getTime().toString();
    }
}
