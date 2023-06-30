package model.chat;

import model.Stronghold;
import webConnetion.Connection;

import java.util.ArrayList;

public abstract class Chat {
    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();
    private final String id;
    private final ChatType chatType;

    public Chat(String id, ChatType chatType) {
        this.id = id;
        this.chatType = chatType;
        Stronghold.addChat(this);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void sendMessage(Message message) {
        for (Connection connection : connections) {

        }
        messages.add(message);
    }

    public void addUSer(Connection user) {
        connections.add(user);
    }

    public void removeUSer(Connection user) {
        connections.remove(user);
    }
}
