package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.models.messageModel.MessageModel;
import uz.pdp.models.requestModel.RequestModel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Narzullayev Husan, Thu 10:17 AM. 12/2/2021
 */
public class MessageDao {
    private static final Type messageListType = new TypeToken<List<MessageModel>>() {
    }.getType();

    public static List<MessageModel> messageList() {
        try (FileReader fileReader = new FileReader("src/main/resources/messages.json");
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String data = bufferedReader.lines().collect(Collectors.joining());
            List<MessageModel> messageList = new Gson().fromJson(data, messageListType);
            if (messageList != null)
                return messageList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeMessageList(List<MessageModel> messages) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/messages.json"))) {
            bufferedWriter.write(new Gson().toJson(messages));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
