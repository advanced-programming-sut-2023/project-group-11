package net;

import controller.SignupMenuController;
import model.Stronghold;
import model.User;
import net.packets.Packet;
import net.packets.Packet00Signup;

import java.io.IOException;
import java.net.*;

public class Client extends Thread {

    private static Client client;
    protected InetAddress ipAddress;
    protected int port;
    protected DatagramSocket socket;

    private Client(String ipAddress) {
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Client() {
    }

    public static Client getClient(String ipAddress){
        if(client == null)
            client = new Client(ipAddress);
        return client;
    }
    public static Client getClient(){
        return client;
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
