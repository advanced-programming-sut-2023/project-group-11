package model.chat;

import model.User;

import java.util.ArrayList;

public class ChatRoom extends Chat {
    public ChatRoom(User owner, String name, ArrayList<User> users) {
        super(owner, name, ChatType.CHAT_ROOM, users);
    }
}
