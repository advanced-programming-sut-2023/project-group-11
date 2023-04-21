package controller;

import model.Stronghold;
import model.User;
import view.enums.messages.LoginMenuMessages;

import java.util.regex.Matcher;

public class LoginMenuController {
    public static LoginMenuMessages checkLogin(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        User user = Stronghold.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USERNAME_NOT_EXIST;
        if (!user.isPasswordCorrect(Utils.encryptField(password))) return LoginMenuMessages.INCORRECT_PASSWORD;
        if (matcher.group("stayLoggedIn") != null) user.setStayLoggedIn(true);
        return LoginMenuMessages.SUCCESS;
    }

    public static void loginUser(Matcher matcher) {
        Stronghold.setCurrentUser(Stronghold.getUserByUsername(matcher.group("username")));
    }

    public static LoginMenuMessages checkForgotPassword(Matcher matcher) {
        String username = matcher.group("username");
        User user = Stronghold.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USERNAME_NOT_EXIST;
        return LoginMenuMessages.SUCCESS;
    }

    public static LoginMenuMessages checkRecoveryAnswer(Matcher matcher, String recoveryAnswer) {
        String username = matcher.group("username");
        User user = Stronghold.getUserByUsername(username);
        if (!user.isRecoveryAnswerCorrect(Utils.encryptField(recoveryAnswer)))
            return LoginMenuMessages.WRONG_RECOVERY_ANSWER;
        return LoginMenuMessages.SUCCESS;
    }

    public static String showRecoveryQuestion(Matcher matcher) {
        String username = matcher.group("username");
        User user = Stronghold.getUserByUsername(username);
        return user.getRecoveryQuestion();
    }

    public static void setNewPassword(Matcher matcher, String password) {
        String username = matcher.group("username");
        User user = Stronghold.getUserByUsername(username);
        user.setPassword(Utils.encryptField(password));
        Utils.updateDatabase("users");
    }

    public static String logout() {
        Stronghold.getCurrentUser().setStayLoggedIn(false);
        Stronghold.setCurrentUser(null);
        return "user logged out successfully!";
    }
}
