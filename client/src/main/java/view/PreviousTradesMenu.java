package view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Trade;
import webConnection.Client;

import java.io.IOException;

public class PreviousTradesMenu extends Application {

    private static Stage stage;
    @FXML
    private TableView sentTrades;
    @FXML
    private TableView receivedTrades;
    @FXML
    private Button acceptButton;
    private Trade selectedTrade;

    @Override
    public void start(Stage stage) throws Exception {
        PreviousTradesMenu.stage = stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/PreviousTradesMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        sentTrades.setItems((ObservableList) Client.getConnection().getData("TradeMenuController","getSentTradesObservable"));
        addColumns(sentTrades);
        receivedTrades.setItems((ObservableList) Client.getConnection().getData("TradeMenuController","getReceivedTradesObservable"));
        addColumns(receivedTrades);
        receivedTrades.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        receivedTrades.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedTrade = (Trade) newSelection;
            try {
                acceptButton.setVisible(selectedTrade.isOpen());
            }catch (Exception e){
            }
        });
//        receivedTrades.setRowFactory(tv -> new TableRow() {
//            protected void updateItem(Trade trade, boolean b) {
//                super.updateItem(trade, b);
//                if(!trade.isSeen()) {
//                        setStyle("-fx-background-color: #ffd700;");
//                }
//            }
//        });
    }

    public void reinitializeReceivedTrades(){
        receivedTrades.getItems().clear();
        try {
            receivedTrades.setItems((ObservableList) Client.getConnection().getData("TradeMenuController","getReceivedTradesObservable"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addColumns(receivedTrades);
        receivedTrades.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        receivedTrades.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedTrade = (Trade) newSelection;
            acceptButton.setVisible(selectedTrade.isOpen());
        });
    }

    private void addColumns(TableView tableView) {
        if(tableView.equals(sentTrades))
            createColumn(tableView,"Receiver","receiverName");
        else {
            createColumn(tableView, "Sender", "senderName");
            createColumn(tableView,"Checked","seenStatus");
        }
        createColumn(tableView,"Resource","resourceName");
        createColumn(tableView,"Amount","resourceAmount");
        createColumn(tableView,"Price","price");
        createColumn(tableView,"Resource","resourceName");
        createColumn(tableView,"Message","senderMessage");
        createColumn(tableView,"Type","tradeType");
        createColumn(tableView,"Status","status");

    }

    private void createColumn(TableView tableView,String header,String field){
        TableColumn<Trade,String> tableColumn = new TableColumn<>(header);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(field));
        tableColumn.setSortable(false);
        tableView.getColumns().add(tableColumn);
    }

    public void back() throws Exception {
        new TradeMenu().start(stage);
    }

    public void showSentTrades() {
        sentTrades.setVisible(true);
        receivedTrades.setVisible(false);
    }

    public void showReceivedTrades() throws IOException {
        Client.getConnection().doInServer("TradeMenuController","seenNewTrades");
        receivedTrades.setVisible(true);
        sentTrades.setVisible(false);
    }

    public void acceptTrade() throws IOException {
        switch (Client.getConnection().checkAction("TradeMenuController","checkAcceptTrade","selectedTrade")){
            case NOT_ENOUGH_GOLD -> ViewUtils.alert(Alert.AlertType.ERROR,"Trade Error","Buyer doesn't have enough gold!");
            case NOT_ENOUGH_AMOUNT -> ViewUtils.alert(Alert.AlertType.ERROR,"Trade Error","Sender doesn't have enough resource!");
            case NOT_ENOUGH_STORAGE -> ViewUtils.alert(Alert.AlertType.ERROR,"Trade Error","Buyer doesn't have enough storage!");
            case SUCCESS -> {
                ViewUtils.alert(Alert.AlertType.INFORMATION,"Trade Successful","Trade Successfully done!");
                reinitializeReceivedTrades();
            }
        }
    }
}
