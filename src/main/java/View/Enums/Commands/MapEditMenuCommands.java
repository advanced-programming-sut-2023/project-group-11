package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapEditMenuCommands {
    COMMAND(""),
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
