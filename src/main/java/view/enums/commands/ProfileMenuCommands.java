package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    BACK("back"),
    END("end"),
    SHOW_CURRENT_MENU("show current menu"),
    PROFILE_CHANGE("profile\\s*change -(?<tag>\\w) (?<field>\"[^\"]+\"|\\S+)"),
    CHANGE_PASSWORD("profile\\s*change\\s*password( -o (?<old>\\S+)| -n (?<new>\\S+)){2}"),
    REMOVE_SLOGAN("profile\\s*remove\\s*slogan"),
    PROFILE_DISPLAY("profile\\s*display( (?<field>\\S+))?"),
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
