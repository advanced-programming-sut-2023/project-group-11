package webConnetion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Packet {
    private String className;
    private String methodName;
    private final ArrayList<Objects> parameters = new ArrayList<>();

    public Packet(String input) {
        JSONObject packet = new JSONObject(input);
        className = (String) packet.get("className");
        methodName = (String) packet.get("methodName");
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<Objects> getParameters() {
        return parameters;
    }
}
