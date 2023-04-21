package model;

import controller.Utils;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private final String recoveryQuestion;
    private final String recoveryAnswer;
    private final ArrayList<Trade> previousTrades = new ArrayList<>();
    private int highScore;

    public User(String username, String password, String email, String nickname, String recoveryQuestion, String recoveryAnswer, String slogan) {
        this.username = username;
        this.password = Utils.encryptField(password);
        this.email = email;
        this.nickname = nickname;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = Utils.encryptField(recoveryAnswer);
        this.slogan = slogan;
        Stronghold.addUser(this);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecoveryQuestion() {
        return recoveryQuestion;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public boolean isRecoveryAnswerCorrect(String recoveryAnswer) {
        return this.recoveryAnswer.equals(recoveryAnswer);
    }

    public void addTrade(Trade trade) {
        previousTrades.add(trade);
    }

    public String printTrades() {
        String output = "";
        int i = 1;
        for (Trade previousTrade : previousTrades)
            output += (i++) + "- " + previousTrade.resourceType() + previousTrade.resourceAmount()
                    + previousTrade.price() + previousTrade.message() + "\n";
        return output;
    }

}