package model.chat;

import webConnection.Client;

public class GlobalChat extends Chat {
    private static GlobalChat globalChat;

    public GlobalChat(String name) {
        super(name, ChatType.GLOBAL);
    }

    public static GlobalChat getInstance() {
        Client.getConnection().doInServer("ChatController", "setGlobalChat");
        if (globalChat == null) globalChat = new GlobalChat("Global");
        return globalChat;
    }
}
