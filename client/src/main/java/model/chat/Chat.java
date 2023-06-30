package model.chat;

import webConnection.Connection;

import java.util.ArrayList;

public abstract class Chat {
    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();
    private final String id;

    public Chat(String id) {
        this.id = id;
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

    public void sendMessage(){
        for (Connection connection : connections) {

        }
    }
}
