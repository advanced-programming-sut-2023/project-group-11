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
        String chatName = (String) parameters.get(1);
//        ChatType chatType = ChatType.valueOf((String) parameters.get(2));

        Message message = new Message(content, Stronghold.getCurrentUser().getUsername());
        Chat chat = Stronghold.getChatByName(chatName);

        chat.sendMessage(message);
        return message;
    }

    public static ArrayList<Message> getChatMessages(ArrayList<Object> parameters) {
        String chatId = (String) parameters.get(0);
        Chat chat = Stronghold.getChatByName(chatId);
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
        int messageId = Integer.parseInt((String) parameters.get(1));

        Chat chat = Stronghold.getChatByName(chatId);
        Message message = chat.getMessageById(messageId);
        if (message.getSenderName().equals(Stronghold.getCurrentUser().getUsername())) chat.removeMessage(message);
    }

    public static void editMessage(ArrayList<Object> parameters) {
        String chatId = (String) parameters.get(0);
        int messageId = Integer.parseInt((String) parameters.get(1));
        String newContent = (String) parameters.get(2);

        Chat chat = Stronghold.getChatByName(chatId);
        Message message = chat.getMessageById(messageId);
        if (message.getSenderName().equals(Stronghold.getCurrentUser().getUsername())) message.setContent(newContent);
    }

    public static ArrayList<String> findUsername(ArrayList<Object> parameters) {
        String toBeFound = (String) parameters.get(0);
        ArrayList<String> usernames = new ArrayList<>();
        Pattern pattern = Pattern.compile(toBeFound);

        for (User user : Stronghold.getUsers()) {
            String username = user.getUsername();
            if (pattern.matcher(username).find() && !user.equals(Stronghold.getCurrentUser())) usernames.add(username);
        }
        return usernames;
    }

    public static ArrayList<String> findRoom(ArrayList<Object> parameters) {
        String toBeFound = (String) parameters.get(0);
        ArrayList<String> chatNames = new ArrayList<>();
        Pattern pattern = Pattern.compile(toBeFound);

        for (Chat chat : Stronghold.getChats())
            if (chat instanceof ChatRoom chatRoom) {
                String chatName = chatRoom.getName();
                if (pattern.matcher(chatName).find())
                    chatNames.add(chatName);
            }
        return chatNames;
    }

    public static void setGlobalChat(ArrayList<Object> parameters) {
        GlobalChat.getInstance();
    }

    public static Chat getGlobalChat(ArrayList<Object> parameters) {
        return GlobalChat.getInstance();
    }

    public static Chat createChat(ArrayList<Object> parameters) {
        ArrayList<String> usernames = (ArrayList<String>) parameters.get(0);
        ChatType chatType = ChatType.valueOf((String) parameters.get(1));
        ArrayList<User> users = new ArrayList<>();
        usernames.forEach(username -> users.add(Stronghold.getUserByUsername(username)));

        Chat chat = null;
        switch (chatType) {
            case GLOBAL -> chat = GlobalChat.getInstance();
            case PRIVATE -> {
                chat = Stronghold.getChatByName(Stronghold.getCurrentUser().getUsername() + usernames.get(0));
                if (chat == null)
                    chat = Stronghold.getChatByName(usernames.get(0) + Stronghold.getCurrentUser().getUsername());
                if (chat == null) chat = new PrivateChat(Stronghold.getCurrentUser(), users);
            }
            case CHAT_ROOM -> {
                String name = (String) parameters.get(2);
                if ((chat = Stronghold.getChatByName(name)) == null)
                    chat = new ChatRoom(Stronghold.getCurrentUser(), name, users);
            }
        }
        return chat;
    }
}
