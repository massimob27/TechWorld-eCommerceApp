package com.example.techworld.view;

import com.example.techworld.controller.CartPageController;
import com.example.techworld.controller.MainController;
import com.example.techworld.model.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CartPageView extends Pane {
    public CartPageView(MainController mainController, UserManager userManager){
        FXMLLoader loader = new FXMLLoader(CartPageView.class.getResource("cartPage.fxml"));
        try {

            AnchorPane root = loader.load();
            CartPageController controller = loader.getController();
            controller.init(mainController, userManager);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());

        }
        catch(Exception ignoredException) {

        }
    }


}
