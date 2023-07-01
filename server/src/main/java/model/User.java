package model;

import model.chat.Chat;

import java.util.ArrayList;

public class User implements Comparable<User> {
    private final transient ArrayList<Chat> chats = new ArrayList<>();
    private final String recoveryQuestion;
    private final String recoveryAnswer;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String avatarFileName = "1.png";
    private String lastSeen = "Online";
    private int score;
    private int rank;
    private boolean stayLoggedIn;

    public User(String username, String password, String email, String nickname, String recoveryQuestion, String recoveryAnswer, String slogan) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
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

    public String getAvatarFileName() {
        return System.getProperty("user.dir") + "/src/main/resources/IMG/avatars/" + avatarFileName;
//        ImageView imageView = new ImageView(new Image(
//                System.getProperty("user.dir") + "/src/main/resources/IMG/avatars/" + avatarFileName));
//        imageView.setFitHeight(40);
//        imageView.setFitWidth(40);
//        return imageView;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
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

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public boolean isRecoveryAnswerCorrect(String recoveryAnswer) {
        return this.recoveryAnswer.equals(recoveryAnswer);
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void updateLastSeen() {
        if (Stronghold.getConnectionByUser(this) != null) setLastSeen("Online");
    }

    public String getLastSeen() {
        updateLastSeen();
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public int compareTo(User o) {
        if (this.score < o.score) return 1;
        else if (this.score > o.score) return -1;
        else return this.username.compareTo(o.username);
    }
}