package webConnection;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutput out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutput getOut() {
        return out;
    }

    public void sendData (Packet packet) throws IOException {
        out.writeUTF(new JSONObject(packet).toString());
    }
}
