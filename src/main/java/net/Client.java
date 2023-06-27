package net;

import model.Stronghold;

import java.io.IOException;
import java.net.*;

public class Client extends Thread {
    private InetAddress ipAddress;
    private DatagramSocket socket;

    public Client(String ipAddress) {
        try {
            this.socket = new DatagramSocket();
            System.out.println(socket.getPort());
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
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
}
