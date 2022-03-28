package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.models.requestModel.RequestModel;
import uz.pdp.models.userModels.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Narzullayev Husan, Thu 10:17 AM. 12/2/2021
 */
public class RequestDao {
    private static final Type requestListType = new TypeToken<List<RequestModel>>() {
    }.getType();

    public static List<RequestModel> requestList() {
        try (FileReader fileReader = new FileReader("src/main/resources/requests.json");
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String data = bufferedReader.lines().collect(Collectors.joining());
            List<RequestModel> requestList = new Gson().fromJson(data, requestListType);
            if (requestList != null)
                return requestList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeRequestList(List<RequestModel> requests) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/requests.json"))) {
            bufferedWriter.write(new Gson().toJson(requests));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
