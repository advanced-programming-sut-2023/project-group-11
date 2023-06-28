package net;

import controller.SignupMenuController;
import model.Stronghold;
import net.packets.Packet;
import net.packets.Packet00Signup;

import java.io.IOException;
import java.net.*;

public class Client extends Thread {
    private InetAddress ipAddress;
    private int port;
    private DatagramSocket socket;

    public Client(String ipAddress) {
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        Stronghold.setCurrentClient(this);
        Server.getClients().add(this);
    }

    public Client(InetAddress ipAddress,int port) {
        try {
            this.socket = new DatagramSocket();
            this.port = port;
            this.ipAddress = ipAddress;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Stronghold.setCurrentClient(this);
        Server.getClients().add(this);
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server > " + new String(data).trim());
            parsePacket(packet.getData());
        }
    }

    private void parsePacket(byte[] data) {
        Packet initialPacket = Packet.newPacket(data);
        switch (initialPacket.getType()){
            case SIGNUP -> {
                Packet00Signup packet = Packet00Signup.newPacket(data);
                SignupMenuController.createUser(packet.getUsername(),packet.getPassword(),
                        packet.getEmail(),packet.getNickname(),packet.getSlogan(),
                        packet.getRecoveryQuestion(),packet.getRecoveryAnswer());
            }
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
