package net.packets;

import com.google.gson.Gson;

public class Packet01Login extends Packet{
    private String username;

    public Packet01Login(String username) {
        super(PacketType.LOGIN);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public static Packet01Login newPacket(byte[] data) {
        return new Gson().fromJson(new String(data).trim(), Packet01Login.class);
    }
}
