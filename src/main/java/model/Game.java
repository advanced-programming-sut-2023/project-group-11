package model;

import model.map.Map;

import java.util.ArrayList;

public class Game {
    private final User owner;
    private final ArrayList<Governance> governances;
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

    public Governance getCurrentGovernance() {
        return currentGovernance;
    }

    public void setCurrentGovernance(Governance currentGovernance) {
        this.currentGovernance = currentGovernance;
    }
}
