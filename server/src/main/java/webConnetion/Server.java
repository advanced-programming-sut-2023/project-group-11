package webConnetion;

import controller.EntryMenuController;
import controller.Utils;
import model.Stronghold;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server(int port) {
        System.out.println("Starting server...");
        EntryMenuController.fillAllFieldsWithPreviousData();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(Utils::endStronghold));
        new Server(15551);
    }
}
