package view;

import controller.EntryMenuController;
import controller.MainMenuController;
import controller.TradeMenuController;
import controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.AllResource;
import model.Governance;
import model.User;

import java.util.ArrayList;

public class CreateTradeMenu extends Application {

    private static Stage stage;
    private AllResource selectedItem;
    private Governance selectedGovernance;
    @FXML
    private TilePane resourcePane;
    @FXML
    private TableView governances;
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
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextArea messageField;

    @Override
    public void start(Stage stage) throws Exception {
        CreateTradeMenu.stage = stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/CreateTradeMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @FXML
    public void initialize(){
        initializeResourcePane();
        initializeUsers();
        amountTextField.textProperty().addListener((observable, oldText, newText)->{
            checkTextFields(amountTextField,newText);
        });
        priceTextField.textProperty().addListener((observable, oldText, newText)->{
            checkTextFields(priceTextField,newText);
        });

    }

    private void checkTextFields(TextField field,String newText) {
        if(newText.isEmpty())
            return;
        if(newText.length()>=4)
            field.setText(newText.substring(0,newText.length()-1));
        try {
            int amount = Integer.parseInt(newText);
        }catch (Exception e){
            field.setText(newText.substring(0,newText.length()-1));
        }
    }

    private void initializeUsers() {
        governances.setItems(MainMenuController.removeCurrentGovernanceFromList(Utils.getGovernancesObservable()));
        addColumns();
        //TODO: debug in game + gameCurrentGovernance
        governances.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        governances.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedGovernance = (Governance) newSelection;
            tradeWithLabel.setText("Trade with: \n" + (selectedGovernance).getOwner().getNickname());
            //TODO: amount label update for governance
        });
    }
    private void addColumns() {
        TableColumn<Governance, String> tableColumn = new TableColumn<>("Avatar");
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        tableColumn.setSortable(false);
        governances.getColumns().add(tableColumn);
        tableColumn = new TableColumn<>("Name");
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tableColumn.setSortable(false);
        governances.getColumns().add(tableColumn);
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
        int amount = Integer.parseInt(amountTextField.getText());
        switch (((Button)mouseEvent.getSource()).getText()){
            case "+"->{
                if(amount<999)
                    amountTextField.setText(String.valueOf(++amount));
            }
            case "-"->{
                if(amount>0)
                    amountTextField.setText(String.valueOf(--amount));
            }
        }
    }

    public void checkTrade(MouseEvent mouseEvent) {
        try {
            if(selectedGovernance == null)
                throw new Exception();
            int amount = Integer.parseInt(String.valueOf(amountTextField.getText()));
            int price = Integer.parseInt(String.valueOf(priceTextField.getText()));
            String tradeType = ((Button) mouseEvent.getSource()).getText();
            TradeMenuController.checkTrade(selectedItem, amount, price, messageField.getText(), tradeType, selectedGovernance);
            ViewUtils.alert(Alert.AlertType.INFORMATION,"Trade Successful","Trade created successfully!");
        }catch (Exception e){
            ViewUtils.alert(Alert.AlertType.ERROR,"Trade Error","Select governance & fill fields!");
        }
    }

    public void back() throws Exception {
        new TradeMenu().start(stage);
    }
}
