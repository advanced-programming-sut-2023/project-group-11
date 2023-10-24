package webConnetion;

import com.google.gson.Gson;
import model.Stronghold;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isInScoreboard = false;
    private boolean isInMainMenu = false;

    private User currentUser;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: ip=" + socket.getInetAddress().getHostAddress() + " port= " + socket.getPort());
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        Stronghold.addConnection(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
//                synchronized (Stronghold.class){
                ReceivingPacket receivingPacket = new ReceivingPacket(in.readUTF());
                Class<?> controllerClass = Class.forName("controller." + receivingPacket.getClassName());
                Method controllerMethod = controllerClass.getDeclaredMethod(receivingPacket.getMethodName(), ArrayList.class);
                if (receivingPacket.getMethodName().equals("loginUser"))
                    currentUser = Stronghold.getUserByUsername((String) receivingPacket.getParameters().get(0));
                if (currentUser != null)
                    Stronghold.setCurrentUser(currentUser);
                sendRespond(receivingPacket, controllerMethod);
//                }
            } catch (IOException e) {
                System.out.println("Connection \"ip=" + socket.getInetAddress().getHostAddress() + " port=" + socket.getPort() + "\" lost!");
//                throw new RuntimeException(e);
                Stronghold.removeConnection(this);
                break;
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                System.out.println("Reflection Problem!");
                throw new RuntimeException(e);
            }
        }
    }

    private void sendRespond(ReceivingPacket receivingPacket, Method controllerMethod) throws IllegalAccessException, InvocationTargetException, IOException {
        SendingPacket sendingPacket;
        if (receivingPacket.getOperationType().equals(OperationType.VOID))
            controllerMethod.invoke(null, receivingPacket.getParameters());
        else {
            Object result = controllerMethod.invoke(null, receivingPacket.getParameters());
            sendingPacket = new SendingPacket(result, "data");
            new ObjectOutputStream(out).writeObject(new Gson().toJson(sendingPacket));
        }
    }

    public static void sendNotification(String menuName, String methodName, Connection connection) {
        String packet = new Gson().toJson(new SendingPacket("command", menuName, methodName));
        try {
            new ObjectOutputStream(connection.getOut()).writeObject(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isInScoreboard() {
        return isInScoreboard;
    }

    public void setInScoreboard(boolean inScoreboard) {
        isInScoreboard = inScoreboard;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public boolean isInMainMenu() {
        return isInMainMenu;
    }

    public void setInMainMenu(boolean inMainMenu) {
        isInMainMenu = inMainMenu;
    }
}
