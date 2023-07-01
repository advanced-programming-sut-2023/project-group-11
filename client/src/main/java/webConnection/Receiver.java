package webConnection;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private String receivedPacket;
    private boolean isReceivedPacketAccessible = false;
    private final Socket socket;
    private DataInputStream in;

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public String getReceivedPacket() {
        return receivedPacket;
    }

    public boolean isReceivedPacketAccessible() {
        return isReceivedPacketAccessible;
    }

    public void setReceivedPacketAccessible(boolean receivedPacketAccessible) {
        isReceivedPacketAccessible = receivedPacketAccessible;
    }

    @Override
    public void run() {
        while (true) {
            try {
                receivedPacket = (String) new ObjectInputStream(in).readObject();
//                JSONObject packet = new JSONObject(receivedPacket);
//                if (packet.get("type").equals("command")) {
//
//                } else
                    isReceivedPacketAccessible = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
