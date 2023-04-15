package Controller;

import View.Enums.Messages.SignupMenuMessages;

import java.util.SortedMap;
import java.util.regex.Matcher;

public class SignupMenuController {
    public static SignupMenuMessages checkRegister(Matcher registerMatcher) {
        String username;
        String password;
        String passwordConfirmation;
        String email;
        String nickname;
        String slogan;
        return null;
    }

    public static SignupMenuMessages checkPickQuestion(Matcher pickQuestionMatcher, Matcher registerMatcher, String randomSlogan, String randomPassword) {
        return null;
    }

    public static String getRecoveryQuestions() {
        String output = "";
        return output;
    }

    public static String pickRandomSlogan() {
        return null;
    }

    public static String generateRandomPassword() {
        return null;
    }
}
