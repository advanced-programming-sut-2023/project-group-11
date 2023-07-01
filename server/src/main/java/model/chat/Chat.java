package model.chat;

import model.Stronghold;
import model.User;
import webConnetion.Connection;

import java.util.ArrayList;

public abstract class Chat {
    private transient final ArrayList<Message> messages = new ArrayList<>();
    private transient final User owner;
    protected transient final ArrayList<Connection> connections = new ArrayList<>();
    protected transient final ArrayList<User> users;
    private final String name;
    private final ChatType chatType;
    private transient final int MAX_MESSAGE_NUMBER = 100;

    public Chat(User owner, String name, ChatType chatType, ArrayList<User> users) {
        this.owner = owner;
        this.name = name;
        this.chatType = chatType;
        this.users = users;
        this.users.add(0, owner);
        Stronghold.addChat(this);
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
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

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    public void addUSer(User user) {
        users.add(user);
    }

    public void removeUSer(User user) {
        users.remove(user);
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
