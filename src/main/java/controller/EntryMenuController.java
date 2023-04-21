package controller;

import model.Stronghold;
import model.User;
import model.map.Map;
import model.map.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class EntryMenuController {
    public static void fillAllFieldsWithPreviousData() {
        initializeFields("users");
        initializeFields("maps");
    }

    private static void initializeFields(String field) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/" + field + ".json")) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            switch (field) {
                case "users" -> jsonArray.forEach(user -> parseUserObject((JSONObject) user));
                case "maps" -> jsonArray.forEach(map -> parseMapObject((JSONObject) map));
            }
        } catch (IOException | ParseException ignored) {
        }
    }

    private static void parseUserObject(JSONObject user) {
        //Get user fields
        String username = (String) user.get("username");
        String password = (String) user.get("password");
        String nickname = (String) user.get("nickname");
        String email = (String) user.get("email");
        String recoveryQuestion = (String) user.get("recoveryQuestion");
        String recoveryAnswer = (String) user.get("recoveryAnswer");
        String slogan = (String) user.get("slogan");
        new User(username, password, email, nickname, recoveryQuestion, recoveryAnswer, slogan);
        Stronghold.addUserToUserList(user);
    }

    private static void parseMapObject(JSONObject map) {
        String name = (String) map.get("name");
        int size = (Integer) map.get("size");
        Tile[][] mapTiles = (Tile[][]) map.get("map");

        new Map(name, mapTiles, size);
        Stronghold.addMapToMapList(map);
    }

    public static boolean isStayLoggedIn() {
        return Stronghold.isStayLoggedIn();
    }
}
