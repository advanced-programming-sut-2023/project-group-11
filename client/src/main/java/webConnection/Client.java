package webConnection;

import view.Main;

import java.net.Socket;

public class Client {
    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = new Connection(new Socket("localhost", 15551));
            Main.main(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return Client.connection;
    }
}
