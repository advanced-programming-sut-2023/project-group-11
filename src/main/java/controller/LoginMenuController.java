package controller;

import model.Delay;
import model.Stronghold;
import model.User;
import view.enums.messages.LoginMenuMessages;

import java.util.regex.Matcher;

public class LoginMenuController {
    public static LoginMenuMessages checkLogin(String username, String password, boolean stayLoggedIn) {
        if (username.isEmpty()) return LoginMenuMessages.EMPTY_USERNAME_FIELD;
        else if (password.isEmpty()) return LoginMenuMessages.EMPTY_PASSWORD_FIELD;

        User user = Stronghold.getUserByUsername(username);

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

        if (stayLoggedIn) user.setStayLoggedIn(true);

        return LoginMenuMessages.SUCCESS;
    }

    public static void loginUser(String username) {
        User user = Stronghold.getUserByUsername(username);
        Stronghold.setCurrentUser(user);
    }

    public static LoginMenuMessages checkForgotPassword(String username) {
        User user = Stronghold.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USERNAME_NOT_EXIST;
        return LoginMenuMessages.SUCCESS;
    }

    public static LoginMenuMessages checkRecoveryAnswer(String username, String recoveryAnswer) {
        if (recoveryAnswer.isEmpty()) return LoginMenuMessages.EMPTY_RECOVERY_ANSWER_FIELD;
        User user = Stronghold.getUserByUsername(username);
        if (!user.isRecoveryAnswerCorrect(Utils.encryptField(recoveryAnswer)))
            return LoginMenuMessages.WRONG_RECOVERY_ANSWER;
        return LoginMenuMessages.SUCCESS;
    }

    public static String showRecoveryQuestion(String username) {
        User user = Stronghold.getUserByUsername(username);
        return user.getRecoveryQuestion();
    }

    public static long getLeftLockedTime(String username) {
        User user = Stronghold.getUserByUsername(username);
        Delay delay = Delay.getDelayByUser(user);
        return (delay.getDelayTime() - System.currentTimeMillis() + delay.getLastLoginCommandTime());
    }

    public static LoginMenuMessages checkNewPassword(String username, String password) {
        if (password.isEmpty()) return LoginMenuMessages.EMPTY_PASSWORD_FIELD;
        else if (!Utils.isStrongPassword(password)) return LoginMenuMessages.WEAK_PASSWORD;

        setNewPassword(username, password);
        return LoginMenuMessages.SUCCESS;
    }

    private static void setNewPassword(String username, String password) {
        User user = Stronghold.getUserByUsername(username);
        user.setPassword(Utils.encryptField(password));
    }
}
