module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires java.desktop;
    requires gson;
    requires org.junit.jupiter.api;
    requires org.mockito;
    requires org.mockito.junit.jupiter;
    requires junit;
    requires org.testng;


    exports Enum;
    exports view;
    exports controller;
    opens controller to javafx.fxml;
    opens view to javafx.fxml;
    exports model;
    opens model to gson;
    opens model.buildings to gson;
    opens model.people to gson;
    opens model.structures to gson;

}