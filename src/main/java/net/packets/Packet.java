package net.packets;

import com.google.gson.Gson;
import net.Client;
import net.Server;

public class Packet {

    public enum PacketType {
        INVALID(-1), SIGNUP(0),LOGIN(1);

        private final int packetId;

        PacketType(int packetId) {
            this.packetId = packetId;
        }

        public int getId() {
            return packetId;
        }
    }

    private final PacketType type;

    public Packet(PacketType type) {
        this.type = type;
    }

    public static Packet newPacket(byte[] data) {
        System.out.println(new String(data).trim());
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
        System.out.println(new Gson().toJson(this));
        return new Gson().toJson(this).getBytes();
    }

}
