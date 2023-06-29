module server {
    requires com.google.gson;
    requires json.simple;
    requires passay;
    requires org.apache.commons.codec;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.annotation;
    requires org.json;
    requires javafx.base;

    opens model to com.google.gson, javafx.base;
    opens model.map to com.google.gson;
    opens model.buildings to com.google.gson;
    opens model.people to com.google.gson;
    exports model.people.enums to com.google.gson;
}