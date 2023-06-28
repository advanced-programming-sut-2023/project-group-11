package net.packets;

import com.google.gson.Gson;

public class Packet00Signup extends Packet{

    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final String slogan;
    private final String recoveryQuestion;
    private final String recoveryAnswer;

    public Packet00Signup(String username, String password, String email, String nickname, String slogan, String recoveryQuestion, String recoveryAnswer) {
        super(PacketType.SIGNUP);
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.slogan = slogan;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
    }
    public static Packet00Signup newPacket(byte[] data) {
        return new Gson().fromJson(new String(data).trim(), Packet00Signup.class);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getRecoveryQuestion() {
        return recoveryQuestion;
    }

    public String getRecoveryAnswer() {
        return recoveryAnswer;
    }
}
