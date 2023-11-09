package com.example.techworld.view;


import com.example.techworld.controller.MainController;
import com.example.techworld.controller.ProfilePageController;
import com.example.techworld.model.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ProfilePageView extends Pane {
    public ProfilePageView(MainController mainController, UserManager userControl){
        FXMLLoader loader = new FXMLLoader(ProfilePageView.class.getResource("profilePage.fxml"));
        try {
            AnchorPane root = loader.load();
            ProfilePageController controller = loader.getController();
            controller.init(mainController, userControl);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        }
        catch(Exception ignoredException) {
        }
    }





}
