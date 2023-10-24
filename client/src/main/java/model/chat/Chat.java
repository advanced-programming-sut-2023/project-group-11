package model.chat;

public abstract class Chat {
    private final String name;
    private final ChatType chatType;

    public Chat(String name, ChatType chatType) {
        this.name = name;
        this.chatType = chatType;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public String getName() {
        return name;
    }
}
