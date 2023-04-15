package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EntryMenuCommands {
    COMMAND(""),
    ;

    private final String regex;

    EntryMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, EntryMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
