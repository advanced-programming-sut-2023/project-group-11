package model.chat;

public class GlobalChat extends Chat {
    private static GlobalChat globalChat;

    public GlobalChat(String id) {
        super(id,ChatType.GLOBAL);
    }

    public static GlobalChat getInstance() {
        if (globalChat == null) globalChat = new GlobalChat("Global");
        return globalChat;
    }
}
