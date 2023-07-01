package model.chat;

import webConnection.Client;

import java.io.IOException;

public class GlobalChat extends Chat {
    private static GlobalChat globalChat;

    public GlobalChat(String id) {
        super(id,ChatType.GLOBAL);
    }

    public static GlobalChat getInstance() {
        try {
            Client.getConnection().doInServer("ChatController", "setGlobalChat");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (globalChat == null) globalChat = new GlobalChat("Global");
        return globalChat;
    }
}
