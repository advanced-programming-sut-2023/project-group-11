package controller;

import javafx.scene.control.ChoiceBox;
import model.Stronghold;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;
import view.enums.messages.MapEditMenuMessages;

import java.util.ArrayList;
import java.util.Random;

public class MapEditMenuController {
    private static Map currentMap;

    public static void setMapsOnChoiceBox(ChoiceBox<Map> mapsChoiceBox) {
        mapsChoiceBox.getItems().addAll(Stronghold.getMaps());
        mapsChoiceBox.setValue(Stronghold.getMapByName("original"));
        ShowMapMenuController.setCurrentMap(Stronghold.getMapByName("original"));
        currentMap = Stronghold.getMapByName("original");
        mapsChoiceBox.setOnAction(actionEvent -> {
            ShowMapMenuController.setCurrentMap(mapsChoiceBox.getValue());
            MapEditMenuController.setCurrentMap(mapsChoiceBox.getValue());
        });
    }

    public static void saveMap() {
        Utils.updateDatabase("maps");
    }

    public static void setCurrentMap(Map map) {
        currentMap = map;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static MapEditMenuMessages checkMakeNewMap(String mapName, String mapSize) {
        if (mapName.isEmpty()) return MapEditMenuMessages.MAP_NAME_FIELD_EMPTY;
        else if (mapSize.isEmpty()) return MapEditMenuMessages.MAP_SIZE_FIELD_EMPTY;
        else if (!mapSize.matches("\\d+")) return MapEditMenuMessages.INVALID_MAP_SIZE_FORMAT;
        else if (Stronghold.getMapByName(mapName) != null) return MapEditMenuMessages.MAP_EXIST;
        else if (Integer.parseInt(mapSize) < 50 || Integer.parseInt(mapSize) > 200)
            return MapEditMenuMessages.INVALID_MAP_SIZE;

        currentMap = new Map(mapName, Integer.parseInt(mapSize));
        ShowMapMenuController.setCurrentMap(currentMap);
        Utils.updateDatabase("maps");
        return MapEditMenuMessages.SUCCESS;
    }

    public static void clear(ArrayList<Tile> tiles) {
        for (Tile tile : tiles) tile.clear();
    }

    public static MapEditMenuMessages setTexture(ArrayList<Tile> tiles, String textureName, int selectedTileX, int selectedTileY) {
        Texture texture = Texture.getTextureByName(textureName);

        if (tiles.size() == 0) return MapEditMenuMessages.EMPTY_SELECTED_TILES;
        else if (texture.equals(Texture.BIG_LAKE) || texture.equals(Texture.SMALL_LAKE) || texture.equals(Texture.CLIFF)) {
            if (tiles.size() > 1) return MapEditMenuMessages.SELECT_ONLY_ONE_TILE;
            switch (texture) {
                case SMALL_LAKE -> {
                    if (!isSuitableLandForLake(selectedTileX, selectedTileY, 3))
                        return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
                    else {
                        for (int i = 0; i < 3; i++)
                            for (int j = 0; j < 3; j++)
                                currentMap.getTile(selectedTileX + i, selectedTileY + j).setTexture(Texture.SMALL_LAKE);
                    }
                }
                case BIG_LAKE -> {
                    if (!isSuitableLandForLake(selectedTileX, selectedTileY, 5))
                        return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
                    else {
                        for (int i = 0; i < 5; i++)
                            for (int j = 0; j < 5; j++)
                                currentMap.getTile(selectedTileX + i, selectedTileY + j).setTexture(Texture.BIG_LAKE);
                    }
                }
                case CLIFF -> {
                    if (!isSuitableLandForCliff(selectedTileX, selectedTileY))
                        return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
                    else {
                        ArrayList<int[][]> directions = buildCoordinates();
                        int[][] direction = directions.get(new Random().nextInt(directions.size()));
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if ((i == direction[0][0] && j == direction[0][1]) || (i == direction[1][0] && j == direction[1][1]))
                                    continue;
                                currentMap.getTile(selectedTileX + i, selectedTileY + j).setTexture(Texture.CLIFF);
                            }
                        }
                    }
                }
            }
        } else {
            for (Tile tile : tiles)
                tile.setTexture(texture);
        }
        return MapEditMenuMessages.SUCCESS;
    }

    public static MapEditMenuMessages dropTree(ArrayList<Tile> tiles, String treeName) {
        if (!isSuitableLandForTree(tiles)) return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;

        for (Tile tile : tiles)
            tile.setTree(new Tree(treeName));
        return MapEditMenuMessages.SUCCESS;
    }

    private static boolean isSuitableLandForLake(int tileX, int tileY, int lakeSize) {
        if (currentMap.getSize() <= tileX + lakeSize || currentMap.getSize() <= tileY + lakeSize) return false;
        for (int i = 0; i < lakeSize; i++)
            for (int j = 0; j < lakeSize; j++) {
                if (currentMap.getTile(tileX + i, tileY + j).getTexture().equals(Texture.CLIFF) ||
                        currentMap.getTile(tileX + i, tileY + j).isFull())
                    return false;
            }
        return true;
    }

    private static boolean isSuitableLandForCliff(int tileX, int tileY) {
        if (currentMap.getSize() <= tileX + 5 || currentMap.getSize() <= tileY + 5) return false;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                if (currentMap.getTile(tileX + i, tileY + j).getTexture().isWater() ||
                        currentMap.getTile(tileX + i, tileY + j).getTexture().equals(Texture.CLIFF) ||
                        currentMap.getTile(tileX + i, tileY + j).isFull())
                    return false;
            }
        return true;
    }

    private static boolean isSuitableLandForTree(ArrayList<Tile> tiles) {
        for (Tile tile : tiles)
            if (tile.isFull() || tile.getTexture().isWater() || tile.getTexture().isStone() || tile.getTexture().isIron())
                return false;
        return true;
    }

    private static ArrayList<int[][]> buildCoordinates() {
        ArrayList<int[][]> directions = new ArrayList<>();
        directions.add(new int[][]{{4, 1}, {4, 3}});
        directions.add(new int[][]{{1, 0}, {3, 0}});
        directions.add(new int[][]{{0, 1}, {0, 3}});
        directions.add(new int[][]{{1, 4}, {3, 4}});
        return directions;
    }
}
