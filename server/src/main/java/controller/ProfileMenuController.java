package controller;

import model.Stronghold;
import model.User;
import model.map.Map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

    public static void changeUsername(ArrayList<Object> parameters) {
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

    public static Message addFriendRequest(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        User user = Stronghold.getUserByUsername(username);
        User currentUser = Stronghold.getCurrentUser();

        if (currentUser.equals(user)) return Message.SELF_FRIENDSHIP;
        if (currentUser.hasReachedFriendshipLimit()) return Message.YOU_REACHED_FRIEND_LIMIT;
        if (user.hasReachedFriendshipLimit()) return Message.HE_REACHED_FRIEND_LIMIT;
        if (user.hasRequest(currentUser.getUsername())) return Message.ALREADY_REQUESTED;
        if (user.isAlreadyFriend(currentUser)) return Message.ALREADY_FRIEND;

        user.addFriendRequest(Stronghold.getCurrentUser().getUsername());
        return Message.SUCCESS;
    }

    public static Message addFriend(ArrayList<Object> parameters) {
        String username = (String) parameters.get(0);
        User user = Stronghold.getUserByUsername(username);
        User currentUser = Stronghold.getCurrentUser();
        if (user.hasReachedFriendshipLimit()) return Message.HE_REACHED_FRIEND_LIMIT;
        if (currentUser.hasReachedFriendshipLimit()) return Message.YOU_REACHED_FRIEND_LIMIT;

        Stronghold.getCurrentUser().addFriend(user.getUsername());
        Stronghold.getCurrentUser().removeFriendRequest(user.getUsername());
        user.addFriend(currentUser.getUsername());
        user.removeFriendRequest(currentUser.getUsername());
        return Message.SUCCESS;
    }

    public static ArrayList<User> getCurrentUserFriendsRequest(ArrayList<Object> parameters) {
        ArrayList<User> users = new ArrayList<>();
        Stronghold.getCurrentUser().getFriendsRequest().forEach(username -> users.add(Stronghold.getUserByUsername(username)));
        return users;
    }

    public static ArrayList<User> getCurrentUserFriends(ArrayList<Object> parameters) {
        ArrayList<User> users = new ArrayList<>();
        Stronghold.getCurrentUser().getFriends().forEach(username -> users.add(Stronghold.getUserByUsername(username)));
        return users;
    }

    public static ArrayList<User> findUser(ArrayList<Object> parameters) {
        String toBeFound = (String) parameters.get(0);
        ArrayList<User> users = new ArrayList<>();
        Pattern pattern = Pattern.compile(toBeFound);

        for (User user : Stronghold.getUsers()) {
            String username = user.getUsername();
            if (pattern.matcher(username).find()) users.add(user);
        }
        return users;
    }
}
