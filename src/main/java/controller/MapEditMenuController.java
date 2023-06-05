package controller;

import javafx.scene.control.ChoiceBox;
import model.Stronghold;
import model.map.Map;
import view.MapEditMenu;

public class MapEditMenuController {

    public static void setMapsOnChoiceBox(ChoiceBox<Map> mapsChoiceBox) {
        mapsChoiceBox.getItems().addAll(Stronghold.getMaps());
        mapsChoiceBox.setValue(Stronghold.getMapByName("original"));
        ShowMapMenuController.setCurrentMap(Stronghold.getMapByName("original"));
        mapsChoiceBox.setOnAction(actionEvent -> ShowMapMenuController.setCurrentMap(mapsChoiceBox.getValue()));
    }
}


//import com.diogonunes.jcolor.Ansi;
//import com.diogonunes.jcolor.Attribute;
//import model.Stronghold;
//import model.map.*;
//import view.enums.messages.MapEditMenuMessages;
//
//import java.util.Random;
//import java.util.regex.Matcher;
//
//public class MapEditMenuController {
//    private static Map currentMap;
//
//    public static MapEditMenuMessages clear(Matcher matcher) {
//        if (!Utils.isValidCommandTags(matcher, "x", "y"))
//            return MapEditMenuMessages.INVALID_COMMAND;
//
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        if (x >= currentMap.getSize() || x < 0 || y >= currentMap.getSize() || y < 0)
//            return MapEditMenuMessages.INVALID_COORDINATE;
//
//        currentMap.getTile(x, y).clear();
//        return MapEditMenuMessages.SUCCESS;
//    }
//
//    public static MapEditMenuMessages checkSetTexture(Matcher matcher) {
//        if (Utils.isValidCommandTags(matcher, "x", "y", "type")) {
//            return setTextureFirstType(matcher);
//        } else if (Utils.isValidCommandTags(matcher, "x1", "y1", "x2", "y2", "type1")) {
//            return setTextureSecondType(matcher);
//        } else return MapEditMenuMessages.INVALID_COMMAND;
//    }
//
//    public static MapEditMenuMessages checkDropCliff(Matcher matcher) {
//        if (!Utils.isValidCommandTags(matcher, "x", "y", "direction"))
//            return MapEditMenuMessages.INVALID_COMMAND;
//
//        String direction = matcher.group("direction");
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        if (x >= currentMap.getSize() || x < 0 || y >= currentMap.getSize() || y < 0)
//            return MapEditMenuMessages.INVALID_COORDINATE;
//        else if (x >= currentMap.getSize() - 4 || y >= currentMap.getSize() - 4 || isSthInArea(x, y) || notSuitableLand(x, y))
//            return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
//        else if (!(direction.equals("r") || direction.equals("u") || direction.equals("l") || direction.equals("d") || direction.equals("random")))
//            return MapEditMenuMessages.INVALID_DIRECTION;
//
//        dropCliff(x, y, direction);
//
//        return MapEditMenuMessages.SUCCESS;
//    }
//
//    public static MapEditMenuMessages checkDropTree(Matcher matcher) {
//        if (!Utils.isValidCommandTags(matcher, "x", "y", "type") ||
//                !Tree.isTreeName(matcher.group("type")))
//            return MapEditMenuMessages.INVALID_COMMAND;
//
//        Tree tree = new Tree(matcher.group("type"));
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        if (x >= currentMap.getSize() || x < 0 || y >= currentMap.getSize() || y < 0)
//            return MapEditMenuMessages.INVALID_COORDINATE;
//        else if (currentMap.getTile(x, y).isFull() || currentMap.getTile(x, y).getTexture().isWater() ||
//                currentMap.getTile(x, y).getTexture().isStone() || currentMap.getTile(x, y).getTexture().equals(Texture.IRON))
//            return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
//
//        currentMap.getTile(x, y).setTree(tree);
//        return MapEditMenuMessages.SUCCESS;
//    }
//
//    private static MapEditMenuMessages setTextureSecondType(Matcher matcher) {
//        int x1 = Integer.parseInt(matcher.group("x1"));
//        int x2 = Integer.parseInt(matcher.group("x2"));
//        int y1 = Integer.parseInt(matcher.group("y1"));
//        int y2 = Integer.parseInt(matcher.group("y2"));
//        Texture type = Texture.getTextureByName(Utils.removeDoubleQuotation(matcher.group("type1")));
//
//        if (x1 >= currentMap.getSize() || x1 < 0 ||
//                y1 >= currentMap.getSize() || y1 < 0 ||
//                x2 >= currentMap.getSize() || x2 < 0 ||
//                y2 >= currentMap.getSize() || y2 < 0 ||
//                x1 > x2 || y1 > y2)
//            return MapEditMenuMessages.INVALID_COORDINATE;
//        else if (type == null || type == Texture.CLIFF)
//            return MapEditMenuMessages.INVALID_TEXTURE_TYPE;
//
//        switch (type) {
//            case SMALL_LAKE, BIG_LAKE -> {
//                return MapEditMenuMessages.INVALID_COMMAND_FOR_CONSTANT_SIZE;
//            }
//            default -> {
//                for (int i = x1; i <= x2; i++)
//                    for (int j = y1; j <= y2; j++)
//                        currentMap.getTile(i, j).setTexture(type);
//            }
//        }
//
//        return MapEditMenuMessages.SUCCESS;
//    }
//
//    private static MapEditMenuMessages setTextureFirstType(Matcher matcher) {
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//        Texture type = Texture.getTextureByName(Utils.removeDoubleQuotation(matcher.group("type")));
//
//        if (x >= currentMap.getSize() || x < 0 || y >= currentMap.getSize() || y < 0)
//            return MapEditMenuMessages.INVALID_COORDINATE;
//        else if (type == null || type == Texture.CLIFF)
//            return MapEditMenuMessages.INVALID_TEXTURE_TYPE;
//
//        switch (type) {
//            case SMALL_LAKE -> {
//                if (x < currentMap.getSize() - 1 && y < currentMap.getSize() - 1) {
//                    for (int i = 0; i < 2; i++)
//                        for (int j = 0; j < 2; j++)
//                            currentMap.getTile(x + i, y + j).setTexture(type);
//                } else return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
//            }
//            case BIG_LAKE -> {
//                if (x < currentMap.getSize() - 3 && y < currentMap.getSize() - 3) {
//                    for (int i = 0; i < 4; i++)
//                        for (int j = 0; j < 4; j++)
//                            currentMap.getTile(x + i, y + j).setTexture(type);
//                } else return MapEditMenuMessages.INVALID_PLACE_TO_DEPLOY;
//            }
//            default -> currentMap.getTile(x, y).setTexture(type);
//        }
//
//        return MapEditMenuMessages.SUCCESS;
//    }
//
//    private static void dropCliff(int x, int y, String direction) {
//        for (int i = 0; i < 5; i++)
//            for (int j = 0; j < 5; j++)
//                currentMap.getTile(x + i, y + j).setTexture(Texture.CLIFF);
//
//        if (direction.equals("random")) {
//            String[] directions = {"r", "u", "l", "d"};
//            direction = directions[new Random().nextInt(directions.length)];
//        }
//
//        switch (direction) {
//            case "d" -> {
//                currentMap.getTile(x + 4, y + 1).setTexture(Texture.SAND);
//                currentMap.getTile(x + 4, y + 3).setTexture(Texture.SAND);
//            }
//            case "l" -> {
//                currentMap.getTile(x + 1, y).setTexture(Texture.SAND);
//                currentMap.getTile(x + 3, y).setTexture(Texture.SAND);
//
//            }
//            case "u" -> {
//                currentMap.getTile(x, y + 1).setTexture(Texture.SAND);
//                currentMap.getTile(x, y + 3).setTexture(Texture.SAND);
//            }
//            case "r" -> {
//                currentMap.getTile(x + 1, y + 4).setTexture(Texture.SAND);
//                currentMap.getTile(x + 3, y + 4).setTexture(Texture.SAND);
//            }
//        }
//    }
//
//    private static boolean notSuitableLand(int x, int y) {
//        for (int i = 0; i < 5; i++)
//            for (int j = 0; j < 5; j++)
//                if (currentMap.getTile(x + i, y + j).getTexture().isWater() ||
//                        currentMap.getTile(x + i, y + j).getTexture().equals(Texture.CLIFF)) return true;
//        return false;
//    }
//
//    private static boolean isSthInArea(int x, int y) {
//        for (int i = 0; i < 5; i++)
//            for (int j = 0; j < 5; j++)
//                if (currentMap.getTile(x + i, y + j).isFull()) return true;
//        return false;
//    }
//
//    public static String getMapsList() {
//        StringBuilder result = new StringBuilder();
//        for (Map map : Stronghold.getMaps())
//            result.append("* ").append(map.getName()).append('\n');
//        return result.toString();
//    }
//
//    public static void saveMap() {
//        Utils.updateDatabase("maps");
//    }
//
//    public static void setCurrentMap(String mapName) {
//        currentMap = Stronghold.getMapByName(mapName);
//    }
//
//    public static Map getCurrentMap() {
//        return currentMap;
//    }
//
//    public static void setNewMapAsCurrentMap(String mapName, int size) {
//        currentMap = new Map(mapName, size);
//        Utils.updateDatabase("maps");
//    }
//
//    public static String showMap() {
//        return showMapForEditing(currentMap, currentMap.getSize() / 2, currentMap.getSize() / 2);
//    }
//
//    public static String showMapForEditing(Map map, int x, int y) {
//        String output = "";
//        Attribute backgroundColor;
//        Attribute textColor;
//        for (int i = 0, k = 0; i < map.getSize(); i++, k++) {
//            for (int j = 0, l = 0; j < map.getSize(); j++, l++) {
//                if (l % 8 == 0) {
//                    output += "|";
//                    j--;
//                } else if (k % 4 == 0) output += "-";
//                else {
//                    Tile tile = map.getTile(i, j);
//                    backgroundColor = tile.getTexture().getColor().getColor();
//
//                    if (i == x && j == y) textColor = Color.RED_TEXT.getColor();
//                    else textColor = Color.WHITE_TEXT.getColor();
//
//                    if (tile.getTree() != null) output += (Ansi.colorize("T", textColor, backgroundColor));
//                    else output += (Ansi.colorize("#", textColor, backgroundColor));
//                }
//            }
//            if (k % 4 == 0) i--;
//            output += '\n';
//        }
//        return output;
//    }
//
//    public static boolean isMapName(String input) {
//        return Stronghold.isMapName(input);
//    }
//}
