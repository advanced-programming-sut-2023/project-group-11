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

    exports view;
    opens view to javafx.fxml;
    exports view.controller;
    opens view.controller to javafx.fxml;
    exports model;
    opens model to com.google.gson;
}