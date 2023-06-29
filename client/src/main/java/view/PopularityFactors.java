package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import webConnection.Client;
import webConnection.Connection;

import java.io.IOException;
import java.net.URL;

public class PopularityFactors extends Application {
    private static Stage stage;
    public Label food;
    public Label tax;
    public Label fear;
    public Label religious;
    public Label ale;
    public Label total;
    public Label sickness;
    public Slider foodRateSlider;
    public Slider taxRateSlider;
    public Slider fearRateSlider;
    public ImageView foodFace;
    public ImageView taxFace;
    public ImageView fearFace;
    public ImageView religiousFace;
    public ImageView aleFace;
    public ImageView totalFace;
    public ImageView sicknessFace;
    private Connection connection = Client.getConnection();
    private String gameMenuController = "GameMenuController";

    @Override
    public void start(Stage stage) throws Exception {
        PopularityFactors.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/PopularityFactors.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void initialize() throws Exception {
        initializeSliders();
        String[] factors = {"Food", "Tax", "Fear", "Religious", "Ale", "Sickness", "Total"};
        for (String factor : factors)
            setLabelValue(factor, (int) connection.getData(gameMenuController, "showFactor", factor));
    }

    private void setLabelValue(String factor, int currentValue) throws Exception {
        Label label = (Label) getClass().getField(factor.toLowerCase()).get(this);
        ImageView imageView = (ImageView) getClass().getField(factor.toLowerCase() + "Face").get(this);

        label.setText(String.valueOf(currentValue));
        if (currentValue > 0) setColor(imageView, label, Color.GREEN, "Green");
        else if (currentValue < 0) setColor(imageView, label, Color.RED, "Red");
        else setColor(imageView, label, Color.YELLOW, "Yellow");
    }

    private void setColor(ImageView imageView, Label label, Color color, String colorName) {
        label.setTextFill(color);
        imageView.setImage(new Image(System.getProperty("user.dir") +
                "/src/main/resources/IMG/PopularityFactorsMenu/" + colorName + "Face.PNG"));
    }

    private void initializeSliders() throws IOException {
        foodRateSlider.setDisable(!((Boolean) connection.getData(gameMenuController, "hasFood")));
        addListener(foodRateSlider, "Food");
        addListener(taxRateSlider, "Tax");
        addListener(fearRateSlider, "Fear");
        foodRateSlider.setValue((Double) connection.getData(gameMenuController, "showFoodRate"));
        taxRateSlider.setValue((Double) connection.getData(gameMenuController, "showTaxRate"));
        fearRateSlider.setValue((Double) connection.getData(gameMenuController, "showFearRate"));
    }

    private void addListener(Slider slider, String factor) {
        slider.valueProperty().addListener((observable, old, newValue) -> {
            try {
                connection.doInServer(gameMenuController, "changeRate", factor, newValue.intValue());
                setLabelValue(factor, (Integer) connection.getData(gameMenuController, "showFactor", factor));
                setLabelValue("Total", (Integer) connection.getData(gameMenuController, "showFactor", "Total"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
