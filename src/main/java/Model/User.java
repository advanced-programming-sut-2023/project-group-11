package Model;

import java.util.ArrayList;

public class User {
    private final static ArrayList<String> randomSlogans = new ArrayList<>();
    private final ArrayList<Trade> previousTrades = new ArrayList<>();
    private int highScore;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private final String recoveryQuestion;
    private final String recoveryAnswer;
    private String slogan;

    public User(String username, String password, String nickname, String email, String recoveryQuestion, String recoveryAnswer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
    }

    public User(String username, String password, String nickname, String email, String recoveryQuestion, String recoveryAnswer, String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
        this.slogan = slogan;
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

    public static ArrayList<String> getRandomSlogans() {
        return randomSlogans;
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
    public static String printTrades() {
        return null;
    }
}