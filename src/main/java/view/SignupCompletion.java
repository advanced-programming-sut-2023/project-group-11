package view;

import controller.SignupMenuController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class SignupCompletion extends Application {

    @FXML
    private ChoiceBox questionBox;
    @FXML
    private TextField answerField;
    @FXML
    private TextField captchaField;
    @FXML
    private Label answerError;
    @FXML
    private Label captchaError;
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

    ObservableList<String> questions = FXCollections.observableArrayList(SignupMenuController.getRecoveryQuestions());

    public void setVariables(String username, String password, String email, String nickname, String slogan) {
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
        questionBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                chosenQuestion = ((String) questionBox.getValue());
            }
        });
    }

    public void finishSignup() throws URISyntaxException {
        if (!answerField.getText().isEmpty()) {
            if (!captchaField.getText().equals(captchaNumber)) {
                captchaError.setText("wrong captcha");
                reloadCaptcha();
                return;
            }
            SignupMenuController.createUser(username, password, email, nickname, slogan, chosenQuestion, answerField.getText());
            stage.close();
            ViewUtils.alert(Alert.AlertType.INFORMATION, "Congratulation!", "User created successfully!");
        } else {
            answerError.setText("must be filled!");
        }

    }

    public void reloadCaptcha() throws URISyntaxException {
        captchaNumber = ViewUtils.reloadCaptcha(captchaNumber, captchaImageView);
//        File[] files = new File(SignupCompletion.class.getResource("/IMG/captcha").toURI()).listFiles();
//        File captcha = files[new Random().nextInt(files.length)];
//        captchaNumber = captcha.getName().substring(0, 4);
//        captchaImageView.setImage(new Image(captcha.getPath()));
    }
}
