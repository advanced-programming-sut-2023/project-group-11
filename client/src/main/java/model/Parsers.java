package model;

import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

        return new Map(name, convertTo2DTileArray(mapTiles, (int) size), (int) size);
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
        Texture texture = Texture.valueOf(((String) tile.get("texture")));
        Tree tree = null;
        if (tile.get("tree") != null)
            tree = new Tree((String) ((JSONObject) tile.get("tree")).get("name"));

        return new Tile(texture, tree);
    }
}
