package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.enums.Message;
import webConnection.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ShareMapMenu extends Application {
    private static Stage stage;
    public ChoiceBox<String> users;
    public ChoiceBox<String> maps;

    @Override
    public void start(Stage stage) throws Exception {
        ShareMapMenu.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(MainMenu.class.getResource("/FXML/ShareMap.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        setChoiceBoxes();
    }

    private void setChoiceBoxes() throws IOException {
        ArrayList<String> mapNames = Client.getConnection().getArrayData("MapEditMenuController","getMapNames");
        maps.getItems().addAll(mapNames);
        if(mapNames.size() > 0)
            maps.setValue(mapNames.get(0));
        ArrayList<String> usernames = Client.getConnection().getArrayData("MapEditMenuController","getUserNames");
        users.getItems().addAll(usernames);
        if(usernames.size() > 0)
            users.setValue(usernames.get(0));
    }

    public void share() throws IOException {
        if (maps.getValue() == null)
            ViewUtils.alert(Alert.AlertType.ERROR, "Sharing Map", "Select a map please!");
        else if (users.getValue() == null)
            ViewUtils.alert(Alert.AlertType.ERROR, "Sharing Map", "Select a user please!");
        else if (Client.getConnection().checkAction("MapEditMenuController", "checkShareMap",
                getMapName(maps.getValue()), users.getValue()).equals(Message.USER_HAS_MAP))
            ViewUtils.alert(Alert.AlertType.ERROR, "Sharing Map", "User has the map!");
        else ViewUtils.alert(Alert.AlertType.INFORMATION, "Sharing Map", "Map shared successfully");
    }

    private String getMapName(String value) {
        if (value.matches("[^-]+")) return value;
        int index = value.indexOf('-') - 1;
        return value.substring(0, index);
    }

    public void back() throws Exception {
        new MapSelection().start(stage);
    }
}
