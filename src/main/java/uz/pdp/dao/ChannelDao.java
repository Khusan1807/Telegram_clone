package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.models.channelModel.ChannelModel;
import uz.pdp.models.channelModel.ChannelModel;
import uz.pdp.models.messageModel.MessageModel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Narzullayev Husan, Thu 10:17 AM. 12/2/2021
 */

public class ChannelDao {

    private static final Type channelListType = new TypeToken<List<ChannelModel>>() {
    }.getType();

    public static List<ChannelModel> channelList() {
        try (FileReader fileReader = new FileReader("src/main/resources/channels.json");
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String data = bufferedReader.lines().collect(Collectors.joining());
            List<ChannelModel> channelList = new Gson().fromJson(data, channelListType);
            if (channelList != null)
                return channelList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeChannelList(List<ChannelModel> channels) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/channels.json"))) {
            bufferedWriter.write(new Gson().toJson(channels));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
