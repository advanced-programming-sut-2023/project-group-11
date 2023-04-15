package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MarketMenuCommands {
    COMMAND(""),
    ;

    private final String regex;

    MarketMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MarketMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
