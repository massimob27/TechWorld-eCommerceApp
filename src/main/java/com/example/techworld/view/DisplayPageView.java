package com.example.techworld.view;

import com.example.techworld.controller.DisplayPageController;
import com.example.techworld.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DisplayPageView extends Pane {

    public DisplayPageView(MainController mainController, String type, String title){
        FXMLLoader loader = new FXMLLoader(DisplayPageView.class.getResource("displayPage.fxml"));
        try {
            AnchorPane root = loader.load();
            DisplayPageController controller = loader.getController();
            controller.init(mainController, type, title);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        }
        catch(Exception ignoredException) {
        }
    }

}
