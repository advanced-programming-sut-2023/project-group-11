package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.Client;
import net.Server;


public class EntryMenu extends Application {

    private static Stage stage;
    public VBox mainBox;
    public Label label;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        EntryMenu.stage = stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/EntryMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    public void createServer() {

        mainBox.setVisible(false);
        label.setVisible(true);
        if(Server.getServer() == null)
            new Server().start();
    }

    public void createClient() throws Exception {
        Client client = Client.getClient("localhost");
        client.start();
        client.sendData("Connection request".getBytes());
        System.out.println(client.getIpAddress());
        new SignupMenu().start(stage);
    }
}
