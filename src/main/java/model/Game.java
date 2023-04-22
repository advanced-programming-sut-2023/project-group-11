package model;

import model.map.Map;

import java.util.ArrayList;

public class Game {
    private User owner = Stronghold.getCurrentUser();
    private ArrayList<Governance> governances;
    private Map map;
    private int turn = 1;

    public Game(ArrayList<Governance> governances, Map map) {
        this.governances = governances;
        this.map = map;
    }

    public User getOwner() {
        return owner;
    }

    public ArrayList<Governance> getGovernances() {
        return governances;
    }

    public Map getMap() {
        return map;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
