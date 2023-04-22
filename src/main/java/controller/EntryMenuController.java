package controller;

import model.Stronghold;
import model.User;
import model.buildings.Building;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;
import model.people.Units;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

        User previousUser = new User(username, password, email, nickname, recoveryQuestion, recoveryAnswer, slogan);
        if ((Boolean) user.get("stayLoggedIn")) previousUser.setStayLoggedIn(true);
    }

    private static void parseMapObject(JSONObject map) {
        String name = (String) map.get("name");
        long size = (Long) map.get("size");
        JSONArray mapTiles = (JSONArray) map.get("map");

        new Map(name, convertTo2DTileArray(mapTiles, (int) size), (int) size);
    }

    private static Tile[][] convertTo2DTileArray(JSONArray mapTiles, int size) {
        Tile[][] result = new Tile[size][];
        for (int i = 0; i < size; i++) {
            result[i] = convertToTileArray((JSONArray) mapTiles.get(i), size);
        }
        return result;
    }

    private static Tile[] convertToTileArray(JSONArray tiles, int size) {
        Tile[] result = new Tile[size];
        for (int i = 0; i < size; i++) {
            result[i] = parseTileObject((JSONObject) tiles.get(i));
        }
        return result;
    }

    private static Tile parseTileObject(JSONObject tile) {
        String texture = (String) tile.get("texture");
        String tree = (String) tile.get("tree");

        return new Tile(Texture.getTextureByName(texture), new Tree(tree));
    }

    public static User getStayLoggedIn() {
        for (User user : Stronghold.getUsers()) {
            if (user.isStayLoggedIn()) return user;
        }
        return null;
    }
}
