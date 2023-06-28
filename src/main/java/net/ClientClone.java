package net;

import model.User;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientClone extends Client{

    private User currentUser;

    public ClientClone(InetAddress ipAddress, int port) {
        super();
        try {
            this.socket = new DatagramSocket();
            this.port = port;
            this.ipAddress = ipAddress;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Server.getClients().add(this);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
