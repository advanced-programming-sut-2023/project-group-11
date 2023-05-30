module ProjectAP {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires JColor;
    requires json.simple;
    requires passay;
    requires org.apache.commons.codec;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.annotation;
    requires javafx.swing;

    exports view;
    opens view to javafx.fxml;
    opens model to com.google.gson, javafx.base;
}