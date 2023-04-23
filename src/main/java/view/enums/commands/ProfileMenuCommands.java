package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    BACK("back"),
    END("end"),
    SHOW_CURRENT_MENU("show current menu"),
    PROFILE_CHANGE("profile change -(?<tag>\\w) (?<field>\"[^\"]+\"|\\S+)"),
    CHANGE_PASSWORD("profile change password( -o (?<old>\\S+)| -n (?<new>\\S+)){2}"),
    REMOVE_SLOGAN("profile remove slogan"),
    PROFILE_DISPLAY("profile display( (?<field>\\S+))?"),
    ;

    private final String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ProfileMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
