package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SelectBuildingMenuCommands {
    BACK("back"),
    SHOW_CURRENT_MENU("show current menu"),
    CREATE_UNIT("create\\s*unit(( -t (?<type>\"[^\"]+\"|\\S+))" +
            "|( -c (?<count>\\d+))){2}"),
    REPAIR("repair");

    private final String regex;

    SelectBuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, SelectBuildingMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
