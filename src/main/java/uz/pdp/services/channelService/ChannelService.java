package uz.pdp.services.channelService;

import uz.pdp.enums.Status;
import uz.pdp.models.channelModel.ChannelModel;
import uz.pdp.models.messageModel.MessageModel;
import uz.pdp.models.userModels.User;

import java.util.List;

import static uz.pdp.dao.ChannelDao.*;
import static uz.pdp.dao.MessageDao.messageList;
import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Color.RED;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.print;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:09 AM. 12/2/2021
 */
public class ChannelService {


    public static void creatNewChannel() {
        String name = getStr("Name: ");
        for (ChannelModel channelModel : channelList()) {
            if (channelModel.getName().equals(name)) {
                println(RED, "Username already token!");
                return;
            }
        }
        ChannelModel channelModel1 = new ChannelModel(name);
        channelModel1.getUsers().add(session);
        List<ChannelModel> models = channelList();
        models.add(channelModel1);
        writeChannelList(models);
        println(GREEN, "Succesfully add Channel");

    }

    public static void sentMessageChannel() {
        boolean channel = false;
        for (ChannelModel model : channelList()) {
            if (model.getStatus().equals(Status.ACTIVE)
                    && model.getOwner().getUserName().equals(session.getUserName())) {
                println(GREEN, "🔉  " + model.getName());
                channel = true;
            }
        }


        if (channel) {
            String whichChannel = getStr("Enter channel name: ");
            List<ChannelModel> channelModels = channelList();
            for (ChannelModel channell : channelModels) {
                if (channell.getName().equalsIgnoreCase(whichChannel)
                        && channell.getOwner().getUserName().equals(session.getUserName())) {
                    String sms = getStr("Message: ");
                    MessageModel message = new MessageModel(session, sms);
                    List<String> mass = channell.getMessageList();
                    mass.add("" + message.getText());
                    channell.setMessageList(mass);
                    println(GREEN, "Successfully sent!!!");
                    break;
                }
            }
            writeChannelList(channelModels);
        } else {
            println(RED, "You have not channel");
        }
    }

    public static void DeleteChannel() {
        boolean channel = false;
        channel = isChannel(channel);
        if (channel) {
            String channelName = getStr("Enter Channel: ");
            for (ChannelModel model : channelList()) {
                if (model.getName().equals(channelName)
                        && model.getOwner().getUserName().equals(session.getUserName())) {
                    List<ChannelModel> models = channelList();
                    models.remove(model);
                    writeChannelList(models);
                    break;
                }
            }
        } else {
            println(RED, "You have not chanell");
        }
    }

    public static void RenameChannel() {
        boolean channel = false;
        channel = isChannel(channel);
        if (channel) {
            String channelName = getStr("Enter Channel: ");
            List<ChannelModel> channelModels = channelList();
            for (ChannelModel model : channelModels) {
                if (model.getName().equals(channelName)
                        && model.getOwner().getUserName().equals(session.getUserName())) {
                    String newName = getStr("New Name: ");
                    model.setName(newName);
                    writeChannelList(channelModels);
                    println(GREEN, "Succesfully Added");
                    break;
                }
            }
        } else {
            println(RED, "You have not channel");
        }
    }

    private static boolean isChannel(boolean channel) {
        for (ChannelModel model : channelList()) {
            for (User user : model.getUsers()) {
                if (user.getId().equalsIgnoreCase(session.getId())) {
                    String color = model.getStatus().equals(Status.ACTIVE) ? GREEN : RED;
                    println(color, "🔉  " + model.getName());
                    channel = true;
                }
            }
//            if (model.getOwner().getUserName().equalsIgnoreCase(session.getUserName())) {
//                String color = model.getStatus().equals(Status.ACTIVE) ? GREEN : RED;
//                println(color, "🔉  " + model.getName());
//                channel = true;
//            }
        }
        return channel;
    }

    private static void showChannel() {
        for (ChannelModel model : channelList()) {
            if (model.getOwner().getUserName().equals(session.getUserName())) {
                String color = model.getStatus().equals(Status.ACTIVE) ? GREEN : RED;
                println(color, "🔉  " + model.getName());
            }
        }
    }

    public static void showChannelMessage() {
        boolean channel = false;
        channel = isChannel(channel);
        if (channel) {
            String channelName = getStr("Enter Channel: ");
            List<ChannelModel> channelModels = channelList();
            for (ChannelModel model : channelModels) {
                if (model.getName().equalsIgnoreCase(channelName)) {
                    for (String s : model.getMessageList()) {
                        println(s);
                    }

                }
            }
        }
    }
}
