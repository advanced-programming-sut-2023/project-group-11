package controller;

import model.Stronghold;
import model.chat.Message;
import model.chat.*;

import java.util.ArrayList;

public class ChatController {
    public static Message sendMessage(ArrayList<Object> parameters) {
        String content = (String) parameters.get(0);
        String chatId = (String) parameters.get(1);
        ChatType chatType = ChatType.valueOf((String) parameters.get(2));

        Message message = new Message(content, Stronghold.getCurrentUser().getUsername());
        Chat chat = Stronghold.getChatById(chatId);
        if (chat == null) switch (chatType) {
            case GLOBAL -> chat = GlobalChat.getInstance();
            case PRIVATE -> chat = new PrivateChat(chatId);
            case CHAT_ROOM -> chat = new ChatRoom(chatId);
        }
        chat.sendMessage(message);
        return message;
    }

    public static ArrayList<Message> getChatMessages(ArrayList<Object> parameters) {
        String chatId = (String) parameters.get(0);
        Chat chat = Stronghold.getChatById(chatId);
        return chat.getMessages();
    }
}
