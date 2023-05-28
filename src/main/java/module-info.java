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
    requires org.apache.commons.codec;
    requires passay;
    requires JColor;
    requires org.apache.commons.lang3;
    requires json.simple;
    requires com.fasterxml.jackson.annotation;

    exports view;
    opens view to javafx.fxml;
    exports view.controller;
    opens view.controller to javafx.fxml;
    exports model;
    opens model to com.google.gson;
}