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
        else if (!user.isPasswordCorrect(Utils.encryptField(password))) {
            long currentTime = System.currentTimeMillis();
            if (!Delay.hasUser(user))
                Delay.addDelayedUser(user, new Delay(currentTime));
            Delay delay = Delay.getDelayByUser(user);
            delay.setDelayTime(delay.getDelayTime() == 0 ? 5000 : 2 * delay.getDelayTime());
            delay.setLastLoginCommandTime(currentTime);
            return LoginMenuMessages.INCORRECT_PASSWORD;
        } else if (Delay.getDelayByUser(user) != null) {
            long currentTime = System.currentTimeMillis();
            Delay delay = Delay.getDelayByUser(user);
            if (delay.getDelayTime() > currentTime - delay.getLastLoginCommandTime())
                return LoginMenuMessages.LOCKED_ACCOUNT;
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
    }

    public static User getUserByUsername(Matcher matcher) {
        String username = matcher.group("username");
        return Stronghold.getUserByUsername(username);
    }

    public static long getLeftLockedTime(Matcher matcher) {
        User user = getUserByUsername(matcher);
        Delay delay = Delay.getDelayByUser(user);
        return (delay.getDelayTime() - System.currentTimeMillis() + delay.getLastLoginCommandTime());
    }

}
