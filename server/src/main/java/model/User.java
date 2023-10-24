package model;

import java.time.LocalTime;
import java.util.HashSet;

public class User implements Comparable<User> {
    private final HashSet<String> friends = new HashSet<>();
    private final HashSet<String> friendsRequest = new HashSet<>();
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

    public void updateOnlineState() {
        if (Stronghold.getConnectionByUser(this) != null) lastSeen = "Online";
    }

    public String getLastSeen() {
        updateOnlineState();
        return lastSeen;
    }

    public void setLastSeen() {
        this.lastSeen = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
    }

    public HashSet<String> getFriends() {
        return friends;
    }

    public void addFriend(String username) {
        friends.add(username);
    }

    public HashSet<String> getFriendsRequest() {
        return friendsRequest;
    }

    public void addFriendRequest(String username) {
        friendsRequest.add(username);
    }

    public void removeFriendRequest(String username) {
        friendsRequest.remove(username);
    }

    public boolean isAlreadyFriend(User currentUser) {
        for (String friendName : friends)
            if (currentUser.getUsername().equals(friendName)) return true;
        return false;
    }

    public boolean hasReachedFriendshipLimit() {
        return friends.size() > 100;
    }

    public boolean hasRequest(String username) {
        for (String s : friendsRequest)
            if (username.equals(s)) return true;
        return false;
    }

    @Override
    public int compareTo(User o) {
        if (this.score < o.score) return 1;
        else if (this.score > o.score) return -1;
        else return this.username.compareTo(o.username);
    }
}