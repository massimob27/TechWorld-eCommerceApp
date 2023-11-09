package com.example.techworld.view;

import com.example.techworld.controller.ArticleCardController;
import com.example.techworld.controller.MainController;
import com.example.techworld.model.Article;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ArticleCardView extends Pane {


    public ArticleCardView(MainController mainController, Article articolo){
        FXMLLoader loader = new FXMLLoader(ArticleCardView.class.getResource("articleCard.fxml"));
        try {

            AnchorPane root = loader.load();
            ArticleCardController controller = loader.getController();
            controller.init(mainController, articolo);
            this.getChildren().add(root);

        }
        catch(Exception ignoredException) {
        }
    }






}
