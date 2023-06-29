package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import webConnection.Client;

import java.io.IOException;
import java.net.URISyntaxException;

public class SignupCompletion extends Application {

    @FXML
    private ChoiceBox<String> questionBox;
    @FXML
    private TextField answerField;
    @FXML
    private TextField captchaField;
    @FXML
    private Label answerError;
    @FXML
    private Label captchaError;
    @FXML
    private Label recoveryQuestionError;
    @FXML
    private ImageView captchaImageView;
    public static Stage stage;

    private static String chosenQuestion;
    private static String captchaNumber;
    private static String username;
    private static String password;
    private static String email;
    private static String nickname;
    private static String slogan;
    ObservableList<String> questions;

    {
        try {
            questions = FXCollections.observableArrayList(Client.getConnection().getArrayData("SignupMenuController","getRecoveryQuestions"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setVariables(String username, String password, String email, String nickname, String slogan) {
//        Client.getConnection().getArrayData("SignupMenuController","getRecoveryQuestions");
        SignupCompletion.username = username;
        SignupCompletion.password = password;
        SignupCompletion.email = email;
        SignupCompletion.nickname = nickname;
        SignupCompletion.slogan = slogan;
    }

    @Override
    public void start(Stage stage) throws Exception {
        SignupCompletion.stage = stage;
        stage.setTitle("Signup completion");
        Pane pane = FXMLLoader.load(SignupCompletion.class.getResource("/FXML/SignupCompletion.fxml"));
        stage.setScene(new Scene(pane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void initialize() throws URISyntaxException {
        initializeQuestionBox();
        reloadCaptcha();
    }

    private void initializeQuestionBox() {
        questionBox.setValue("Pick a Question");
        questionBox.setItems(questions);
        questionBox.valueProperty().addListener((observableValue, o, t1) -> chosenQuestion = questionBox.getValue());
    }

    public void finishSignup() throws URISyntaxException, IOException {
        if (answerField.getText().isEmpty()) answerError.setText("must be filled!");
        else if (!captchaField.getText().equals(captchaNumber)) {
            captchaError.setText("wrong captcha");
            reloadCaptcha();
        } else if (chosenQuestion == null) recoveryQuestionError.setText("must be chosen!");
        else {
            Client.getConnection().doInServer("SignupMenuController","createUser",username, password, email, nickname, slogan, chosenQuestion, answerField.getText());
            stage.close();
            ViewUtils.alert(Alert.AlertType.INFORMATION, "Congratulation!", "User created successfully!");
        }
    }

    public void reloadCaptcha() throws URISyntaxException {
        captchaNumber = ViewUtils.reloadCaptcha(captchaImageView);
    }
}
