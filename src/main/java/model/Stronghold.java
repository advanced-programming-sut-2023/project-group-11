package model;

import model.map.Map;

import java.util.ArrayList;

public class Stronghold {
    private final static ArrayList<User> users = new ArrayList<>();
    private final static ArrayList<Trade> trades = new ArrayList<>();
    private final static ArrayList<Map> maps = new ArrayList<>();
    private final static ArrayList<String> emails = new ArrayList<>();
    private final static ArrayList<String> recoveryQuestions = new ArrayList<>();
    private static User currentUser;
    private static Governance currentGovernance;
    private static boolean stayLoggedIn;

    public static boolean emailExist(String currentEmail) {
        for (String email : emails)
            if (email.equals(currentEmail)) return true;
        return false;
    }

    public static User getUserByUsername(String username) {
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Stronghold.currentUser = currentUser;
    }

    private static void sortUsersRank() {

    }

    public static int getRankByUsername(String name) {
        sortUsersRank();
        return 0;
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean stayLoggedIn) {
        Stronghold.stayLoggedIn = stayLoggedIn;
    }

    public static Governance getCurrentGovernance() {
        return currentGovernance;
    }

    public static void setCurrentGovernance(Governance currentGovernance) {
        Stronghold.currentGovernance = currentGovernance;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static void addTrade(Trade trade) {
        trades.add(trade);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void addEmail(String email) {
        emails.add(email);
    }

    private static void addRecoveryQuestions() {
        recoveryQuestions.add("What is my father’s name?");
        recoveryQuestions.add("What was my first pet’s name?");
        recoveryQuestions.add("What is my mother’s last name?");
        recoveryQuestions.add("What is my best friend's name");
    }

    public static ArrayList<String> getRecoveryQuestions() {
        return recoveryQuestions;
    }

    public static String printTrades() {
        return null;
    }

    public static String printRecoveryQuestions() {
        StringBuilder output = new StringBuilder();
        int i = 1;
        for (String recoveryQuestion : recoveryQuestions)
            output.append(i++).append(recoveryQuestion).append("\n");
        return output.toString();
    }

    public static Map getMapByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                    return map;
        return null;
    }
}
