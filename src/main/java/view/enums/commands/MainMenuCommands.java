package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    END("end"),
    LOGOUT("logout"),
    SHOW_CURRENT_MENU("show current menu"),
    ENTER_MAP_EDIT_MENU("enter map edit menu"),
    ENTER_PROFILE_MENU("enter profile menu"),
    START_GAME("start game( -m (?<mapName>\"[^\"]+\"|\\S+)| -g (?<guests>((\"[^\"]+\"|\\S+) ?)+)){2}"),
    ;

    private final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MainMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
