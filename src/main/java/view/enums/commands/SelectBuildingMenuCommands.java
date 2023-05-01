package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SelectBuildingMenuCommands {
    BACK("back"),
    CREATE_UNIT("createunit((?<typeGroup> -t (?<type>\"[^\"]+\"|\\S+))" +
            "|(?<countGroup> -c (?<count>\\d+))){2}"),
    REPAIR("repair")
    ;

    private final String regex;

    SelectBuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, SelectBuildingMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
