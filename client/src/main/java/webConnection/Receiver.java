//package webConnection;
//
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.net.Socket;
//
//public class Receiver extends Thread {
//    private final Socket socket;
//    private DataInputStream dataInputStream;
//
//    public Receiver(Socket socket) throws IOException {
//        this.socket = socket;
//        this.dataInputStream = new DataInputStream(socket.getInputStream());
//    }
//
//    public Socket getSocket() {
//        return socket;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            String notification = null;
//            try {
//                notification = dataInputStream.readUTF();
//                System.out.println(notification);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
