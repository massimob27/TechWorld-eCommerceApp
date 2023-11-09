package com.example.techworld.view;


import com.example.techworld.controller.CartInstanceController;
import com.example.techworld.controller.CartPageController;
import com.example.techworld.controller.MainController;
import com.example.techworld.model.CartInstance;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;



public class CartInstanceView extends Pane {
    public CartInstanceView(MainController mainController, CartPageController cartPageController, CartInstance cartInstance){
        FXMLLoader loader = new FXMLLoader(CartInstanceView.class.getResource("cartInstance.fxml"));
        try {

            AnchorPane root = loader.load();
            CartInstanceController controller = loader.getController();
            controller.init(mainController, cartPageController, this, cartInstance);
            this.getChildren().add(root);

        }

        catch(Exception ignoredException) {
        }

    }
}





