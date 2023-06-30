package model;

import model.chat.Chat;
import model.map.Map;
import webConnetion.Connection;

import java.util.ArrayList;

public class Stronghold {
    private final static ArrayList<String> randomSlogans = new ArrayList<>();
    private final static ArrayList<User> users = new ArrayList<>();
    private final static ArrayList<Chat> chats = new ArrayList<>();
    private static ArrayList<Map> maps = new ArrayList<>();
    private final static ArrayList<Trade> trades = new ArrayList<>();
    private final static ArrayList<String> recoveryQuestions = new ArrayList<>();

    private static ArrayList<Connection> connections = new ArrayList<>();
    private final static ArrayList<Game> unStartedGames = new ArrayList<>();
    private static User currentUser;
    private static Game currentGame;

    static {
        recoveryQuestions.add("What is my father's name?");
        recoveryQuestions.add("What was my first pet's name?");
        recoveryQuestions.add("What is my mother's last name?");
        recoveryQuestions.add("What is my best friend's name?");
        randomSlogans.add("I shall have my revenge, in this life or the next");
        randomSlogans.add("You are clearly no match for my forces! Give in now, my friend!");
        randomSlogans.add("A soldier's death... is a noble death.");
        randomSlogans.add("I am, indeed, a king, because I know how to rule myself.");
        randomSlogans.add("A king without power is an absurdity.");
        randomSlogans.add("In the kingdom of the blind, the one-eyed man is king");
    }

    public static boolean emailExist(String email) {
        for (User user : users)
            if (user.getEmail().equalsIgnoreCase(email)) return true;
        return false;
    }

    public static User getUserByUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username)) return user;
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Stronghold.currentUser = currentUser;
    }

    public static ArrayList<Game> getUnStartedGames() {
        return unStartedGames;
    }

    public static int getRankByUsername(String name) {
        users.sort(User::compareTo);
        return users.indexOf(getUserByUsername(name)) + 1;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static void setMaps(ArrayList<Map> maps) {
        Stronghold.maps = maps;
    }

    public static void addMap(Map map) {
        maps.add(map);
    }

    public static void addTrade(Trade trade) {
        trades.add(trade);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<String> getRecoveryQuestions() {
        return recoveryQuestions;
    }

    public static ArrayList<String> getRandomSlogans() {
        return randomSlogans;
    }


    public static String printRecoveryQuestions() {
        String output = "";
        int i = 1;
        for (String recoveryQuestion : recoveryQuestions)
            output += (i++) + "- " + recoveryQuestion + "\n";
        return output;
    }

    public static Map getMapByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return map;
        return null;
    }

    public static String printRandomSlogans() {
        String output = "";
        int i = 1;
        for (String randomSlogan : randomSlogans)
            output += (i++) + "- " + randomSlogan + "\n";
        return output;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Stronghold.currentGame = currentGame;
    }

    public static boolean usernameExist(String username) {
        return getUserByUsername(username) != null;
    }

    public static boolean isMapName(String name) {
        for (Map map : getMaps())
            if (map.getName().equals(name)) return true;
        return false;
    }

    public static ArrayList<Connection> getConnections() {
        return connections;
    }

    public static void addConnection(Connection connection) {
        connections.add(connection);
    }

    public static void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    public static Connection getConnectionByUser(User user) {
        for (Connection connection : connections)
            if (user.equals(connection.getCurrentUser())) return connection;
        return null;
    }

    public static void addChat(Chat chat) {
        chats.add(chat);
    }

    public static void removeChat(Chat chat) {
        chats.remove(chat);
    }

    public static Chat getChatById(String id) {
        for (Chat chat : chats)
            if (chat.getId().equals(id)) return chat;
        return null;
    }
}