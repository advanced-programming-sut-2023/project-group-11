package model.chat;

public class GlobalChat extends Chat {
    private static GlobalChat globalChat;

    public GlobalChat(String id) {
        super(id);
    }

    public static GlobalChat getInstance() {
        if (globalChat == null) globalChat = new GlobalChat("123");
        return globalChat;
    }
}
