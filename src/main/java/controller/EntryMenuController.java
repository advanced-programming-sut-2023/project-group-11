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
        initializeUsers();
        initializeMaps();
    }

    private static void initializeUsers() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/users.json")) {
            JSONArray userList = (JSONArray) jsonParser.parse(reader);
            userList.forEach(user -> parseUserObject((JSONObject) user));
        } catch (IOException | ParseException ignored) {
        }
    }

    private static void parseUserObject(JSONObject user) {
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
        Stronghold.addUserToUserList(user);
    }

    private static void initializeMaps() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/maps.json")) {
            JSONArray mapList = (JSONArray) jsonParser.parse(reader);
            mapList.forEach(map -> parseMapObject((JSONObject) map));
        } catch (IOException | ParseException ignored) {
        }
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
