package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Parsers;
import model.chat.Chat;
import model.chat.GlobalChat;
import model.chat.Message;
import model.chat.PrivateChat;
import org.json.JSONArray;
import org.json.JSONObject;
import webConnection.Client;

import java.io.IOException;
import java.net.URL;

public class MainMenu extends Application {
    private static Stage stage;
    private Chat currentChat;
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
    private ImageView open;

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
        Client.getConnection().doInServer("MainMenuController", "logout");
        new SignupMenu().start(SignupMenu.getStage());
    }

    public void lobby() {
    }

    public void send() throws IOException {
        if (currentChat == null) currentChat = GlobalChat.getInstance();
        Message message = Parsers.parseMessageObject(Client.getConnection().getJSONData("ChatController",
                "sendMessage", messageContent.getText(), currentChat.getId(), currentChat.getChatType().name()));

        if (!message.getContent().isEmpty()) sendMessage(message);
    }

    private void sendMessage(Message message) {
        VBox vBox = message.toVBox();
        if (currentChat.equals(GlobalChat.getInstance())) ((VBox) globalChat.getContent()).getChildren().add(vBox);
        ViewUtils.clearFields(messageContent);
    }

    public void showGlobal() {
        changeVisibility(globalChat, privateChat, chatRoom);
        currentChat = GlobalChat.getInstance();
    }

    public void showPrivate() {
        changeVisibility(privateChat, globalChat, chatRoom);
    }

    public void showChatRoom() {
        changeVisibility(chatRoom, globalChat, privateChat);
    }

    public void closeChat() {
        changeVisibility(open, chatPane);
    }

    public void openChat() {
        changeVisibility(chatPane, open);
    }

    private void changeVisibility(Node node, Node... nodes) {
        node.setVisible(true);
        for (Node node1 : nodes) node1.setVisible(false);
    }

    public void refresh() throws IOException {
        if (currentChat == null) currentChat = GlobalChat.getInstance();
        JSONArray jsonArray = Client.getConnection().getJSONArrayData("ChatController", "getChatMessages", currentChat.getId());
        ((VBox) globalChat.getContent()).getChildren().clear();
        for (Object message : jsonArray) sendMessage(Parsers.parseMessageObject((JSONObject) message));
    }
}
