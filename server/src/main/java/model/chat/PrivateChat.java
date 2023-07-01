package model.chat;

import model.Stronghold;
import model.User;

import java.util.ArrayList;

public class PrivateChat extends Chat {
    public PrivateChat(User owner, ArrayList<User> users) {
        super(owner, users.get(0).getUsername(), ChatType.PRIVATE, users);
    }

    @Override
    public String getName() {
        if (users.get(0).equals(Stronghold.getCurrentUser()))
            return users.get(1).getUsername();
        return users.get(0).getUsername();
    }
}
