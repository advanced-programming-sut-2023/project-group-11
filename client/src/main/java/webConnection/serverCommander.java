package webConnection;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class serverCommander extends Thread {
    private JSONObject packet;

    public serverCommander(JSONObject packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            Class<?> menu = Class.forName("view." + packet.get("menuName"));
            Method method = menu.getDeclaredMethod((String) packet.get("methodName"));
            method.invoke(null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
