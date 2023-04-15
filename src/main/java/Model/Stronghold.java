package Model;

import Model.map.Map;

import java.util.ArrayList;

public class Stronghold {
    private final static ArrayList<User> users = new ArrayList<>();
    private final static ArrayList<Trade> trades = new ArrayList<>();
    private final static ArrayList<Map> maps = new ArrayList<>();
    private static User currentUser;
    private static Governance currentGovernance;
    private static boolean stayLoggedIn;

    public static boolean isRepeatedEmail(String email) {
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

    public static String printTrades() {
        return null;
    }
}
