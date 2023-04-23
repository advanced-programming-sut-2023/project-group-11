package controller;

import model.Stronghold;
import model.map.Map;
import model.map.Tile;
import view.enums.messages.ShowMapMenuMessages;

import java.util.regex.Matcher;

public class ShowMapMenuController {
    public static ShowMapMenuMessages checkShowDetails(Integer xCoordinate, Integer yCoordinate) {
        return null;
    }

    public static ShowMapMenuMessages checkMoveInMap(Matcher matcher, Integer xCoordinate, Integer yCoordinate) {
        return null;
    }

    public static ShowMapMenuMessages checkShowMap(Matcher matcher) {
        Integer x = Integer.parseInt(matcher.group("x"));
        Integer y = Integer.parseInt(matcher.group("y"));
        Map map = Stronghold.getMapByName("first");//TODO: change this
        int mapSize = map.getSize();
        if (x > mapSize || y > mapSize) return ShowMapMenuMessages.INVALID_COORDINATE;
        return ShowMapMenuMessages.SUCCESS;
    }


    public static String showMap(Matcher matcher) {
        String output = "";
        Integer x = Integer.parseInt(matcher.group("x"));
        Integer y = Integer.parseInt(matcher.group("y"));
        Map map = Stronghold.getMapByName("first");//TODO: change this
        for (int i = (Math.max(x - 5, 0)); i < x + 5 && i < map.getSize(); i++) {
            for (int j = (Math.max(y - 25, 0)); j < y + 25 && j < map.getSize(); j++) {
                Tile tile = map.getTile(i, j);
                if (i == x && j == y) output += '!';
                else if (tile.getUnits().size() > 0) output += 'S';
                else if (tile.getBuilding() != null) output += 'B';
                else if (tile.getTree() != null) output += 'T';
                else output += '#';
            }
            output += '\n';
        }
        return output;
    }
}
