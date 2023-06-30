package model.chat;

public class PrivateChat extends Chat {
    public PrivateChat(String id) {
        super(id, ChatType.PRIVATE);
    }
}
