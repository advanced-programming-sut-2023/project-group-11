package model.chat;

import model.User;

import java.util.ArrayList;

public class PrivateChat extends Chat {

    public PrivateChat(User owner, ArrayList<User> users) {
        super(owner, owner.getUsername() + users.get(0).getUsername(), ChatType.PRIVATE, users);
    }
}
