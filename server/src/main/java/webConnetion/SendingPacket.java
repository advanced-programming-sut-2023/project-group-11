package webConnetion;

import java.util.ArrayList;

public class SendingPacket {
    private Object value;
    private final String type;
    private  String menuName;
    private  String methodName;

    public SendingPacket(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public SendingPacket(String type, String menuName, String methodName) {
        this.type = type;
        this.menuName = menuName;
        this.methodName = methodName;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMenuName() {
        return menuName;
    }
}
