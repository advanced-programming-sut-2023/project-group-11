package controller;

import model.Stronghold;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class EntryMenuController {
    public static void fillAllFieldsWithPreviousData() {
        initializeUsers();

    }

    private static void initializeUsers() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("users.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) obj;
            employeeList.forEach(user -> parseEmployeeObject((JSONObject) user));
        } catch (IOException | ParseException ignored) {
        }
    }

    private static void parseEmployeeObject(JSONObject user) {
        //Get user object within list
        JSONObject userObject = (JSONObject) user.get("user");

        String username = (String) userObject.get("username");
        String password = (String) userObject.get("password");
        String nickname = (String) userObject.get("nickname");
        String email = (String) userObject.get("email");
        String recoveryQuestion = (String) userObject.get("recoveryQuestion");
        String recoveryAnswer = (String) userObject.get("recoveryAnswer");
        String slogan = (String) userObject.get("slogan");
        new User(username, password, email, nickname, recoveryQuestion, recoveryAnswer, slogan);
    }

    public static boolean isStayLoggedIn() {
        return Stronghold.isStayLoggedIn();
    }
}
