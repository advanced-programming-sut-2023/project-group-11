package controller;

import model.Stronghold;
import model.User;
import view.enums.messages.ProfileMenuMessages;

import java.util.regex.Matcher;

public class ProfileMenuController {
    public static ProfileMenuMessages checkChangeUsername(Matcher matcher) {
        String username = Utils.removeDoubleQuotation(matcher.group("field"));

        if (!Utils.isValidUsernameFormat(username))
            return ProfileMenuMessages.INVALID_USERNAME;
        else if (Stronghold.usernameExist(username))
            return ProfileMenuMessages.USERNAME_EXIST;

        Stronghold.getCurrentUser().setUsername(username);
        return ProfileMenuMessages.SUCCESS;
    }

    public static void changeNickname(Matcher matcher) {
        String nickname = Utils.removeDoubleQuotation(matcher.group("field"));
        Stronghold.getCurrentUser().setNickname(nickname);
    }

    public static ProfileMenuMessages checkChangeEmail(Matcher matcher) {
        String email = Utils.removeDoubleQuotation(matcher.group("field"));

        if (!Utils.isValidEmailFormat(email))
            return ProfileMenuMessages.INVALID_EMAIL;
        else if (Stronghold.emailExist(email))
            return ProfileMenuMessages.EMAIL_EXIST;

        Stronghold.getCurrentUser().setEmail(email);
        return ProfileMenuMessages.SUCCESS;
    }

    public static void changeSlogan(Matcher matcher) {
        String slogan = Utils.removeDoubleQuotation(matcher.group("field"));
        Stronghold.getCurrentUser().setSlogan(slogan);
    }

    public static ProfileMenuMessages checkChangePassword(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "new", "old"))
            return ProfileMenuMessages.INVALID_COMMAND;

        String newPassword = matcher.group("new");
        String oldPassword = matcher.group("old");

        if (!Stronghold.getCurrentUser().isPasswordCorrect(Utils.encryptField(oldPassword)))
            return ProfileMenuMessages.INCORRECT_PASSWORD;
        else if (oldPassword.equals(newPassword))
            return ProfileMenuMessages.SAME_PASSWORD;
        else if (findWeakPartOfPassword(newPassword) != null)
            return findWeakPartOfPassword(newPassword);

        Stronghold.getCurrentUser().setPassword(Utils.encryptField(newPassword));
        return ProfileMenuMessages.SUCCESS;
    }

    public static void removeSlogan() {
        Stronghold.getCurrentUser().setSlogan(null);
    }

    public static ProfileMenuMessages checkDisplayProfile(Matcher matcher) {
        String field = matcher.group("field");

        if (!(field == null || field.equals("highscore") || field.equals("slogan") || field.equals("rank")))
            return ProfileMenuMessages.INVALID_FIELD_TO_DISPLAY;
        else if (field != null && field.equals("slogan") && Stronghold.getCurrentUser().getSlogan() == null)
            return ProfileMenuMessages.EMPTY_SLOGAN;

        return ProfileMenuMessages.SUCCESS;
    }

    public static String displayProfile(String field) {
        String result = "";
        User user = Stronghold.getCurrentUser();

        if (field == null) {
            result += "** UserName: " + user.getUsername() + " **\n";
            result += "HighScore = " + user.getHighScore() + '\n';
            result += "Rank = " + Stronghold.getRankByUsername(user.getUsername());
            if (user.getSlogan() != null)
                result += "\nSlogan = " + user.getSlogan();
        } else {
            switch (field) {
                case "highscore" -> result = "HighScore = " + user.getHighScore();
                case "rank" -> result = "Rank = " + Stronghold.getRankByUsername(user.getUsername());
                case "slogan" -> result = "Slogan = " + user.getSlogan();
            }
        }
        return result;
    }

    public static ProfileMenuMessages findWeakPartOfPassword(String password) {
        if (password.length() < 6) return ProfileMenuMessages.SHORT_PASSWORD;
        if (!password.matches("(?=.*[A-Z]).*")) return ProfileMenuMessages.NO_UPPERCASE;
        if (!password.matches("(?=.*[a-z]).*")) return ProfileMenuMessages.NO_LOWERCASE;
        if (!password.matches("(?=.*[0-9]).*")) return ProfileMenuMessages.NO_NUMBER;
        if (!password.matches("(?=.*[^A-Za-z0-9]).*")) return ProfileMenuMessages.NO_SPECIAL;
        return null;
    }
}
