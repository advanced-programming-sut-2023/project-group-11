package controller;

import model.Stronghold;
import model.map.Map;
import model.map.Tile;
import org.apache.commons.lang3.StringUtils;
import view.enums.messages.ShowMapMenuMessages;

import java.util.regex.Matcher;

public class ShowMapMenuController {
    public static ShowMapMenuMessages checkShowDetails(Matcher matcher, String command) {
        return null;
    }

    public static ShowMapMenuMessages checkMoveInMap(Matcher matcher, String command) {
        if (checkMoveInMapTags(command)) return ShowMapMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.checkCoordinates(x, y)) return ShowMapMenuMessages.INVALID_COORDINATE;
        return ShowMapMenuMessages.SUCCESS;
    }


    private static boolean checkMoveInMapTags(String command) {
        return StringUtils.countMatches(command, "up") <= 1 &&
                StringUtils.countMatches(command, "down") <= 1 &&
                StringUtils.countMatches(command, "right") <= 1 &&
                StringUtils.countMatches(command, "left") <= 1;
    }

    public static String showMap(String xCoordinate, String yCoordinate) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        String output = "";
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
