package controller;

import model.Stronghold;
import view.enums.messages.ProfileMenuMessages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ProfileMenuController {
    public static ProfileMenuMessages checkChangeUsername(String username) {
        if (!Utils.isValidUsernameFormat(username))
            return ProfileMenuMessages.INVALID_USERNAME;
        else if (Stronghold.usernameExist(username))
            return ProfileMenuMessages.USERNAME_EXIST;

//        changeUsername(Stronghold.getCurrentUser(), username);
        return ProfileMenuMessages.SUCCESS;
    }

    public static void changeUsername(String username) {
        Stronghold.getCurrentUser().setUsername(username);
    }

    public static void changeAvatar(File avatar) {
        File databaseFile = new File("src/main/resources/IMG/avatars/" + avatar.getName());
        try {
            if (!databaseFile.exists()) Files.copy(avatar.toPath(), databaseFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stronghold.getCurrentUser().setAvatarFileName(avatar.getName());
    }

    public static void changeNickname(String nickname) {
        Stronghold.getCurrentUser().setNickname(nickname);
    }

    public static ProfileMenuMessages checkChangeEmail(String email) {
        if (!Utils.isValidEmailFormat(email))
            return ProfileMenuMessages.INVALID_EMAIL;
        else if (Stronghold.emailExist(email))
            return ProfileMenuMessages.EMAIL_EXIST;

        return ProfileMenuMessages.SUCCESS;
    }

    public static void changeEmail(String email) {
        Stronghold.getCurrentUser().setEmail(email);
    }

    public static void changeSlogan(String slogan) {
        Stronghold.getCurrentUser().setSlogan(slogan);
    }

    public static ProfileMenuMessages checkChangePassword(String oldPassword, String newPassword) {
        if (!Stronghold.getCurrentUser().isPasswordCorrect(Utils.encryptField(oldPassword)))
            return ProfileMenuMessages.INCORRECT_PASSWORD;
        else if (oldPassword.equals(newPassword))
            return ProfileMenuMessages.SAME_PASSWORD;

        changePassword(newPassword);
        return ProfileMenuMessages.SUCCESS;
    }

    private static void changePassword(String newPassword) {
        Stronghold.getCurrentUser().setPassword(Utils.encryptField(newPassword));
    }

    public static void removeSlogan() {
        Stronghold.getCurrentUser().setSlogan(null);
    }
}
