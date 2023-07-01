package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Parsers;
import model.chat.Chat;
import model.chat.GlobalChat;
import model.chat.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import webConnection.Client;
import webConnection.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Application {
    private static Stage stage;
    public ListView<String> usernameListView = new ListView<>();
    private final Connection connection = Client.getConnection();
    private final String chatController = "ChatController";
    private Chat currentChat;
    private VBox selected;
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
    private void initialize() throws IOException {
        initializeScrollPane(globalChat);
        initializeScrollPane(privateChat);
        initializeScrollPane(chatRoom);
        refresh();
        search.textProperty().addListener((observableValue, old, newText) -> {
            try {
                find(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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

    public void send() throws IOException {
        if (currentChat == null) currentChat = GlobalChat.getInstance();
        Message message = Parsers.parseMessageObject(connection.getJSONData(chatController,
                "sendMessage", messageContent.getText(), currentChat.getId(), currentChat.getChatType().name()));

        if (!message.getContent().isEmpty()) sendMessage(message);
    }

    private void sendMessage(Message message) {
        VBox vBox = message.toVBox();
        vBox.setOnMouseClicked(mouseEvent -> {
            changeOpacityVbox(vBox, 0.7, 1);
            if (vBox.getOpacity() == 0.7) selected = vBox;
        });

        if (currentChat.equals(GlobalChat.getInstance())) ((VBox) globalChat.getContent()).getChildren().add(vBox);
        ViewUtils.clearFields(messageContent);
    }

    private void changeOpacityVbox(VBox vBox, double v, int i) {
        if (vBox.getOpacity() == v) vBox.setOpacity(i);
        else vBox.setOpacity(v);
    }

    public void showGlobal() {
        changeVisibility(globalChat, privateChat, chatRoom);
        currentChat = GlobalChat.getInstance();
    }

    public void showPrivate() throws IOException {
        changeVisibility(privateChat, globalChat, chatRoom);
        find("");
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
        JSONArray jsonArray = connection.getJSONArrayData(chatController, "getChatMessages", currentChat.getId());
        ((VBox) globalChat.getContent()).getChildren().clear();
//        for (int i = jsonArray.length()-1; i > jsonArray.; i++) {
//
//        }
        for (Object message : jsonArray) sendMessage(Parsers.parseMessageObject((JSONObject) message));
    }

    public void delete() throws IOException {
        JSONArray jsonArray = connection.getJSONArrayData(chatController, "getChatMessages", currentChat.getId());
        for (Object message : jsonArray) {
            Message message1 = Parsers.parseMessageObject((JSONObject) message);
            if (message1.isSelected())
                connection.doInServer(chatController, "removeMessage", currentChat.getId(), message1.getId());
        }
        refresh();
    }

    public void edit(MouseEvent mouseEvent) {
    }

    private void find(String newText) throws IOException {
        JSONArray jsonArray = connection.getJSONArrayData(chatController, "findUsername", newText);
        List<Object> objects = jsonArray.toList();
        List<String> usernames = new ArrayList<>();

        for (Object object : objects) usernames.add((String) object);
        usernameListView.setItems(FXCollections.observableList(usernames));
    }
}
