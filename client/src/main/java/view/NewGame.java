package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Game;
import model.Parsers;
import webConnection.Client;
import webConnection.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class NewGame extends Application {
    private static Stage stage;
    @FXML
    private TableView<Game> games;
    @FXML
    private ChoiceBox<String> mapName;
    @FXML
    private TextField playersNeededField;
    private final Connection connection = Client.getConnection();
    private final String utils = "Utils";

    @Override
    public void start(Stage stage) throws Exception {
        NewGame.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/NewGame.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        initializeMapNames();
        initializeGames();
    }

    private void initializeGames() throws IOException {
        String gamesJson = Client.getConnection().receiveJsonData("MainMenuController","getUnStartedGames");
        games.getItems().addAll(Parsers.parseGamesArrayList(gamesJson));
        addColumns();
                //TODO: check this later
//        games.setItems(MainMenuController.removeCurrentUserFromList(Utils.getUsersObservable()));
        games.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void initializeMapNames() throws IOException {
        ArrayList<String> mapNames =  Client.getConnection().getArrayData("MapEditMenuController","getMapNames");
        mapName.getItems().addAll(mapNames);
        mapName.setValue(mapNames.get(0));
//        mapName.setValue(Stronghold.getMapByName("original"));
//        ShowMapMenuController.setCurrentMap(Stronghold.getMapByName("original"));
//        currentMap = Stronghold.getMapByName("original");
//        mapName.setOnAction(actionEvent -> {
//            ShowMapMenuController.setCurrentMap(mapName.getValue());
//            MapEditMenuController.setCurrentMap(mapName.getValue());
//        });
//        connection.doInServer("MapEditMenuController", "setMapsOnChoiceBox", mapName);
    }

    private void addColumns() throws IOException {
        columnMaker(games,"Owner","ownerName");
        columnMaker(games,"JoinedPlayers","joinedPlayers");
    }

    private void columnMaker(TableView tableView,String header,String field){
        TableColumn<Game, String> tableColumn = new TableColumn<>(header);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(field));
        tableColumn.setSortable(false);
        tableView.getColumns().add(tableColumn);
    }

    public void startGame() throws Exception {
        Client.getConnection().doInServer("MainMenuController","createGame",mapName.getValue(),Integer.parseInt(playersNeededField.getText()));
//        if (games.getSelectionModel().getSelectedItems().size() >= 1) {
//            connection.doInServer("MainMenuController", "startGame",
////                    new ArrayList<>(games.getSelectionModel().getSelectedItems()), mapName.getValue().getName());
////            new GameMenu().start(SignupMenu.getStage());
////            stage.close();
//        } else ViewUtils.alert(Alert.AlertType.ERROR, "Start Game", "Please choose a player!");
    }

    public void back() throws Exception {
        stage.close();
        new MainMenu().start(SignupMenu.getStage());
    }
}
