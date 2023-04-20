package controller;

import model.Stronghold;
import model.map.Map;
import model.map.Texture;
import view.Enums.Messages.MapEditMenuMessages;

import java.util.regex.Matcher;

public class MapEditMenuController {
    private static Map currentMap;

    public static MapEditMenuMessages checkSetTexture(Matcher matcher) {
        if (matcher.group("x") != null && matcher.group("y") != null && matcher.group("type") != null) {
            return setTextureFirstType(matcher);
        } else if (matcher.group("x1") != null && matcher.group("y1") != null &&
                matcher.group("x2") != null && matcher.group("y2") != null &&
                matcher.group("type1") != null) {
            return setTextureSecondType(matcher);
        } else return MapEditMenuMessages.INVALID_COMMAND;
    }

    private static MapEditMenuMessages setTextureSecondType(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        Texture type = Texture.getTextureByName(matcher.group("type1"));

        if (x1 >= currentMap.getSize() || x1 < 0 ||
                y1 >= currentMap.getSize() || y1 < 0 ||
                x2 >= currentMap.getSize() || x2 < 0 ||
                y2 >= currentMap.getSize() || y2 < 0 ||
                x1 > x2 || y1 > y2)
            return MapEditMenuMessages.INVALID_COORDINATE;
        else if (type == null || type == Texture.CLIFF)
            return MapEditMenuMessages.INVALID_TEXTURE_TYPE;

        switch (type) {
            case SMALL_LAKE, BIG_LAKE -> {
                return MapEditMenuMessages.INVALID_COMMAND_FOR_CONSTANT_SIZE;
            }
            default -> {
                for (int i = x1; i <= x2; i++)
                    for (int j = y1; j <= y2; j++)
                        currentMap.getTile(i, j).setTexture(type);
            }
        }

        return MapEditMenuMessages.SUCCESS;
    }

    private static MapEditMenuMessages setTextureFirstType(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Texture type = Texture.getTextureByName(matcher.group("type"));

        if (x >= currentMap.getSize() || x < 0 || y >= currentMap.getSize() || y < 0)
            return MapEditMenuMessages.INVALID_COORDINATE;
        else if (type == null || type == Texture.CLIFF)
            return MapEditMenuMessages.INVALID_TEXTURE_TYPE;

        switch (type) {
            case SMALL_LAKE -> {
                if (x < currentMap.getSize() - 1 && y < currentMap.getSize() - 1 ) {
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < 2; j++)
                            currentMap.getTile(x+i, y+j).setTexture(type);
                } else return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
            }
            case BIG_LAKE -> {
                if (x < currentMap.getSize() - 3 && y < currentMap.getSize() - 3 ) {
                    for (int i = 0; i < 4; i++)
                        for (int j = 0; j < 4; j++)
                            currentMap.getTile(x+i, y+j).setTexture(type);
                } else return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
            }
            default -> currentMap.getTile(x, y).setTexture(type);
        }

        return MapEditMenuMessages.SUCCESS;
    }

    public static MapEditMenuMessages clear(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (x >= currentMap.getSize() || x < 0 || y >= currentMap.getSize() || y < 0)
            return MapEditMenuMessages.INVALID_COORDINATE;

        currentMap.getTile(x, y).clear();
        return MapEditMenuMessages.SUCCESS;
    }

    public static MapEditMenuMessages checkDropRock(Matcher matcher) {
        return null;
    }

    public static MapEditMenuMessages checkDropTree(Matcher matcher) {
        return null;
    }

    public static String getMapsList() {
        StringBuilder result = new StringBuilder();
        for (Map map : Stronghold.getMaps())
            result.append("* ").append(map.getName());
        return result.toString();
    }

    public static boolean isMapName(String name) {
        for (Map map : Stronghold.getMaps())
            if (map.getName().equals(name)) return true;
        return false;
    }

    public static void saveMap() {
        //TODO: save map in dataBase
    }

    public static void setCurrentMap(String mapName) {
        MapEditMenuController.currentMap = Stronghold.getMapByName(mapName);
    }
}
