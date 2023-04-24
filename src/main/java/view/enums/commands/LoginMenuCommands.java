package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    END("end"),
    BACK("back"),
    SHOW_CURRENT_MENU("show current menu"),
    LOGIN("user login" +
            "(( -u (?<username>\"[^\"]+\"|\\S+))" +
            "|( -p (?<password>\\S*))){2}" +
            "( (?<stayLoggedIn>--stay-logged-in))?"),
    FORGOT_PASSWORD("forgot my password" +
            " -u (?<username>\"[\"]+\"|\\S+)"),
    LOGOUT("user logout"),
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
