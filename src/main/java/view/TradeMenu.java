package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TradeMenu extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        TradeMenu.stage = stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/TradeMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @FXML
    public void initialize(){
    }

    public void newTrade() throws Exception {
        new CreateTradeMenu().start(stage);
    }

    public void previousTrades() {
    }

    public void returnMarket() throws Exception {
        new MarketMenu().start(stage);
    }
}

