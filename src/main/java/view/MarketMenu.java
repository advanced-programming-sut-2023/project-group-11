package view;

import controller.MarketMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.AllResource;
import model.Stronghold;

public class MarketMenu extends Application {
    @FXML
    private HBox foodBox;
    @FXML
    private HBox weaponBox;
    @FXML
    private HBox rawMaterialBox;
    @FXML
    private Pane buySellPane;
    @FXML
    private TextField amountTextField;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Label itemLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label buyLabel;
    @FXML
    private Label sellLabel;
    @FXML
    private Label goldLabel;
    //TODO: update gold label & debug market in the game
    private AllResource item;
    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/MarketMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }
    @FXML
    public void initialize(){
        setMouseEvent(foodBox);
        setMouseEvent(rawMaterialBox);
        setMouseEvent(weaponBox);
        goldLabel.setText(String.valueOf(Stronghold.getCurrentGame().getCurrentGovernance().getGold()));
        amountTextField.textProperty().addListener((observable, oldText, newText)->{
            updateBuySellLabels(newText);
        });
    }

    private void updateBuySellLabels(String newText) {
        if(newText.length()>=4)
            amountTextField.setText(newText.substring(0,newText.length()-1));
        if(newText.isEmpty()){
            buyLabel.setText("");
            sellLabel.setText("");
        }else {
            try {
            int amount = Integer.parseInt(newText);
            buyLabel.setText(String.valueOf((amount * item.getPrice())));
            sellLabel.setText(String.valueOf((amount * item.getPrice() / 2)));
            }catch (Exception e){
                amountTextField.setText(newText.substring(0,newText.length()-1));
            }
        }
    }

    private void setMouseEvent(HBox box) {
        for(Node imageView:box.getChildren()){
            imageView.setOnMouseClicked(mouseEvent -> {
                buySellPane.setVisible(true);
                String itemName = ((ImageView)mouseEvent.getSource()).getId();
                item = MarketMenuController.getResourceByName(itemName);
                updateBuySellLabels(amountTextField.getText());
                itemLabel.setText(itemName);
                amountLabel.setText("amount: " + Stronghold.getCurrentGame().getCurrentGovernance().getResourceCount(item));
                itemImageView.setImage(item.getImage());
            });
        }
    }

    public void showRawBox() {
        rawMaterialBox.setVisible(true);
        weaponBox.setVisible(false);
        foodBox.setVisible(false);
    }

    public void showWeaponBox() {
        weaponBox.setVisible(true);
        rawMaterialBox.setVisible(false);
        foodBox.setVisible(false);
    }
    public void showFoodBox() {
        foodBox.setVisible(true);
        rawMaterialBox.setVisible(false);
        weaponBox.setVisible(false);
    }

    public void sell() {
        switch (MarketMenuController.checkSellItem(item,amountTextField.getText())){
            case NOT_ENOUGH_STORAGE -> ViewUtils.alert(Alert.AlertType.ERROR,
                    "Sell Error","You don't have enough of this item!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION,
                        "Sell Successful", "Item Sold Successfully!");
                goldLabel.setText(String.valueOf(Stronghold.getCurrentGame().getCurrentGovernance().getGold()));
                amountLabel.setText("amount: " + Stronghold.getCurrentGame().getCurrentGovernance().getResourceCount(item));
            }
        }
    }
    public void buy() {
        switch (MarketMenuController.checkBuyItem(item,amountTextField.getText())){
            case NOT_ENOUGH_STORAGE -> ViewUtils.alert(Alert.AlertType.ERROR,
                    "Buy Error","You don't have enough storage!");
            case NOT_ENOUGH_GOLD -> ViewUtils.alert(Alert.AlertType.ERROR,
                    "Buy Error","You don't have enough gold to buy this item!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION,
                        "Buy Successful", "Item bought successfully!");
                goldLabel.setText(String.valueOf(Stronghold.getCurrentGame().getCurrentGovernance().getGold()));
                amountLabel.setText("amount: " + Stronghold.getCurrentGame().getCurrentGovernance().getResourceCount(item));
            }
        }
    }

    public void tradeMenu() throws Exception {
        new TradeMenu().start(stage);
    }

    public void back() {
        stage.close();
    }
}
