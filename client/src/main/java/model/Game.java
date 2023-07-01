package model;

import model.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private User owner;
    private  ArrayList<Governance> governances;
    private ArrayList<User> joinedUsers = new ArrayList<>();
    private int id;
    private int playersNeeded;
    private final HashMap<Governance, Integer> scores = new HashMap<>();
    private final ArrayList<Trade> trades = new ArrayList<>();
    private Governance currentGovernance;
    private transient final Map map;
    private int turn = 1;
    private int currentTurn = 1;

    public Game(ArrayList<Governance> governances, Map map) {
        this.governances = governances;
        this.currentGovernance = governances.get(0);
        this.map = map;
    }

    public Game(User owner,Map map,int playersNeeded) {
        this.owner = owner;
        this.map = map;
        this.playersNeeded = playersNeeded;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public User getOwner() {
        return owner;
    }

    public String getOwnerName() {
        return owner.getNickname();
    }

    public ArrayList<Governance> getGovernances() {
        return governances;
    }

    public Map getMap() {
        return map;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }
    public int getTurn() {
        return turn;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getJoinedUsers() {
        return joinedUsers;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void plusTurnCounter(int number) {
        this.turn += number;
    }

    public void plusCurrentTurnCounter() {
        this.currentTurn += 1;
    }

    public HashMap<Governance, Integer> getScores() {
        return scores;
    }

    public void addLoserScore(Governance governance, int score) {
        scores.put(governance, score);
    }

    public String getJoinedPlayers(){
        return joinedUsers.size() +  "/" + playersNeeded;
    }

    public Governance getCurrentGovernance() {
        return currentGovernance;
    }

    public void setCurrentGovernance(Governance currentGovernance) {
        this.currentGovernance = currentGovernance;
    }
}
