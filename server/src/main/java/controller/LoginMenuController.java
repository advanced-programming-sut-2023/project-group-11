package controller;

import model.Delay;
import model.Stronghold;
import model.User;

import java.util.ArrayList;

public class LoginMenuController {
    public static Message checkLogin(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        String password = (String) parameters.get(1);
        boolean stayLoggedIn = (boolean) parameters.get(2);

        if (username.isEmpty()) return Message.EMPTY_USERNAME_FIELD;
        else if (password.isEmpty()) return Message.EMPTY_PASSWORD_FIELD;

        User user = Stronghold.getUserByUsername(username);

        if (user == null) return Message.USERNAME_NOT_EXIST;
        else if (Stronghold.getConnectionByUser(user) != null) return Message.USER_ALREADY_LOGGED_IN;
        else if (!user.isPasswordCorrect(Utils.encryptField(password))) {
            long currentTime = System.currentTimeMillis();
            if (!Delay.hasUser(user))
                Delay.addDelayedUser(user, new Delay(currentTime));
            Delay delay = Delay.getDelayByUser(user);
            delay.setDelayTime(delay.getDelayTime() == 0 ? 5000 : 2 * delay.getDelayTime());
            delay.setLastLoginCommandTime(currentTime);
            return Message.INCORRECT_PASSWORD;
        } else if (Delay.getDelayByUser(user) != null) {
            long currentTime = System.currentTimeMillis();
            Delay delay = Delay.getDelayByUser(user);
            if (delay.getDelayTime() > currentTime - delay.getLastLoginCommandTime())
                return Message.LOCKED_ACCOUNT;
        }

        if (stayLoggedIn) user.setStayLoggedIn(true);

        return Message.SUCCESS;
    }

    public static void loginUser(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        User user = Stronghold.getUserByUsername(username);
        Stronghold.setCurrentUser(user);
    }

    public static Message checkForgotPassword(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        User user = Stronghold.getUserByUsername(username);
        if (user == null) return Message.USERNAME_NOT_EXIST;
        return Message.SUCCESS;
    }

    public static Message checkRecoveryAnswer(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        String recoveryAnswer = (String) parameters.get(1);

        if (recoveryAnswer.isEmpty()) return Message.EMPTY_RECOVERY_ANSWER_FIELD;
        User user = Stronghold.getUserByUsername(username);
        if (!user.isRecoveryAnswerCorrect(Utils.encryptField(recoveryAnswer)))
            return Message.WRONG_RECOVERY_ANSWER;
        return Message.SUCCESS;
    }

    public static String showRecoveryQuestion(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        User user = Stronghold.getUserByUsername(username);
        return user.getRecoveryQuestion();
    }

    public static long getLeftLockedTime(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        User user = Stronghold.getUserByUsername(username);
        Delay delay = Delay.getDelayByUser(user);
        return (delay.getDelayTime() - System.currentTimeMillis() + delay.getLastLoginCommandTime());
    }

    public static Message checkNewPassword(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        String password = (String) parameters.get(1);

        if (password.isEmpty()) return Message.EMPTY_PASSWORD_FIELD;
        else if (!Utils.isStrongPassword(password)) return Message.WEAK_PASSWORD;

        setNewPassword(username, password);
        return Message.SUCCESS;
    }

    private static void setNewPassword(String username, String password) {
        User user = Stronghold.getUserByUsername(username);
        user.setPassword(Utils.encryptField(password));
    }
}
