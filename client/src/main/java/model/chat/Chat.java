package model.chat;

import webConnection.Connection;

import java.util.ArrayList;

public abstract class Chat {
//    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();
    private final String name;
    private final ChatType chatType;

    public Chat(String name, ChatType chatType) {
        this.name = name;
        this.chatType = chatType;
    }

//    public ArrayList<Message> getMessages() {
//        return messages;
//    }

    public ChatType getChatType() {
        return chatType;
    }

    public void sendMessage(Message message) {
        for (Connection connection : connections) {

        }
//        messages.add(message);
    }

    public void addUSer(Connection user) {
        connections.add(user);
    }

    public void removeUSer(Connection user) {
        connections.remove(user);
    }

    public String getName() {
        return name;
    }
}
