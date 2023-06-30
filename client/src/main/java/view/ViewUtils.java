package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Governance;
import model.User;
import webConnection.Client;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class ViewUtils {
//    private static GameMenu gameMenu;

    public static void fieldError(Label label, String error) {
        label.setText(error);
        label.setTextFill(Color.RED);
    }

    public static void clearLabels(Label... labels) {
        for (Label label : labels) {
            label.setText("");
        }
    }

    public static void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.setText("");
        }
    }


    public static boolean setEmptyError(TextField field, Label error) {
        if (field.getText().isEmpty()) {
            error.setText("must be filled!");
            return false;
        } else return error.getText().isEmpty() || error.getText().equals("Strong!");
    }

    public static Optional<ButtonType> alert(Alert.AlertType TYPE, String header, String content) {
        Alert alert = new Alert(TYPE);
        alert.setTitle(TYPE.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public static String reloadCaptcha(ImageView captchaImageView) throws URISyntaxException {
        File[] files = new File(SignupCompletion.class.getResource("/IMG/captcha").toURI()).listFiles();
        File captcha = files[new Random().nextInt(files.length)];
        String captchaNumber = captcha.getName().substring(0, 4);
        captchaImageView.setImage(new Image(captcha.getPath()));
        return captchaNumber;
    }

    static void livePasswordError(PasswordField newPassword, Label newPasswordError) {
        newPassword.textProperty().addListener((observable, oldText, newText) -> {
            int weakness = 0;
            try {
                weakness = (int) Client.getConnection().getData("SignupMenuController", "findHowWeakPasswordIs", newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (newText.isEmpty()) {
                newPasswordError.setText("");
            } else if (weakness == 0) {
                newPasswordError.setStyle("-fx-text-fill: green;");
                newPasswordError.setText("Strong!");
            } else if (weakness <= 2) {
                newPasswordError.setStyle("-fx-text-fill: orange;");
                newPasswordError.setText("Weak!");
            } else {
                newPasswordError.setStyle("-fx-text-fill: red;");
                newPasswordError.setText("Very weak!");
            }
        });
    }

//    public static ObservableList<User> getUsersObservable() throws IOException {
//        ArrayList<User> users = Stronghold.getUsers();
//        Client.getConnection().doInServer("Utils", "sortUsers");
//        return FXCollections.observableArrayList(users);
//    }
//
//    public static ObservableList<Governance> getGovernancesObservable(ArrayList<Object> parameters) {
//        ArrayList<Governance> governances = Stronghold.getCurrentGame().getGovernances();
//        return FXCollections.observableArrayList(governances);
//    }
//
//    public static void columnMaker(TableView tableView, String header, String userField) {
//        TableColumn<User, String> tableColumn = new TableColumn<>(header);
//        tableColumn.setCellValueFactory(new PropertyValueFactory<>(userField));
//        tableColumn.setSortable(false);
//        tableView.getColumns().add(tableColumn);
//    }
//
//    public static GameMenu getGameMenu() {
//        return gameMenu;
//    }
//
//    public static void setGameMenu(GameMenu gameMenu) {
//        ViewUtils.gameMenu = gameMenu;
//    }
}
