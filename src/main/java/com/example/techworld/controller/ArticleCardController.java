package com.example.techworld.controller;

import com.example.techworld.model.Article;
import com.example.techworld.view.ImageSelector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ArticleCardController {



    @FXML
    private ImageView articleImage;

    @FXML
    private Label articlePrice;

    @FXML
    private Label articleTitle;






    Article articolo;

    MainController mainPageController;


    public void init(MainController mainController, Article articolo){

        this.articolo = articolo;
        mainPageController = mainController;

        ImageSelector imageSelector = new ImageSelector();




        articleImage.setImage(imageSelector.articleImage(articolo.nome_articolo()));

        articlePrice.setText(Double.toString(articolo.costo_singolo())+"€");

        articleTitle.setText(articolo.nome_articolo());



        articleImage.setOnMousePressed(event -> {

            mainPageController.goToArticle(articolo);

        });


        articleTitle.setOnMousePressed(event -> {

            mainPageController.goToArticle(articolo);

        });






    }




}
