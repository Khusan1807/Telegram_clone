package uz.pdp.models.requestModel;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.models.BaseModel;
import uz.pdp.models.groupModel.GroupModel;
import uz.pdp.models.userModels.User;
import uz.pdp.services.groupService.GroupService;

import java.time.LocalDateTime;

/**
 * @author Narzullayev Husan, чт 11:01. 02.12.2021
 */
@Getter
@Setter
public class RequestModel extends BaseModel {
    private User sender;
    private User receiver;
    private GroupModel group;

    public RequestModel() {
        super();
    }


    public RequestModel(User sender, User receiver, GroupModel group) {
        this.sender = sender;
        this.receiver = receiver;
        this.group = group;
    }
}
