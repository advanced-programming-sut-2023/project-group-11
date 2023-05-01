package model;

import model.map.Map;

import java.util.ArrayList;

public class Game {
    private final User owner;
    private final ArrayList<Governance> governances;
    private final ArrayList<Trade> trades = new ArrayList<>();
    private Governance currentGovernance;
    private final Map map;
    private int turn;

    public Game(ArrayList<Governance> governances, Map map) {
        this.owner = Stronghold.getCurrentUser();
        this.governances = governances;
        this.currentGovernance = governances.get(0);
        this.map = map;
        this.turn = 1;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
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

    public void plusTurnCounter() {
        this.turn =+ 1;
    }

    public Governance getCurrentGovernance() {
        return currentGovernance;
    }

    public void setCurrentGovernance(Governance currentGovernance) {
        this.currentGovernance = currentGovernance;
    }
}
