module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires java.desktop;
//    requires gson;
    requires org.junit.jupiter.api;
    requires org.mockito;
    requires org.mockito.junit.jupiter;
    requires junit;
    requires org.testng;
    requires com.google.gson;


    exports Enum;
    exports view;
    exports controller;
    opens controller to javafx.fxml;
    opens view to javafx.fxml;
    exports model;
    opens model to com.google.gson;
    opens model.buildings to com.google.gson;
    opens model.people to com.google.gson;
    opens model.structures to com.google.gson;

}