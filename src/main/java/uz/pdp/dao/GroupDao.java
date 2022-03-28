package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.models.groupModel.GroupModel;
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
public class GroupDao {
    private static final Type groupListType = new TypeToken<List<GroupModel>>() {
    }.getType();

    public static List<GroupModel> groupList() {
        try (FileReader fileReader = new FileReader("src/main/resources/groups.json");
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String data = bufferedReader.lines().collect(Collectors.joining());
            List<GroupModel> groupList = new Gson().fromJson(data, groupListType);
            if (groupList != null)
                return groupList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeGroupList(List<GroupModel> groups) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/groups.json"))) {
            bufferedWriter.write(new Gson().toJson(groups));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
