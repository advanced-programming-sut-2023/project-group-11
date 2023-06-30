package webConnection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.json.JSONArray;
import view.enums.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutput out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutput getOut() {
        return out;
    }

    public Message checkAction(String className, String methodName, Object... parameters) throws IOException {
        Packet packet = new Packet(OperationType.CHECK_ACTION, className, methodName, parameters);
        sendData(packet);
        return getRespond();
    }

    public Object getData(String className, String methodName, Object... parameters) throws IOException {
        Packet packet = new Packet(OperationType.GET_DATA, className, methodName, parameters);
        sendData(packet);
        return receiveData().get("value");
    }

    public ArrayList getArrayData(String className, String methodName, Object... parameters) throws IOException {
        Packet packet = new Packet(OperationType.GET_ARRAY_DATA, className, methodName, parameters);
        sendData(packet);
        return new Gson().fromJson(receiveArrayData().toString(),new TypeToken<ArrayList>(){}.getType());
    }

    public JSONObject getJSONData(String className, String methodName, Object... parameters) throws IOException {
        Packet packet = new Packet(OperationType.GET_DATA, className, methodName, parameters);
        sendData(packet);
        return (JSONObject) receiveData().get("value");
    }

    public JSONArray getJSONArrayData(String className, String methodName, Object... parameters) throws IOException {
        Packet packet = new Packet(OperationType.GET_ARRAY_DATA, className, methodName, parameters);
        sendData(packet);
        return receiveArrayData();
    }

    public void doInServer (String className, String methodName, Object... parameters) throws IOException {
        Packet packet = new Packet(OperationType.VOID, className, methodName, parameters);
        sendData(packet);
    }

    private void sendData (Packet packet) throws IOException {
        String data = new Gson().toJson(packet);
        out.writeUTF(data);
    }

    private Message getRespond() throws IOException {
        return Message.valueOf((String) receiveData().get("value"));
    }

    private JSONObject receiveData() throws IOException {
        return new JSONObject(in.readUTF());
    }

    private JSONArray receiveArrayData() throws IOException {
        return (JSONArray) receiveData().get("value");
    }
}
