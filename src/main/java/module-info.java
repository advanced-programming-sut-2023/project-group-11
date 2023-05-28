module ProjectAP {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires org.apache.commons.codec;
    requires passay;
    requires JColor;
    requires org.apache.commons.lang3;
    requires json.simple;
    requires com.fasterxml.jackson.annotation;


    exports view;
    opens view to javafx.fxml;
}