package com.example.techworld.view;
import com.example.techworld.controller.ArticlePageController;
import com.example.techworld.controller.MainController;
import com.example.techworld.model.Article;
import com.example.techworld.model.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ArticlePageView extends Pane {

    public ArticlePageView(MainController mainController, UserManager userManager, Article articolo){
        FXMLLoader loader = new FXMLLoader(ArticlePageView.class.getResource("articlePage.fxml"));
        try {

            AnchorPane root = loader.load();
            ArticlePageController controller = loader.getController();
            controller.init(mainController, userManager, articolo);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());

        }
        catch(Exception ignoredException) {

        }
    }






}
