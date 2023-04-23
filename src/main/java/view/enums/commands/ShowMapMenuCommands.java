package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShowMapMenuCommands {
    END("end"),
    BACK("back"),
    SHOW_CURRENT_MENU("show current menu"),
    MOVE_IN_MAP("map(((?<upDirection> up)( (?<upCount>\\d+))?)|((?<leftDirection> left)( (?<leftCount>\\d+))?)|(?<downDirection> down( (?<downCount>\\d+))?)|(?<rightDirection> right( (?<rightCount>\\d+))?)){1,4}"),
    SHOW_DETAILS("show details ((-x (?<xCoordinate>\\\\d+))" +
            "|( -y (?<yCoordinate>\\\\d+))){2}")
    ;

    private final String regex;

    ShowMapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ShowMapMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
