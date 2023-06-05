module ProjectAP {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.swing;
    requires com.google.gson;
    requires JColor;
    requires json.simple;
    requires passay;
    requires org.apache.commons.codec;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.annotation;

    exports view;
    exports model.map;
    opens view to javafx.fxml;
    opens model to com.google.gson, javafx.base;
}