package controller;

import model.Stronghold;
import model.map.Map;
import model.map.Tile;
import org.apache.commons.lang3.StringUtils;
import view.ShowMapMenu;
import view.enums.messages.ShowMapMenuMessages;

import java.util.regex.Matcher;

public class ShowMapMenuController {
    public static ShowMapMenuMessages checkShowDetails(Matcher matcher, String command) {
        return null;
    }

    public static ShowMapMenuMessages checkMoveInMap(Matcher matcher, String command) {
        if (!checkMoveInMapTags(command)) return ShowMapMenuMessages.INVALID_COMMAND;

        String upDirection = matcher.group("upDirection");
        String leftDirection = matcher.group("leftDirection");
        String downDirection = matcher.group("downDirection");
        String rightDirection = matcher.group("rightDirection");
        int upCount = 0, leftCount = 0, downCount = 0, rightCount = 0;

        if (upDirection != null)
            if (matcher.group("upCount") != null) upCount = Integer.parseInt(matcher.group("upCount"));
            else upCount = 1;
        if (leftDirection != null)
            if (matcher.group("leftCount") != null) leftCount = Integer.parseInt(matcher.group("leftCount"));
            else leftCount = 1;
        if (downDirection != null)
            if (matcher.group("downCount") != null) downCount = Integer.parseInt(matcher.group("downCount"));
            else downCount = 1;
        if (rightDirection != null)
            if (matcher.group("rightCount") != null) rightCount = Integer.parseInt(matcher.group("rightCount"));
            else rightCount = 1;

        int verticalMove = downCount - upCount;
        int horizontalMove = rightCount - leftCount;

        if (!Utils.isValidCoordinates(ShowMapMenu.xCoordinate + verticalMove, ShowMapMenu.yCoordinate + horizontalMove))
            return ShowMapMenuMessages.INVALID_COORDINATE;

        ShowMapMenu.xCoordinate += verticalMove;
        ShowMapMenu.yCoordinate += horizontalMove;

        return ShowMapMenuMessages.SUCCESS;
    }


    private static boolean checkMoveInMapTags(String command) {
        return StringUtils.countMatches(command, "up") <= 1 &&
                StringUtils.countMatches(command, "down") <= 1 &&
                StringUtils.countMatches(command, "right") <= 1 &&
                StringUtils.countMatches(command, "left") <= 1;
    }

    public static String showMap(int x, int y) {
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

    public static String showMapDetails(int x, int y) {
        String output = "";
        Map map = Stronghold.getMapByName("first");//TODO: change this
        for (int i = (Math.max(x - 5, 0)); i < x + 5 && i < map.getSize(); i++) {
            for (int j = (Math.max(y - 25, 0)); j < y + 25 && j < map.getSize(); j++) {
                Tile tile = map.getTile(i, j);
                if (i == x && j == y) output += '!';
                if (tile.getUnits().size() > 0) output += 'S';
                if (tile.getBuilding() != null) output += 'B';
                if (tile.getTree() != null) output += 'T';
                output += '#';
            }
            output += '\n';
        }
        return output;
    }
}
}
