package model;

import model.map.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {
    private User owner;
    private  ArrayList<Governance> governances;

    private ArrayList<User> joinedUsers = new ArrayList<>();

    private boolean isPrivate = false;

    private boolean isStarted = false;

    private int playersNeeded;
    private final HashMap<Governance, Integer> scores = new HashMap<>();
    private final ArrayList<Trade> trades = new ArrayList<>();
    private Governance currentGovernance;
    private transient final Map map;
    private int id;
    private int turn = 1;
    private int currentTurn = 1;

    public Game(ArrayList<Governance> governances, Map map) {
        this.owner = Stronghold.getCurrentUser();
        this.governances =  governances;
        this.currentGovernance = governances.get(0);
        this.map = map;
    }

    public Game(User owner,Map map,int playersNeeded) {
        this.owner = owner;
        joinedUsers.add(owner);
        this.map = map;
        this.playersNeeded = playersNeeded;
        this.id = new Random().nextInt(1000,10000);
        while (Stronghold.getGameById(id) != null)
            this.id = new Random().nextInt(1000,10000);
        Stronghold.getGames().add(this);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner){
        this.owner = owner;
    }

    public ArrayList<Governance> getGovernances() {
        return governances;
    }

    public Map getMap() {
        return map;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getJoinedUsers() {
        return joinedUsers;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }
    public int getTurn() {
        return turn;
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

    public Governance getCurrentGovernance() {
        return currentGovernance;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void setCurrentGovernance(Governance currentGovernance) {
        this.currentGovernance = currentGovernance;
    }
}
