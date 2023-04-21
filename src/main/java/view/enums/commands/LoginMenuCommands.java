package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    END("end"),
    BACK("back"),
    LOGIN("user login" +
            "(( (?<usernameTag>-u) (?<username>\".*?[^\\\\]\"|(\\\"|[^\" ])*))" +
            "|( (?<passwordTag>-p) (?<password>\\S*))){2}"),
    ;

    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, LoginMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
