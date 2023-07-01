package model.chat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Message {
    private String content;
    private final int id;
    private static int counter = 1;
    private final String senderName;
    private final String sentTime;
    private final ArrayList<Emoji> emojis = new ArrayList<>();
    private boolean seen = false;

    public Message(String content, String senderName) {
        this.sentTime = getTime();
        this.id = counter++;
        this.content = content;
        this.senderName = senderName;
    }

    private static String getTime() {
        return LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public int getId() {
        return id;
    }

    public void addEmoji(Emoji emoji) {
        emojis.add(emoji);
    }

    public void removeEmoji(Emoji emoji) {
        emojis.remove(emoji);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSentTime() {
        return sentTime;
    }

    public ArrayList<Emoji> getEmojis() {
        return emojis;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public enum Emoji {

    }
}

