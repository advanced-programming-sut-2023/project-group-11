package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapEditMenuCommands {
    SET_TEXTURE("settexture(( -x (?<x>-?\\d+)| -y (?<y>-?\\d+)| -t (?<type>\\S+|\".+\")){3}|" +
            "( -x1 (?<x1>-?\\d+)| -y1 (?<y1>-?\\d+)| -x2 (?<x2>-?\\d+)| -y2 (?<y2>-?\\d+)| -t (?<type1>\\S+|\".+\")){5})"),
    CLEAR("clear( -x (?<x>-?\\d+)| -y (?<y>-?\\d+)){2}"),
    DROP_CLIFF("dropcliff( -x (?<x>-?\\d+)| -y (?<y>-?\\d+)| -d (?<direction>\\S+)){3}"),
    DROP_TREE("droptree( -x (?<x>-?\\d+)| -y (?<y>-?\\d+)| -t (?<type>\\S+|\".+\")){3}"),
    ;

    private final String regex;

    MapEditMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MapEditMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
