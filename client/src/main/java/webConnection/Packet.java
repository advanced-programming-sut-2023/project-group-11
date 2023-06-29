package webConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Packet {
    private OperationType operationType;
    private String className;
    private String methodName;
    private ArrayList<Object> parameters = new ArrayList<>();

    public Packet(OperationType operationType, String className, String methodName, Object[] parameters) {
        this.operationType = operationType;
        this.className = className;
        this.methodName = methodName;
        this.parameters.addAll(Arrays.asList(parameters));
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
