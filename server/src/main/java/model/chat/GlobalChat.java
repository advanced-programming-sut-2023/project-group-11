package model.chat;

import model.User;

import java.util.ArrayList;

public class GlobalChat extends Chat {
    private static GlobalChat globalChat;

    public GlobalChat(User owner, String name, ChatType chatType) {
        super(owner, name, chatType, new ArrayList<>());
    }

    public static GlobalChat getInstance() {
        if (globalChat == null) globalChat = new GlobalChat(null, "Global", ChatType.GLOBAL);
        return globalChat;
    }
}
