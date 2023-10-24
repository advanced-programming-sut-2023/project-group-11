//package view;
//
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Label;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.TilePane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import model.map.Tile;
//import model.people.Machine;
//import view.enums.Message;
//import webConnection.Client;
//
//import java.io.IOException;
//
//public class BuildMachine extends Application {
//
//    @FXML
//    private Label mainLabel;
//    @FXML
//    private Label detailLabel;
//    @FXML
//    private TilePane machinePane;
//    private static Tile tile;
//    private Message message;
//    public static Stage stage;
//
//    @FXML
//    public void initialize() {
//        for (Node node : machinePane.getChildren()) {
//            ImageView imageView = (ImageView) node;
//            imageView.setId(imageView.getId().replace("_", " "));
//            imageView.setOnMouseClicked(mouseEvent -> {
//                try {
//                    onMouseClicked(mouseEvent);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//    }
//
//    private void onMouseClicked(MouseEvent mouseEvent) throws IOException {
//        String machineType = ((ImageView) mouseEvent.getSource()).getId();
//        detailLabel.setText(new Machine(machineType).getPublicDetails());
//        message = Client.getConnection().checkAction("SelectUnitMenuController",
//                "checkBuildMachine", tile, machineType);
//        if (mouseEvent.getClickCount() == 2) {
//            switch (message) {
//                case NOT_ENOUGH_GOLD ->
//                        ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough gold");
//                case NOT_ENOUGH_ENGINEERS ->
//                        ViewUtils.alert(Alert.AlertType.ERROR, "Build Error", "You don't have enough engineers");
//                case SUCCESS -> stage.close();
//            }
//        }
//    }
//
//    public void setTile(Tile tile) {
//        BuildMachine.tile = tile;
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        BuildMachine.stage = stage;
//        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/BuildMachine.fxml"));
//        stage.setScene(new Scene(pane));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.show();
//    }
//
//}
