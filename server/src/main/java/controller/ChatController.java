package controller;

import model.Stronghold;
import model.User;
import model.chat.Message;
import model.chat.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
        setSeen(chat.getMessages());
        return chat.getMessages();
    }

    private static void setSeen(ArrayList<Message> messages) {
        messages.forEach(message -> message.setSeen(true));
    }

    public static ArrayList<Chat> getCurrentUserChats(ArrayList<Object> parameters) {
        return Stronghold.getCurrentUser().getChats();
    }

    public static void removeMessage(ArrayList<Object> parameters) {
        String chatId = (String) parameters.get(0);
        int messageId = (int) parameters.get(1);

        Chat chat = Stronghold.getChatById(chatId);
        chat.removeMessage(chat.getMessageById(messageId));
    }

    public static ArrayList<String> findUsername(ArrayList<Object> parameters) {
        String toBeFound = (String) parameters.get(0);
        ArrayList<String> usernames = new ArrayList<>();
        Pattern pattern = Pattern.compile(toBeFound);

        for (User user : Stronghold.getUsers()) {
            String username = user.getUsername();
            if (pattern.matcher(username).find() && !user.equals(Stronghold.getCurrentUser())) usernames.add(username);
        }
        if (usernames.size() > 0) System.out.println(usernames.get(0));
        return usernames;
    }

    public static void setGlobalChat(ArrayList<Object> parameters) {
        GlobalChat.getInstance();
    }
}
