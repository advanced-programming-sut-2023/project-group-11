package view;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    @Override
    public void start(Stage stage) throws Exception {
        PopularityFactors.stage = stage;
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/PopularityFactors.fxml").toExternalForm()));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();
    }

    public void initialize() throws Exception {
        initializeSliders();
        String[] factors = {"Food", "Tax", "Fear", "Religious", "Ale", "Sickness", "Total"};
        for (String factor : factors) setLabelValue(factor, GameMenuController.showFactor(factor));
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

    private void initializeSliders() {
        foodRateSlider.setDisable(!GameMenuController.hasFood());
        addListener(foodRateSlider, "Food");
        addListener(taxRateSlider, "Tax");
        addListener(fearRateSlider, "Fear");
        foodRateSlider.setValue(GameMenuController.showFoodRate());
        taxRateSlider.setValue(GameMenuController.showTaxRate());
        fearRateSlider.setValue(GameMenuController.showFearRate());
    }

    private void addListener(Slider slider, String factor) {
        slider.valueProperty().addListener((observable, old, newValue) -> {
            try {
                GameMenuController.changeRate(factor, newValue.intValue());
                setLabelValue(factor, GameMenuController.showFactor(factor));
                setLabelValue("Total", GameMenuController.showFactor("Total"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
