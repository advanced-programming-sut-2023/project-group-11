package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Parsers;
import model.chat.Chat;
import model.chat.ChatType;
import model.chat.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import webConnection.Client;
import webConnection.Connection;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainMenu extends Application {
    private static Stage stage;
    private final Connection connection = Client.getConnection();
    private final String chatController = "ChatController";
    public HBox sendHBox;
    public Button createChatRoom;
    private Chat currentChat;
    private final LinkedList<VBox> selectedMessages = new LinkedList<>();
    private static MainMenu instance;

    @FXML
    private AnchorPane chatPane;
    @FXML
    private ScrollPane globalChat;
    @FXML
    private ScrollPane privateChat;
    @FXML
    private ScrollPane chatRoom;
    @FXML
    private TextField messageContent;
    @FXML
    private TextField usernameSearch;
    @FXML
    private TextField roomSearch;
    @FXML
    private ImageView open;
    @FXML
    private Button sendButton;

    @FXML
    private ListView<String> usernameListView = new ListView<>();
    @FXML
    private ListView<String> roomListView;

    public MainMenu() {
        super();
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/MainMenu.fxml").toExternalForm()));
        MainMenu.stage = stage;
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        Client.getConnection().doInServer("Utils", "setInMainMenu", true);

        stage.show();
    }

    @FXML
    private void initialize() {
        initializeScrollPane(globalChat);
        initializeScrollPane(privateChat);
        initializeScrollPane(chatRoom);
        currentChat = getGetGlobalChat();
        refresh();
        usernameSearch.textProperty().addListener((observableValue, old, newText) -> find(usernameListView, "Username", newText));
        roomSearch.textProperty().addListener((observableValue, old, newText) -> find(roomListView, "Room", newText));
    }

    private Chat getGetGlobalChat() {
        return Parsers.parseChatObject(connection.getJSONData(chatController, "getGlobalChat"));
    }

    private void initializeScrollPane(ScrollPane scrollPane) {
        scrollPane.vvalueProperty().bind(((VBox) scrollPane.getContent()).heightProperty());
    }

    public void newGame() throws Exception {
        new NewGame().start(new Stage());
    }

    public void profileMenu() throws Exception {
        Client.getConnection().doInServer("Utils", "setInMainMenu", false);
        new ProfileMenu().start(SignupMenu.getStage());
    }

    public void mapSelection() throws Exception {
        new MapSelection().start(new Stage());
    }

    public void logout() throws Exception {
        connection.doInServer("Utils", "setInMainMenu", false);
        connection.doInServer("MainMenuController", "logout");
        new SignupMenu().start(SignupMenu.getStage());
    }

    public void lobby() {
    }

    //-----------------------------------------------CHAT----------------------------------------------------//

    public void send() {
        Message message = Parsers.parseMessageObject(connection.getJSONData(chatController,
                "sendMessage", messageContent.getText(), currentChat.getName(), currentChat.getChatType().name()));

        if (!message.getContent().isEmpty()) sendMessage(message);
    }

    private void sendMessage(Message message) {
        VBox vBox = message.toVBox();
        vBox.setOnMouseClicked(mouseEvent -> {
            changeOpacityVbox(vBox, 0.7, 1);
            if (vBox.getOpacity() == 0.7) selectedMessages.add(vBox);
            else selectedMessages.remove(vBox);
        });


        switch (currentChat.getChatType()) {
            case GLOBAL -> ((VBox) globalChat.getContent()).getChildren().add(vBox);
            case PRIVATE -> ((VBox) privateChat.getContent()).getChildren().add(vBox);
            case CHAT_ROOM -> ((VBox) chatRoom.getContent()).getChildren().add(vBox);
        }
        ViewUtils.clearFields(messageContent);
    }

    private void changeOpacityVbox(VBox vBox, double v, int i) {
        if (vBox.getOpacity() == v) vBox.setOpacity(i);
        else vBox.setOpacity(v);
    }

    public void showGlobal() {
        changeVisibility(globalChat, privateChat, chatRoom);
        currentChat = getGetGlobalChat();
        sendHBox.setVisible(true);
    }

    public void showPrivate() {
        changeVisibility(privateChat, globalChat, chatRoom);
        I_DONT_KNOWWW(privateChat, "Username", usernameListView, usernameSearch);
    }

    public void showChatRoom() {
        changeVisibility(chatRoom, globalChat, privateChat);
        I_DONT_KNOWWW(chatRoom, "Room", roomListView, roomSearch, createChatRoom);
    }

    private void I_DONT_KNOWWW(ScrollPane scrollPane, String field, ListView<String> listView, TextField textField, Node... nodes) {
        initializeChats();

        VBox vBox = (VBox) scrollPane.getContent();
        vBox.getChildren().clear();
        vBox.getChildren().addAll(textField, listView);
        for (Node node : nodes) vBox.getChildren().add(node);
        find(listView, field, "");
    }

    private void initializeChats() {
        currentChat = null;
        sendHBox.setVisible(false);
    }

    public void closeChat() {
        changeVisibility(open, chatPane);
    }

    public void openChat() {
        changeVisibility(chatPane, open);
        currentChat = getGetGlobalChat();
    }

    private void changeVisibility(Node node, Node... nodes) {
        node.setVisible(true);
        for (Node node1 : nodes) node1.setVisible(false);
    }

    public void refresh() {
        JSONArray jsonArray = connection.getJSONArrayData(chatController, "getChatMessages", currentChat.getName());
        switch (currentChat.getChatType()) {
            case GLOBAL -> ((VBox) globalChat.getContent()).getChildren().clear();
            case PRIVATE -> ((VBox) privateChat.getContent()).getChildren().clear();
            case CHAT_ROOM -> ((VBox) chatRoom.getContent()).getChildren().clear();
        }
        for (Object message : jsonArray) sendMessage(Parsers.parseMessageObject((JSONObject) message));
    }

    public static void updateChat() {
        Platform.runLater(() -> instance.refresh());
    }

    public void delete() {
        for (VBox selectedMessage : selectedMessages)
            connection.doInServer(chatController, "removeMessage", currentChat.getName(), getIdByVBox(selectedMessage));
        selectedMessages.clear();
        refresh();
    }

    public void edit() {
        System.out.println(selectedMessages.size());
        if (selectedMessages.size() == 1) {
            VBox vBox = selectedMessages.get(0);
            String string = ((Label) vBox.getChildren().get(0)).getText();
            messageContent.setText(string);
            sendButton.setOnMouseClicked(mouseEvent -> {
                connection.doInServer(chatController, "editMessage", currentChat.getName(), getIdByVBox(selectedMessages.get(0)), messageContent.getText());
                selectedMessages.clear();
                refresh();
                resetSendButton();
            });
        }
    }

    private void resetSendButton() {
        sendButton.setOnMouseClicked(mouseEvent -> send());
    }

    private String getIdByVBox(VBox selectedMessage) {
        HBox hBox = (HBox) selectedMessage.getChildren().get(1);
        return ((Label) hBox.getChildren().get(2)).getText();
    }

    private void find(ListView<String> listView, String field, String newText) {
        JSONArray jsonArray = connection.getJSONArrayData(chatController, "find" + field, newText);
        List<Object> objects = jsonArray.toList();
        List<String> usernames = new ArrayList<>();

        for (Object object : objects) usernames.add((String) object);

        listView.setItems(FXCollections.observableList(usernames));
    }

    public void usernameListViewClicked(MouseEvent mouseEvent) {
        ArrayList<String> selectedUsernames = new ArrayList<>(usernameListView.getSelectionModel().getSelectedItems());
        if (mouseEvent.getClickCount() == 2) setCurrentChat(privateChat, selectedUsernames, ChatType.PRIVATE, null);
    }

    public void roomListViewClicked(MouseEvent mouseEvent) {
        String selected = roomListView.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2)
            setCurrentChat(chatRoom, new ArrayList<>(Collections.singleton(selected)), ChatType.CHAT_ROOM, selected);
    }

    private void setCurrentChat(ScrollPane scrollPane, ArrayList<String> selectedUsernames, ChatType chatType, String chatName) {
        ((VBox) scrollPane.getContent()).getChildren().clear();
        if (chatName == null) currentChat = Parsers.parseChatObject(connection.getJSONData(chatController, "createChat",
                selectedUsernames, chatType));
        else currentChat = Parsers.parseChatObject(connection.getJSONData(chatController, "createChat",
                selectedUsernames, chatType, chatName));
        sendHBox.setVisible(true);
        refresh();
    }

    public void createChat() {
        Stage stage1 = new Stage();
        TextField textField = new TextField();
        textField.setPromptText("groupName");
        Button button = new Button("apply");
        VBox vBox = new VBox(usernameSearch, usernameListView, textField, button);
        usernameListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        button.setOnMouseClicked(mouseEvent -> {
            ArrayList<String> selectedUsernames = new ArrayList<>(usernameListView.getSelectionModel().getSelectedItems());
            stage1.close();
            setCurrentChat(chatRoom, selectedUsernames, ChatType.CHAT_ROOM, textField.getText());
            usernameListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        });
        stage1.setScene(new Scene(vBox));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }
}
