package controller;

import model.Stronghold;
import model.chat.Chat;
import model.chat.Message;

import java.util.ArrayList;

public class ChatController {
    public static Message sendMessage(ArrayList<Object> parameters) {
        String content = (String) parameters.get(0);
        String chatId = (String) parameters.get(1);

        Message message = new Message(content, Stronghold.getCurrentUser().getUsername());
        Chat chat = Stronghold.getChatById(chatId);
        return message;
    }
}
