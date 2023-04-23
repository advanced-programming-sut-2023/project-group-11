package controller;

import model.Stronghold;
import view.enums.messages.ProfileMenuMessages;
import view.enums.messages.SignupMenuMessages;

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
        String slogan = Utils.removeDoubleQuotation("field");
        Stronghold.getCurrentUser().setSlogan(slogan);
    }

    public static ProfileMenuMessages checkChangePassword(Matcher matcher) {
        String newPassword = matcher.group("new");
        String oldPassword = matcher.group("old");

        if (oldPassword == null || newPassword == null)
            return ProfileMenuMessages.INVALID_COMMAND;
        else if (!Stronghold.getCurrentUser().isPasswordCorrect(Utils.encryptField(oldPassword)))
            return ProfileMenuMessages.INCORRECT_PASSWORD;
        else if (oldPassword.equals(newPassword))
            return ProfileMenuMessages.SAME_PASSWORD;
        else if (findWeakPartOfPassword(newPassword) != null)
            return findWeakPartOfPassword(newPassword);

        Stronghold.getCurrentUser().setPassword(Utils.encryptField(newPassword));
        return ProfileMenuMessages.SUCCESS;
    }

    public static ProfileMenuMessages checkRemoveSlogan() {
        return null;
    }

    public static String displayProfile(Matcher matcher) {
        return null;
    }

    public static ProfileMenuMessages findWeakPartOfPassword(String password) {
        if (password.length() < 6) return ProfileMenuMessages.WEAK_PASSWORD_SHORT_PASSWORD;
        if (!password.matches("(?=.*[A-Z]).*")) return ProfileMenuMessages.WEAK_PASSWORD_NO_UPPERCASE;
        if (!password.matches("(?=.*[a-z]).*")) return ProfileMenuMessages.WEAK_PASSWORD_NO_LOWERCASE;
        if (!password.matches("(?=.*[0-9]).*")) return ProfileMenuMessages.WEAK_PASSWORD_NO_NUMBER;
        if (!password.matches("(?=.*[^A-Za-z0-9]).*")) return ProfileMenuMessages.WEAK_PASSWORD_NO_SPECIAL;
        return null;
    }
}
