package view;

import javafx.application.Application;
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
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends Application {
    private static Stage stage;
    public ListView<String> usernameListView = new ListView<>();
    private final Connection connection = Client.getConnection();
    private final String chatController = "ChatController";
    public HBox sendHBox;
    private Chat currentChat;
    private final LinkedList<VBox> selectedMessages = new LinkedList<>();
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
    private TextField search;
    @FXML
    private ImageView open;
    @FXML
    private Button sendButton;


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/MainMenu.fxml").toExternalForm()));
        MainMenu.stage = stage;
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    private void initialize() {
        initializeScrollPane(globalChat);
        initializeScrollPane(privateChat);
        initializeScrollPane(chatRoom);
        currentChat = getGetGlobalChat();
        refresh();
        search.textProperty().addListener((observableValue, old, newText) -> find(newText));
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
        new ProfileMenu().start(SignupMenu.getStage());
    }

    public void mapSelection() throws Exception {
        new MapSelection().start(new Stage());
    }

    public void logout() throws Exception {
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
        initializeChats();

        VBox vBox = (VBox) privateChat.getContent();
        vBox.getChildren().clear();
        vBox.getChildren().addAll(search, usernameListView);
        find("");
    }

    public void showChatRoom() {
        changeVisibility(chatRoom, globalChat, privateChat);
        initializeChats();
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
        ((VBox) globalChat.getContent()).getChildren().clear();
        for (Object message : jsonArray) sendMessage(Parsers.parseMessageObject((JSONObject) message));
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

    private void find(String newText) {
        JSONArray jsonArray = connection.getJSONArrayData(chatController, "findUsername", newText);
        List<Object> objects = jsonArray.toList();
        List<String> usernames = new ArrayList<>();
        List<HBox> hBoxes = new ArrayList<>();


        for (Object object : objects) usernames.add((String) object);

        usernameListView.setItems(FXCollections.observableList(usernames));
    }

    public void list(MouseEvent mouseEvent) {
        ArrayList<String> selectedUsernames = new ArrayList<>(usernameListView.getSelectionModel().getSelectedItems());
        if (mouseEvent.getClickCount() == 2) {
            if (privateChat.isVisible()) {
                setCurrentChat(selectedUsernames, ChatType.PRIVATE);
                ((VBox) privateChat.getContent()).getChildren().clear();
            } else setCurrentChat(selectedUsernames, ChatType.CHAT_ROOM);
        }
    }

    private void setCurrentChat(ArrayList<String> selectedUsernames, ChatType chatType) {
        currentChat = Parsers.parseChatObject(connection.getJSONData(chatController, "createChat",
                selectedUsernames, chatType));
        sendHBox.setVisible(true);
    }
}
