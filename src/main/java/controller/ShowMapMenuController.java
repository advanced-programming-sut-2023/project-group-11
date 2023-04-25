package controller;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import model.Game;
import model.Stronghold;
import model.map.Color;
import model.map.Map;
import model.map.Tile;
import org.apache.commons.lang3.StringUtils;
import view.ShowMapMenu;
import view.enums.messages.ShowMapMenuMessages;

import java.util.regex.Matcher;

public class ShowMapMenuController {
    public static ShowMapMenuMessages checkShowDetails(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
            return ShowMapMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y))
            return ShowMapMenuMessages.INVALID_COORDINATE;
        return ShowMapMenuMessages.SUCCESS;
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

        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), ShowMapMenu.xCoordinate + verticalMove, ShowMapMenu.yCoordinate + horizontalMove))
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

    public static String showMap(String name, int x, int y) {//TODO: in Utils?
        String output = "";
        Map map = Stronghold.getMapByName(name);
        Attribute backgroundColor;
        Attribute textColor;
        for (int i = (Math.max(x - 7, 0)), k = (Math.max(x - 7, 0)); i < x + 7 && i < map.getSize(); i++, k++) {
            for (int j = (Math.max(y - 50, 0)), l = (Math.max(y - 50, 0)); j < y + 50 && j < map.getSize(); j++, l++) {
                if (l % 8 == 0) {
                    output += "|";
                    j--;
                } else if (k % 4 == 0) output += "-";
                else {
                    Tile tile = map.getTile(i, j);
                    backgroundColor = tile.getTexture().getColor().getColor();

                    if (i == x && j == y) textColor = Color.RED_TEXT.getColor();
                    else textColor = Color.WHITE_TEXT.getColor();

                    if (tile.getUnits().size() > 0) output += (Ansi.colorize("S", textColor, backgroundColor));
                    else if (tile.getBuilding() != null) output += (Ansi.colorize("B", textColor, backgroundColor));
                    else if (tile.getTree() != null) output += (Ansi.colorize("T", textColor, backgroundColor));
                    else output += (Ansi.colorize("#", textColor, backgroundColor));
                }
            }
            if (k % 4 == 0) i--;
            output += '\n';
        }
        return output;
    }

    public static String showMapDetails(int x, int y) {
        String output = "";
        Map map = Stronghold.getCurrentGame().getMap();
        Tile tile = map.getTile(x, y);
        output += tile.toString();
        return output;
    }

    public static String getCurrentMapName() {
        return Stronghold.getCurrentGame().getMap().getName();
    }
}
