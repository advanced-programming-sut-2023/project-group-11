package view;

import controller.EntryMenuController;
import controller.MainMenuController;
import controller.TradeMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.AllResource;
import model.User;

import java.util.ArrayList;

public class CreateTradeMenu extends Application {

    private static Stage stage;
    private AllResource selectedItem;
    @FXML
    private TilePane resourcePane;
    @FXML
    private TableView users;
    @FXML
    private HBox tradeBox;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Label itemLabel;
    @FXML
    private Label tradeWithLabel;
    @FXML
    private Label amountLabel;

    @Override
    public void start(Stage stage) throws Exception {
        CreateTradeMenu.stage = stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/CreateTradeMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @FXML
    public void initialize(){
        EntryMenuController.fillAllFieldsWithPreviousData();
        initializeResourcePane();
        initializeUsers();
    }

    private void initializeUsers() {
        users.setItems(MainMenuController.removeCurrentUserFromList(Utils.getUsersObservable()));
        addColumns();
        //TODO: debug in game + gameCurrentGovernance
        users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        users.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            User selectedUser = (User)newSelection;
            tradeWithLabel.setText("Trade with: \n" + (selectedUser).getUsername());
            //TODO: amount label update for governance
        });
    }
    private void addColumns() {
        Utils.columnMaker(users, "Avatar", "avatar");
        Utils.columnMaker(users, "Username", "username");
    }

    private void initializeResourcePane() {
        ArrayList<AllResource> allResources = TradeMenuController.getAllResources();
        for (int i =0;i< allResources.size()-1;i++){
            AllResource resource = allResources.get(i);
            ImageView item = ((ImageView)resourcePane.getChildren().get(i));
            item.setImage(resource.getImage());
            item.setId(resource.getName());
            item.setOnMouseClicked(this::handle);
        }
    }

    private void handle(MouseEvent mouseEvent) {
        selectedItem = AllResource.getAllResourceByName(((ImageView)mouseEvent.getSource()).getId());
        itemImageView.setImage(selectedItem.getImage());
        itemLabel.setText(selectedItem.getName());
        tradeBox.setVisible(true);
    }

    public void amountChange(MouseEvent mouseEvent) {
        switch (((Button)mouseEvent.getSource()).getText()){
            case "+"->{

            }
            case "-"->{

            }
        }
    }
}
