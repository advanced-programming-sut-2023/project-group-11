package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_CURRENT_MENU("show current menu"),
    NEXT_TURN("next turn"),
    SHOW_MAP("show\\s*map(( -x (?<xCoordinate>\\d+))" +
            "|( -y (?<yCoordinate>\\d+))){2}"),
    DROP_BUILDING("drop\\s*building( -x (?<xCoordinate>\\d+)" +
            "|( -y (?<yCoordinate>\\d+))" +
            "|( -t (?<type>\"[^\"]+\"|\\S+))){3}"),
    SELECT_BUILDING("select\\s*building( -x (?<xCoordinate>\\d+)" +
            "|( -y (?<yCoordinate>\\d+))){2}"),
    SHOW_POPULARITY("show popularity( (?<factors>factors))?"),
    SHOW_FOOD_LIST("show food list"),
    CHANGE_FOOD_RATE("food\\s*rate -r (?<rateNumber>\\d+)"),
    SHOW_FOOD_RATE("food\\s*rate show"),
    CHANGE_TAX_RATE("tax\\s*rate -r (?<rateNumber>\\d+)"),
    SHOW_TAX_RATE("tax\\s*rate show"),
    CHANGE_FEAR_RATE("fear\\s*rate -r (?<rateNumber>\\d+)"),
    SHOW_FEAR_RATE("fear\\s*rate show"),
    SELECT_UNIT("select\\s*unit( -x (?<xCoordinate>\\d+)" +
            "|( -y (?<yCoordinate>\\d+))" +
            "|( -t (?<type>\"[^\"]+\"|\\S+))){3}"),
    DROP_UNIT("drop\\s*unit( -x (?<xCoordinate>\\d+)" +
            "|( -y (?<yCoordinate>\\d+))" +
            "|( -t (?<type>\"[^\"]+\"|\\S+))" +
            "|( -c (?<count>\\d+))){4}"),
    SHOW_RESOURCE_LEFT("show resource -r (?<resource>\"[^\"]+\"|\\S+)"),
    SHOW_DETAILS("show details(( -x (?<xCoordinate>\\d+))" +
                         "|( -y (?<yCoordinate>\\d+))){2}"),
    OPEN_TRADE_MENU("enter trade menu"),
    OPEN_MARKET_MENU("enter market menu"),
    SHOW_GOLD("show gold");

    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, GameMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
