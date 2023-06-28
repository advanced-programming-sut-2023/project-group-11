package net.packets;

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
