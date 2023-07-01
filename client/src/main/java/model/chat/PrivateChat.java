package model.chat;

public class PrivateChat extends Chat {
    public PrivateChat(String name) {
        super(name, ChatType.PRIVATE);
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
