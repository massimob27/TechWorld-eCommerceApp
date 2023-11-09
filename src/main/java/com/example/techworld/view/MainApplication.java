package com.example.techworld.view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1550, 850);


        for (String style : List.of("css/custom.css", "css/normalTheme.css")) {
            String resource = Objects.requireNonNull(MainApplication.class.getResource(style)).toExternalForm();
            scene.getStylesheets().add(resource);
        }



        stage.setMinWidth(1500);
        stage.setMinHeight(930);
        stage.setTitle("TechWorld");
        stage.getIcons().add(new Image(
                new File("src/main/resources/com/example/techworld/view/images/techworld.png")
                        .toURI().toString()));

        stage.setScene(scene);

        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}