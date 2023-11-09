module com.example.techworld {
    requires com.opencsv;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires spring.security.crypto;
    requires java.desktop;

    opens com.example.techworld to javafx.fxml;
    exports com.example.techworld;
    exports com.example.techworld.controller;
    opens com.example.techworld.controller to javafx.fxml;
    exports com.example.techworld.view;
    opens com.example.techworld.view to javafx.fxml;
}