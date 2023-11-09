package com.example.techworld.controller;

import com.example.techworld.model.Article;
import com.example.techworld.model.DatabaseManager;
import com.example.techworld.model.UserManager;
import com.example.techworld.view.ImageSelector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class ArticlePageController {


    @FXML
    private Text articlePageDescription;

    @FXML
    private ImageView articlePageImage;

    @FXML
    private Label articlePagePrice;

    @FXML
    private Text articlePageTitle;

    @FXML
    private Button buttonCart;



    Article articolo;

    MainController mainPageController;

    UserManager userControl;

    DatabaseManager DBManager = new DatabaseManager();




    @FXML
    void addToCart(ActionEvent event) {

        if (userControl.getUserIsLogged()) {

            DBManager.addToCart(userControl.getUser().username(), articolo.nome_articolo());

            mainPageController.goCart();

        }

        else{
            mainPageController.goRegisterOrLogin();
        }


    }


    public void init(MainController mainController, UserManager userControl, Article articolo){

        this.articolo = articolo;

        this.userControl = userControl;

        mainPageController = mainController;


        ImageSelector imageSelector = new ImageSelector();

        articlePageImage.setImage(imageSelector.articleImage(articolo.nome_articolo()));

        articlePagePrice.setText("EUR "+ Double.toString(articolo.costo_singolo()));

        articlePageTitle.setText(articolo.nome_articolo());

        articlePageDescription.setText(articolo.descrizione());




    }





}


