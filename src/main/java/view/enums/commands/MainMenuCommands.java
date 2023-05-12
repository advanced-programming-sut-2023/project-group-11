package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    END("end"),
    LOGOUT("logout"),
    SHOW_CURRENT_MENU("show\\s*current\\s*menu"),
    ENTER_MAP_EDIT_MENU("enter\\s*map\\s*edit\\s*menu"),
    ENTER_PROFILE_MENU("enter\\s*profile\\s*menu"),
    START_GAME("start\\s*game( -m (?<mapName>\"[^\"]+\"|\\S+)| -g (?<guests>((\"[^\"]+\"|\\S+) ?)+)){2}"),
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
