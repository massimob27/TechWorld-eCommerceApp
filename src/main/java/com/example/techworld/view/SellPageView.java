package com.example.techworld.view;


import com.example.techworld.controller.MainController;
import com.example.techworld.controller.SellPageController;
import com.example.techworld.model.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SellPageView extends Pane {
    public SellPageView(MainController mainController, UserManager userControl){
        FXMLLoader loader = new FXMLLoader(LoginPageView.class.getResource("sellPage.fxml"));
        try {
            AnchorPane root = loader.load();
            SellPageController controller = loader.getController();
            controller.init(mainController, userControl);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        }
        catch(Exception ignoredException) {
        }
    }



}
