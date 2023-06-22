package view;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    public Slider foodRateSlider;
    public Slider taxRateSlider;
    public Slider fearRateSlider;

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
        setSliderValue();
        String[] factors = {"Food", "Tax", "Fear", "Religious", "Ale"};
        for (String factor : factors) setLabelValue(factor);
    }

    private void setLabelValue(String factor) throws Exception {
        int currentValue = GameMenuController.showFactor(factor);
        Label label = (Label) getClass().getField(factor.toLowerCase()).get(this);

        label.setText(factor + ": " + currentValue);
        if (currentValue > 0) label.setTextFill(Color.GREEN);
        else if (currentValue < 0) label.setTextFill(Color.RED);
        else label.setTextFill(Color.YELLOW);
    }

    private void setSliderValue() {
        foodRateSlider.setValue(GameMenuController.showFoodRate());
        taxRateSlider.setValue(GameMenuController.showTaxRate());
        fearRateSlider.setValue(GameMenuController.showFearRate());
    }

    public void save() {
        GameMenuController.changeFoodRate((int) foodRateSlider.getValue());
        GameMenuController.changeFearRate((int) fearRateSlider.getValue());
        GameMenuController.changeTaxRate((int) taxRateSlider.getValue());
    }
}
