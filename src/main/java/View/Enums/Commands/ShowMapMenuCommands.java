package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShowMapMenuCommands {
    COMMAND(""),
    ;

    private final String regex;

    ShowMapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ShowMapMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
