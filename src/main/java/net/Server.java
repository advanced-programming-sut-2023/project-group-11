package net;

import net.packets.Packet;
import net.packets.Packet00Signup;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {

    private static Server server;
    private DatagramSocket socket;
    private int port = 1331;
    private static ArrayList<Client> clients = new ArrayList<>();

    public Server() {
        server = this;
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static Server getServer() {
        return server;
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
            System.out.println("Client > " + new String(data).trim());
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        if (new String(data).trim().equals("Connection request")) {
            Client client = new Client(address, port);
            System.out.println("    ip: " + address + " port: " + port);
            return;
        }
        Packet initialPacket = Packet.newPacket(data);
        switch (initialPacket.getType()) {
            case SIGNUP -> {
                Packet00Signup packet = Packet00Signup.newPacket(data);
                sendDataToAllClients(data,address,port);
            }
            case LOGIN -> {
                Packet packet;
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public void sendDataToAllClients(byte[] data) {
        for (Client client : clients)
            sendData(data, client.getIpAddress(), client.getPort());
    }

    public void sendDataToAllClients(byte[] data,InetAddress address,int port) {
        for (Client client : clients)
            if(!(client.getIpAddress().equals(address) && client.getPort() == port))
                sendData(data, client.getIpAddress(), client.getPort());
    }
}
