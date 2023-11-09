package com.example.techworld.view;

import com.example.techworld.controller.LoginPageController;
import com.example.techworld.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class LoginPageView extends Pane {

    public LoginPageView(MainController mainController){
        FXMLLoader loader = new FXMLLoader(LoginPageView.class.getResource("loginPage.fxml"));
        try {
            AnchorPane root = loader.load();
            LoginPageController controller = loader.getController();
            controller.init(mainController);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        }
        catch(Exception ignoredException) {
        }
    }



}

