package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands {
    REGISTER("user create -u (?<username>\\S*) -p (?<password>\\S*) (?<passwordConfirmation>\\S*)" +
            " –email (?<email>\\S*) -n (?<nickname>\\S*)( -s (?<slogan>\\S*))?"), //TODO: how to handle quotation
    PICK_QUESTION(""),

    ;

    private final String regex;

    SignupMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, SignupMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
