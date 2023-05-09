package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_CURRENT_MENU("show current menu"),
    NEXT_TURN("next turn"),
    SHOW_MAP("show map(( -x (?<xCoordinate>\\d+))" +
            "|( -y (?<yCoordinate>\\d+))){2}"),
    DROP_BUILDING("dropbuilding((?<xGroup> -x (?<xCoordinate>\\d+))" +
            "|(?<yGroup> -y (?<yCoordinate>\\d+))" +
            "|(?<typeGroup> -type (?<type>\"[^\"]+\"|\\S+))){3}"),
    SELECT_BUILDING("select building((?<xGroup> -x (?<xCoordinate>\\d+))" +
            "|(?<yGroup> -y (?<yCoordinate>\\d+))){2}"),
    SHOW_POPULARITY("show popularity( (?<factors>factors))?"),
    SHOW_FOOD_LIST("show food list"),
    CHANGE_FOOD_RATE("food rate -r (?<rateNumber>\\d+)"),
    SHOW_FOOD_RATE("food rate show"),
    CHANGE_TAX_RATE("tax rate -r (?<rateNumber>\\d+)"),
    SHOW_TAX_RATE("tax rate show"),
    CHANGE_FEAR_RATE("fear rate -r (?<rateNumber>\\d+)"),
    SHOW_FEAR_RATE("fear rate show"),
    SELECT_UNIT("select unit((?<xGroup> -x (?<xCoordinate>\\d+))" +
            "|(?<yGroup> -y (?<yCoordinate>\\d+))" +
            "|(?<typeGroup> -type (?<type>\"[^\"]+\"|\\S+))){3}"),
    DROP_UNIT("dropunit(( -x (?<xCoordinate>\\d+))" +
            "|( -y (?<yCoordinate>\\d+))" +
            "|( -t (?<type>\"[^\"]+\"|\\S+))" +
            "|( -c (?<count>\\d+))){4}"),
    ;

    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, GameMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
