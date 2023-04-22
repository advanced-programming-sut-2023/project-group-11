package controller;

import model.Delay;
import model.Stronghold;
import model.User;
import view.enums.messages.LoginMenuMessages;

import java.util.regex.Matcher;

public class LoginMenuController {
    public static LoginMenuMessages checkLogin(Matcher matcher) {
        User user = getUserByUsername(matcher);
        String password = matcher.group("password");
        if (user == null) return LoginMenuMessages.USERNAME_NOT_EXIST;
        if (!user.isPasswordCorrect(Utils.encryptField(password))) {
            int currentDelay = Delay.getDelay(user);
            currentDelay += currentDelay == 0 ? 5 : currentDelay;
            Delay.addDelayedUser(user, new Delay(System.currentTimeMillis()));
            return LoginMenuMessages.INCORRECT_PASSWORD;
        }
        if (matcher.group("stayLoggedIn") != null) user.setStayLoggedIn(true);
        return LoginMenuMessages.SUCCESS;
    }

    public static void loginUser(Matcher matcher) {
        User user = getUserByUsername(matcher);
        Stronghold.setCurrentUser(user);
    }

    public static LoginMenuMessages checkForgotPassword(Matcher matcher) {
        User user = getUserByUsername(matcher);
        if (user == null) return LoginMenuMessages.USERNAME_NOT_EXIST;
        return LoginMenuMessages.SUCCESS;
    }

    public static LoginMenuMessages checkRecoveryAnswer(Matcher matcher, String recoveryAnswer) {
        User user = getUserByUsername(matcher);
        if (!user.isRecoveryAnswerCorrect(Utils.encryptField(recoveryAnswer)))
            return LoginMenuMessages.WRONG_RECOVERY_ANSWER;
        return LoginMenuMessages.SUCCESS;
    }

    public static String showRecoveryQuestion(Matcher matcher) {
        User user = getUserByUsername(matcher);
        return user.getRecoveryQuestion();
    }

    public static void setNewPassword(Matcher matcher, String password) {
        User user = getUserByUsername(matcher);
        user.setPassword(Utils.encryptField(password));
        Utils.updateDatabase("users");
    }

    public static String logout() {
        Stronghold.getCurrentUser().setStayLoggedIn(false);
        Stronghold.setCurrentUser(null);
        return "user logged out successfully!";
    }

    public static User getUserByUsername(Matcher matcher) {
        String username = matcher.group("username");
        return Stronghold.getUserByUsername(username);
    }
}
