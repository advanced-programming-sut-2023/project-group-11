package net.packets;

import com.google.gson.Gson;
import model.User;
import net.Client;

import static controller.Utils.updateDatabase;

public class Packet02UpdateProfile extends Packet{

    private final String originalUsername;
    private final User updatedUser;

    public Packet02UpdateProfile(String originalUsername, User updatedUser) {
        super(PacketType.UPDATE_PROFILE);
        this.originalUsername = originalUsername;
        this.updatedUser = updatedUser;
    }

    public String getOriginalUsername() {
        return originalUsername;
    }

    @Override
    public void writeData(Client client) {
        super.writeData(client);
        updateDatabase("users");
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public static Packet02UpdateProfile newPacket(byte[] data) {
        return new Gson().fromJson(new String(data).trim(), Packet02UpdateProfile.class);
    }
}
