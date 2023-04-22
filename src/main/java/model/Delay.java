package model;

import java.util.HashMap;

public class Delay {
    private static final HashMap<User, Delay> delayedUsers = new HashMap<>();
    private long lastLoginCommandTime;
    private int delayTime = 0;

    public static void addDelayedUser(User user, Delay delay) {
        delayedUsers.put(user, delay);
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public static Integer getDelay(User user) {
        Delay delay = delayedUsers.get(user);
        return delayedUsers.get(user) == null ? null : delay.delayTime;
    }

    public void setLastLoginCommandTime(long lastLoginCommandTime) {
        this.lastLoginCommandTime = lastLoginCommandTime;
    }

    public long getLastCommandTime() {
        return lastLoginCommandTime;
    }

    public Delay(long lastLoginCommandTime) {
        this.lastLoginCommandTime = lastLoginCommandTime;
    }
}
