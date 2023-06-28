package net;

import model.Stronghold;
import net.packets.Packet;
import net.packets.Packet00Signup;
import net.packets.Packet01Login;
import net.packets.Packet02UpdateProfile;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {

    private static Server server;
    private DatagramSocket socket;
    private int port = 1331;
    private static ArrayList<ClientClone> clients = new ArrayList<>();

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
        ClientClone client;
        if (new String(data).trim().equals("Connection request")) {
            client = new ClientClone(address, port);
            System.out.println("    ip: " + address + " port: " + port);
            return;
        }
        client = getClient(address, port);
        Packet initialPacket = Packet.newPacket(data);
        switch (initialPacket.getType()) {
            case SIGNUP -> {
                Packet00Signup packet = Packet00Signup.newPacket(data);
                sendDataToAllClients(data,address,port);
            }
            case LOGIN -> {
                Packet01Login packet = Packet01Login.newPacket(data);
                client.setCurrentUser(Stronghold.getUserByUsername(packet.getUsername()));
            }
            case UPDATE_PROFILE -> {
                Packet02UpdateProfile packet = Packet02UpdateProfile.newPacket(data);
                sendDataToAllClients(data, address, port);
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

    public static ArrayList<ClientClone> getClients() {
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

    private ClientClone getClient(InetAddress address,int port){
        for (ClientClone client:clients)
            if(client.getIpAddress().equals(address) && client.getPort() == port)
                return client;
        return null;
    }
}
