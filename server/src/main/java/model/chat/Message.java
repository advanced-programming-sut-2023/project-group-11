package model.chat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Message {
    private String content;
    private final String senderName;
    private final String sentTime;
    private final ArrayList<Emoji> emojis = new ArrayList<>();

    public Message(String content, String senderName) {
        this.sentTime = getTime();
        this.content = content;
        this.senderName = senderName;
    }

    private static String getTime() {
        return LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);
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

    public enum Emoji {

    }
}

