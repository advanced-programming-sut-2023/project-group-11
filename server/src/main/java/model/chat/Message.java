package model.chat;

import model.User;

import java.time.LocalTime;
import java.util.ArrayList;

public class Message {
    private String content;
    private final User sender;
    private final String sentTime;
    private final ArrayList<Emoji> emojis = new ArrayList<>();

    public Message(String content, User sender) {
        this.sentTime = LocalTime.now().toString();
        this.content = content;
        this.sender = sender;
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

    public User getSender() {
        return sender;
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

