package model.chat;

import model.User;

import java.util.ArrayList;

public class GlobalChat extends Chat {
    private static GlobalChat globalChat;

    private GlobalChat() {
    }

    public static GlobalChat getInstance() {
        if (globalChat == null) globalChat = new GlobalChat();
        return globalChat;
    }

    @Override
    public void sendMessage(ArrayList<User> users) {
        for (User user : users) {

        }
    }
}
