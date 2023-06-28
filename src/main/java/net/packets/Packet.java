package net.packets;

import com.google.gson.Gson;
import net.Client;
import net.Server;

public class Packet {

    private final PacketType type;

    public Packet(PacketType type) {
        this.type = type;
    }

    public static Packet newPacket(byte[] data) {
        return new Gson().fromJson(new String(data).trim(), Packet.class);
    }

    public PacketType getType() {
        return type;
    }

    public void writeData(Client client){
        client.sendData(getData());
    }

    public void writeData(Server server){
        server.sendDataToAllClients(getData());
    }

    public Packet readData(){
        return new Gson().fromJson(new String(getData()),getClass());
    }

    public byte[] getData(){
        return new Gson().toJson(this).getBytes();
    }

}
