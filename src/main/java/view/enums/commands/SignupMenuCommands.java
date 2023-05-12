package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands {
    END("end"),
    BACK("back"),
    SHOW_CURRENT_MENU("show current menu"),
    REGISTER("user\\s*create(?:( (?<usernameTag>-u) (?<username>\"[^\"]+\"|\\S+))" +
            "|( (?<passwordTag>-p) (?<password>random|\\S* \\S*))" +
            "|( (?<emailTag>-e) (?<email>\\S*))" +
            "|( (?<nicknameTag>-n) (?<nickname>\"[^\"]+\"|\\S+))" +
            "|( (?<sloganTag>-s) (?<slogan>\"[^\"]+\"|\\S+))?){4,5}"),
    PICK_QUESTION("question\\s*pick" +
            "( -q (?<questionNumber>\\d+)" +
            "|( -a (?<answer>\"[^\"]+\"|\\S+))" +
            "|( -c (?<answerConfirmation>\"[^\"]+\"|\\S+))){3}"),
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
