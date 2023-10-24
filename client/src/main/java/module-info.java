module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.swing;
    requires JColor;
    requires json.simple;
    requires passay;
    requires org.apache.commons.codec;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.annotation;
    requires org.json;

    exports view;
    exports model;
    exports model.chat to com.google.gson;
    opens view to javafx.fxml;
    opens model to com.google.gson, javafx.base;
    opens model.map to com.google.gson;
    opens model.buildings to com.google.gson;
    opens model.people to com.google.gson;
    opens webConnection to com.google.gson;
    exports model.people.enums to com.google.gson;
//    exports view.animation;
//    opens view.animation to javafx.fxml;
}