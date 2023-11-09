package com.example.techworld.view;


import com.example.techworld.controller.OrderInstanceController;
import com.example.techworld.model.Ordine;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class OrderInstanceView extends Pane {
    public OrderInstanceView(Ordine ordine){
        FXMLLoader loader = new FXMLLoader(OrderInstanceView.class.getResource("orderInstance.fxml"));
        try {

            AnchorPane root = loader.load();
            OrderInstanceController controller = loader.getController();
            controller.init(ordine);
            this.getChildren().add(root);

        }

        catch(Exception ignoredException) {
        }

    }


}
