package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.chat.*;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Parsers {

    public static ArrayList<Map> parseMapArrayList(JSONArray jsonArray) {
        ArrayList<Map> maps = new ArrayList<>();
        jsonArray.forEach(map -> maps.add(parseMapObject((JSONObject) map)));
        return maps;
    }

    public static Map parseMapObject(JSONObject map) {
        String name = (String) map.get("name");
        long size = (Long) map.get("size");
        JSONArray mapTiles = (JSONArray) map.get("tiles");

        return new Map(name, convertTo2DTileArray(mapTiles, (int) size, (int) size), (int) size);
    }

    public static Tile[][] convertTo2DTileArray(JSONArray mapTiles, int rowsCount, int columnCount) {
        Tile[][] result = new Tile[rowsCount][];
        for (int i = 0; i < rowsCount; i++) {
            result[i] = convertToTileArray((JSONArray) mapTiles.get(i), columnCount);
        }
        return result;
    }

    private static Tile[] convertToTileArray(JSONArray tiles, int columnCount) {
        Tile[] result = new Tile[columnCount];
        for (int i = 0; i < columnCount; i++) {
            result[i] = parseTileObject((JSONObject) tiles.get(i));
        }
        return result;
    }

    public static Tile parseTileObject(JSONObject tile) {
        int[] location = new int[]{(Integer) ((JSONArray) tile.get("location")).get(0), (Integer) ((JSONArray) tile.get("location")).get(1)};
        Texture texture = Texture.valueOf(((String) tile.get("texture")));
        Tree tree = null;
        try {
            if (tile.get("tree") != null)
                tree = new Tree((String) ((JSONObject) tile.get("tree")).get("name"));
        } catch (Exception e) {
        }

        return new Tile(texture, tree, location[0], location[1]);
    }

    public static Message parseMessageObject(JSONObject message) {
        int id = message.getInt("id");
        String content = message.getString("content");
        String senderName = message.getString("senderName");
        String sentTime = message.getString("sentTime");
        String avatarAddress = message.getString("avatarAddress");
        boolean seen = message.getBoolean("seen");
        boolean isOwnerCurrentUser = message.getBoolean("isOwnerCurrentUser");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Message(id, content, senderName, sentTime, avatarAddress, seen, isOwnerCurrentUser);
    }

    public static Chat parseChatObject(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ChatType chatType = ChatType.valueOf(jsonObject.getString("chatType"));
        if (chatType.equals(ChatType.GLOBAL))
            return GlobalChat.getInstance();
        if (chatType.equals(ChatType.PRIVATE))
            return new PrivateChat(name);
        if (chatType.equals(ChatType.CHAT_ROOM))
            return new ChatRoom(name);
        return null;
    }

    public static ArrayList<Game> parseGamesArrayList(String json) {
        json = json.substring(0, json.lastIndexOf("]") + 1);
        System.out.println(json);
        Type type = new TypeToken<ArrayList<Game>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public static ArrayList<User> parseUserArrayList(String json) {
        Type userDatabaseType = new TypeToken<ArrayList<User>>() {
        }.getType();
        return new Gson().fromJson(json, userDatabaseType);
    }
}
