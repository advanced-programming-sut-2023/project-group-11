package model.chat;

import model.User;

import java.util.ArrayList;

public abstract class Chat {
    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();

    public void sendMessage(Message message) {
        for (User user : users) {

        }
        messages.add(message);
    }

    public void addUSer(User user) {
        users.add(user);
    }

    public void removeUSer(User user) {
        users.remove(user);
    }
}
