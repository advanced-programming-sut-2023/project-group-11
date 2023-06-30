package controller;

import model.Stronghold;
import model.map.Map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ProfileMenuController {
    public static Message checkChangeUsername(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);

        if (!Utils.isValidUsernameFormat(username))
            return Message.INVALID_USERNAME;
        else if (Stronghold.usernameExist(username))
            return Message.USERNAME_EXIST;

//        changeUsername(Stronghold.getCurrentUser(), username);
        return Message.SUCCESS;
    }

    public static void changeUsername(ArrayList<Object> parameters ) {
        String newUsername = (String) parameters.get(0);
        String oldUsername = Stronghold.getCurrentUser().getUsername();

        for (Map map : Stronghold.getMaps())
            if (map.getOwners().contains(oldUsername))
                map.getOwners().set(map.getOwners().indexOf(oldUsername), newUsername);
        Stronghold.getCurrentUser().setUsername(newUsername);
        Utils.updateDatabase("maps");
    }

    public static void changeAvatar(ArrayList<Object> parameters) {
        File avatar = new File((String) parameters.get(0));
        File databaseFile = new File("src/main/resources/IMG/avatars/" + avatar.getName());
        try {
            if (!databaseFile.exists()) Files.copy(avatar.toPath(), databaseFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stronghold.getCurrentUser().setAvatarFileName(avatar.getName());
    }

    public static void changeNickname(ArrayList<Object> parameters) {
        String nickname = (String) parameters.get(0);
        Stronghold.getCurrentUser().setNickname(nickname);
    }

    public static Message checkChangeEmail(ArrayList<Object> parameters) {
        String email = (String) parameters.get(0);
        if (!Utils.isValidEmailFormat(email))
            return Message.INVALID_EMAIL;
        else if (Stronghold.emailExist(email))
            return Message.EMAIL_EXIST;

        return Message.SUCCESS;
    }

    public static void changeEmail(ArrayList<Object> parameters) {
        String email = (String) parameters.get(0);
        Stronghold.getCurrentUser().setEmail(email);
    }

    public static void changeSlogan(ArrayList<Object> parameters) {
        String slogan = (String) parameters.get(0);
        Stronghold.getCurrentUser().setSlogan(slogan);
    }

    public static Message checkChangePassword(ArrayList<Object> parameters) {
        String oldPassword = (String) parameters.get(0);
        String newPassword = (String) parameters.get(1);

        if (!Stronghold.getCurrentUser().isPasswordCorrect(Utils.encryptField(oldPassword)))
            return Message.INCORRECT_PASSWORD;
        else if (oldPassword.equals(newPassword))
            return Message.SAME_PASSWORD;

        changePassword(newPassword);
        return Message.SUCCESS;
    }

    private static void changePassword(String newPassword) {
        Stronghold.getCurrentUser().setPassword(Utils.encryptField(newPassword));
    }

    public static void removeSlogan(ArrayList<Object> parameters) {
        Stronghold.getCurrentUser().setSlogan(null);
    }
}
