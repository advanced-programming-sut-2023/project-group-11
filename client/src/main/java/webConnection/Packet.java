package webConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Packet {
    private String className;
    private String methodName;
    private ArrayList<Objects> parameters = new ArrayList<>();

    public Packet(String className, String methodName, Objects... parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameters.addAll(List.of(parameters));
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
