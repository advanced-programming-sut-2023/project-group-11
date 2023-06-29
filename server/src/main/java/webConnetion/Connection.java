package webConnetion;

import com.google.gson.Gson;
import model.Stronghold;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private User currentUser;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: ip=" + socket.getInetAddress().getHostAddress() + " port= " + socket.getPort());
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                ReceivingPacket receivingPacket = new ReceivingPacket(in.readUTF());
                Class<?> controllerClass = Class.forName("controller." + receivingPacket.getClassName());
                Method controllerMethod = controllerClass.getDeclaredMethod(receivingPacket.getMethodName(), ArrayList.class);
                if(receivingPacket.getMethodName().equals("loginUser"))
                    currentUser = Stronghold.getUserByUsername((String) receivingPacket.getParameters().get(0));
                SendingPacket sendingPacket;
                if(currentUser != null)
                    Stronghold.setCurrentUser(currentUser);
                if (receivingPacket.getOperationType().equals(OperationType.VOID))
                    controllerMethod.invoke(null, receivingPacket.getParameters());
                else {
                    Object result = controllerMethod.invoke(null, receivingPacket.getParameters());
                    sendingPacket = new SendingPacket(result);
                    out.writeUTF(new Gson().toJson(sendingPacket));
                }
            } catch (IOException e) {
                System.out.println("Connection \"ip=" + socket.getInetAddress().getHostAddress() + " port=" + socket.getPort() + "\" lost!");
                throw new RuntimeException(e);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                System.out.println("Reflection Problem!");
                throw new RuntimeException(e);
            }
        }
    }
}
