package webConnetion;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

public class ReceivingPacket {
    private OperationType operationType;
    private String className;
    private String methodName;
    private ArrayList<Object> parameters;

    public ReceivingPacket(String input) {
        JSONObject packet = new JSONObject(input);
        operationType = OperationType.valueOf((String) packet.get("operationType"));
        className = (String) packet.get("className");
        methodName = (String) packet.get("methodName");
        parameters = new ArrayList<>(((JSONArray) packet.get("parameters")).toList());
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<Object> getParameters() {
        return parameters;
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
