package model;

import java.util.HashMap;

public class Delay {
    private static final HashMap<User, Delay> delayedUsers = new HashMap<>();
    private long lastLoginCommandTime;
    private long delayTime = 0;

    public static Delay getDelayByUser(User user) {
        return delayedUsers.get(user);
    }

    public static void addDelayedUser(User user, Delay delay) {
        delayedUsers.put(user, delay);
    }

    public static boolean hasUser(User user) {
        return delayedUsers.containsKey(user);
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setLastLoginCommandTime(long lastLoginCommandTime) {
        this.lastLoginCommandTime = lastLoginCommandTime;
    }

    public long getLastLoginCommandTime() {
        return lastLoginCommandTime;
    }

    public Delay(long lastLoginCommandTime) {
        this.lastLoginCommandTime = lastLoginCommandTime;
    }
}
