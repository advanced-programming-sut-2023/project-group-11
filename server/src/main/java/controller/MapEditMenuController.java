package controller;

import model.Stronghold;
import model.User;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.map.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapEditMenuController {
    private static Map currentMap;

    public static void saveMap(ArrayList<Object> parameters) {
        Utils.updateDatabase("maps");
    }

    public static ArrayList<String> getMapNames(ArrayList parameters) {
        ArrayList<String> mapNames = new ArrayList<>();
        for (Map map : Stronghold.getMaps()) {
            if (map.getOwners().contains(Stronghold.getCurrentUser().getUsername())) {
                if (map.getMainOwner().equals(Stronghold.getCurrentUser())) mapNames.add(map.getName());
                else mapNames.add(map.getName() + " -received");
            }
        }
        return mapNames;
    }

    public static ArrayList<String> getUserNames(ArrayList parameters) {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : Stronghold.getUsers())
            usernames.add(user.getUsername());
        return usernames;
    }

    public static void setCurrentMap(ArrayList<Object> parameters) {
        String mapName = (String) parameters.get(0);
        currentMap = Stronghold.getMapByName(mapName);
    }

    public static Map getCurrentMap(ArrayList<Object> parameters) {
        return currentMap;
    }

    public static Message checkMakeNewMap(ArrayList<Object> parameters) {
        String mapName = (String) parameters.get(0);
        String mapSize = (String) parameters.get(1);
        if (mapName.isEmpty()) return Message.MAP_NAME_FIELD_EMPTY;
        else if (mapSize.isEmpty()) return Message.MAP_SIZE_FIELD_EMPTY;
        else if (!mapSize.matches("\\d+")) return Message.INVALID_MAP_SIZE_FORMAT;
        else if (Stronghold.getMapByName(mapName) != null) return Message.MAP_EXIST;
        else if (Integer.parseInt(mapSize) < 50 || Integer.parseInt(mapSize) > 200)
            return Message.INVALID_MAP_SIZE;

        currentMap = new Map(mapName, Integer.parseInt(mapSize));
        ShowMapMenuController.setCurrentMap(new ArrayList<>(Arrays.asList(currentMap.getName())));
        Utils.updateDatabase("maps");
        return Message.SUCCESS;
    }
    public static Message checkShareMap(ArrayList<Object> parameters) {
        String mapName = (String) parameters.get(0);
        String username = (String) parameters.get(1);
        Map sendingMap = Stronghold.getMapByName(mapName);

        if (sendingMap.getOwners().contains(username)) return Message.USER_HAS_MAP;

        sendingMap.addOwner(username);
        Utils.updateDatabase("maps");
        return Message.SUCCESS;
    }

    public static Message setTexture(ArrayList<Object> parameters) {
        int selectedTilesSize = (Integer) parameters.get(0);
        String textureName = (String) parameters.get(1);
        int selectedTileX = (Integer) parameters.get(2);
        int selectedTileY = (Integer) parameters.get(3);
        int height = (Integer) parameters.get(4);
        int width = (Integer) parameters.get(5);
        Texture texture = Texture.getTextureByName(textureName);

        if (selectedTilesSize == 0) return Message.EMPTY_SELECTED_TILES;
        else if (texture.equals(Texture.BIG_LAKE) || texture.equals(Texture.SMALL_LAKE) || texture.equals(Texture.CLIFF)) {
            if (selectedTilesSize > 1) return Message.SELECT_ONLY_ONE_TILE;
            switch (texture) {
                case SMALL_LAKE -> {
                    if (!isSuitableLandForLake(selectedTileX, selectedTileY, 3))
                        return Message.INVALID_PLACE_TO_DEPLOY;
                    else {
                        for (int i = 0; i < 3; i++)
                            for (int j = 0; j < 3; j++)
                                currentMap.getTile(selectedTileX + i, selectedTileY + j).setTexture(Texture.SMALL_LAKE);
                    }
                }
                case BIG_LAKE -> {
                    if (!isSuitableLandForLake(selectedTileX, selectedTileY, 5))
                        return Message.INVALID_PLACE_TO_DEPLOY;
                    else {
                        for (int i = 0; i < 5; i++)
                            for (int j = 0; j < 5; j++)
                                currentMap.getTile(selectedTileX + i, selectedTileY + j).setTexture(Texture.BIG_LAKE);
                    }
                }
                case CLIFF -> {
                    if (!isSuitableLandForCliff(selectedTileX, selectedTileY))
                        return Message.INVALID_PLACE_TO_DEPLOY;
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
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    currentMap.getTile(selectedTileX + i, selectedTileY + j).setTexture(texture);
        }
        return Message.SUCCESS;
    }

    public static void clear(ArrayList<Object> parameters) {
        int selectedTileX = (Integer) parameters.get(0);
        int selectedTileY = (Integer) parameters.get(1);
        int width = (Integer) parameters.get(2);
        int height = (Integer) parameters.get(3);

        ArrayList<Tile> tiles = ShowMapMenuController.getTilesList(new ArrayList<>(Arrays.asList(selectedTileX, selectedTileY, height, width)));
        tiles.forEach(Tile::clear);
    }

    public static Message dropTree(ArrayList<Object> parameters) {
        int selectedTileX = (Integer) parameters.get(0);
        int selectedTileY = (Integer) parameters.get(1);
        int width = (Integer) parameters.get(2);
        int height = (Integer) parameters.get(3);
        String treeName = (String) parameters.get(4);
        ArrayList<Tile> tiles = ShowMapMenuController.getTilesList(new ArrayList<>(Arrays.asList(selectedTileX, selectedTileY, height, width)));

        if (!isSuitableLandForTree(tiles)) return Message.INVALID_PLACE_TO_DEPLOY;

        for (Tile tile : tiles)
            tile.setTree(new Tree(treeName));
        return Message.SUCCESS;
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
