package model.chat;

import model.Stronghold;
import webConnetion.Connection;

import java.util.ArrayList;

public abstract class Chat {
    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();
    private final String id;
    private final ChatType chatType;
    private final int MAX_MESSAGE_NUMBER = 100;

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
//        for (Connection connection : connections) {
//
//        }
        //TODO: need implementation for live chat update
        messages.add(message);
        if (messages.size() > MAX_MESSAGE_NUMBER) messages.remove(0);
    }

    public void addUSer(Connection user) {
        connections.add(user);
    }

    public void removeUSer(Connection user) {
        connections.remove(user);
    }

    public Message getMessageById(int id) {
        for (Message message : messages)
            if (message.getId() == id) return message;
        return null;
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }
}
