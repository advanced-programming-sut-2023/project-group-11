package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Stronghold;
import model.User;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EntryMenuController {
    public static void fillAllFieldsWithPreviousData() {
        parseUsers();
        parseMaps();
    }

    private static void parseUsers() {
        try (FileReader reader = new FileReader("src/main/resources/JSON/users.json")) {
            Type userDatabaseType = new TypeToken<ArrayList<User>>() {
            }.getType();
            Stronghold.getUsers().addAll(new Gson().fromJson(reader, userDatabaseType));
        } catch (IOException ignored) {
        }
    }

    public static void parseMaps() {
        Stronghold.setMaps(new ArrayList<>());
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/JSON/maps.json")) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            jsonArray.forEach(map -> parseMapObject((JSONObject) map));
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void parseMapObject(JSONObject map) {
        String name = (String) map.get("name");
        long size = (Long) map.get("size");
        JSONArray mapTiles = (JSONArray) map.get("tiles");
        ArrayList<String> owners = (ArrayList<String>) map.get("owners");

        new Map(name, convertTo2DTileArray(mapTiles, (int) size), (int) size, owners);
    }

    private static Tile[][] convertTo2DTileArray(JSONArray mapTiles, int size) {
        Tile[][] result = new Tile[size][];
        for (int i = 0; i < size; i++) {
            result[i] = convertToTileArray((JSONArray) mapTiles.get(i), size, i);
        }
        return result;
    }

    private static Tile[] convertToTileArray(JSONArray tiles, int size, int row) {
        Tile[] result = new Tile[size];
        for (int i = 0; i < size; i++) {
            result[i] = parseTileObject((JSONObject) tiles.get(i), i, row);
        }
        return result;
    }

    private static Tile parseTileObject(JSONObject tile, int column, int row) {
        Texture texture = Texture.valueOf(((String) tile.get("texture")));
        Tree tree = null;
        if (tile.get("tree") != null)
            tree = new Tree((String) ((JSONObject) tile.get("tree")).get("name"));

        return new Tile(texture, tree, column, row);
    }

    public static User getStayLoggedIn() {
        for (User user : Stronghold.getUsers())
            if (user.isStayLoggedIn()) {
                Stronghold.setCurrentUser(user);
                return user;
            }
        return null;
    }
}
