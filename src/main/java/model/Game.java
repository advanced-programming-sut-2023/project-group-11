package model;

import model.map.Map;

import java.util.ArrayList;

public class Game {
    private static User owner = Stronghold.getCurrentUser();
    private static ArrayList<Governance> governances;
    private static Map map;
    private static int turn = 1;

    public Game(ArrayList<Governance> governances, Map map) {
        this.governances = governances;
        this.map = map;
    }

    public static User getOwner() {
        return owner;
    }

    public static ArrayList<Governance> getGovernances() {
        return governances;
    }

    public static Map getMap() {
        return map;
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        turn = turn;
    }
}
