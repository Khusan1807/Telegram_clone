package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.models.userModels.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Narzullayev Husan, Thu 10:17 AM. 12/2/2021
 */
public class UsersDao {
    private static final Type userListType = new TypeToken<List<User>>() {
    }.getType();

    public static List<User> userList() {
        List<User> userList;
        try (FileReader fileReader = new FileReader("src/main/resources/users.json");
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String data = bufferedReader.lines().collect(Collectors.joining());
            userList = new Gson().fromJson(data, userListType);
            if (userList != null)
                return userList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeUserList(List<User> users) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/users.json"))) {
            bufferedWriter.write(new Gson().toJson(users));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
