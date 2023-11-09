package com.example.techworld.controller;

import com.example.techworld.model.*;
import com.example.techworld.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainController {

    @FXML
    private Button cambiaTema;

    @FXML
    private Button catalogo;

    @FXML
    private Button informazioni;

    @FXML
    private Button vendere;

    @FXML
    private Button carrello;

    @FXML
    private Button profilo;
    @FXML
    private Label messaggioDiBenvenuto;

    @FXML
    private ScrollPane mainPage;




    UserManager userControl = new UserManager();
    DatabaseManager DBConnection = new DatabaseManager();
    ThemeManager themeManager = new ThemeManager();












    @FXML
    void goChangeTheme(ActionEvent event) {

        if (ThemeManager.getCurrentTheme().equals("NORMAL")) {

            String resource = Objects.requireNonNull(MainApplication.class.getResource("css/normalTheme.css")).toExternalForm();

            mainPage.getScene().getStylesheets().remove(resource);


            String resource2 = Objects.requireNonNull(MainApplication.class.getResource("css/blueTheme.css")).toExternalForm();

            mainPage.getScene().getStylesheets().add(resource2);

            themeManager.setCurrentTheme("BLUE");

        }

        else{

            String resource = Objects.requireNonNull(MainApplication.class.getResource("css/blueTheme.css")).toExternalForm();

            mainPage.getScene().getStylesheets().remove(resource);


            String resource2 = Objects.requireNonNull(MainApplication.class.getResource("css/normalTheme.css")).toExternalForm();

            mainPage.getScene().getStylesheets().add(resource2);

            themeManager.setCurrentTheme("NORMAL");


        }

    }














    @FXML
    void goInfo(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informazioni");
        alert.setHeaderText("Informazioni sull'applicazione");
        alert.setContentText("Hai accesso fin da subito al catalogo e agli articoli, ma verrai reindirizzato " +
                "alla schermata di login o registrazione ogni volta che farai un azione che necessita di un account.\n" +

                "\nSe sei il rappresentante di un azienda e vuoi sbloccare la funzionalità di vendita, basta accedere al " +
                "proprio account aziendale o crearne uno nuovo specificando l'email aziendale.");


        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(ImageSelector.alertIcon());


        alert.getDialogPane().setMinHeight(400);
        alert.getDialogPane().setMinWidth(400);


        alert.getDialogPane().setGraphic(new FontIconClass("mdi2i-information-outline", 40));

        DialogPane dialogPane = alert.getDialogPane();

        if (ThemeManager.getCurrentTheme().equals("NORMAL")) {

            for (String style : List.of("css/infoDialog.css", "css/alertNormalTheme.css")) {
                String resource = Objects.requireNonNull(MainApplication.class.getResource(style)).toExternalForm();
                dialogPane.getStylesheets().add(resource);
            }

        }

        else{

            for (String style : List.of("css/infoDialog.css", "css/alertBlueTheme.css")) {
                String resource = Objects.requireNonNull(MainApplication.class.getResource(style)).toExternalForm();
                dialogPane.getStylesheets().add(resource);
            }


        }


        Optional<ButtonType> result = alert.showAndWait();

    }








    void goRegisterOrLogin(){

        LoginPageView loginPageView = new LoginPageView(this);
        mainPage.setContent(loginPageView);

        mainPage.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        loginPageView.prefWidthProperty().bind(mainPage.widthProperty());
        loginPageView.prefHeightProperty().bind(mainPage.heightProperty());


    }

    void goCart(){

        if (mainPage.getContent().getClass() != CartPageView.class) {

            CartPageView cartPageView = new CartPageView(this, userControl);
            mainPage.setContent(cartPageView);

            mainPage.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            cartPageView.prefWidthProperty().bind(mainPage.widthProperty());
            cartPageView.minHeightProperty().bind(mainPage.heightProperty());

        }

        else{
            // ritorna sulla cima
            mainPage.setVvalue(0);
        }


    }




    @FXML
    void goCatalog(ActionEvent event) {

        if (mainPage.getContent().getClass() != DisplayPageView.class) {

            showCatalog("NORMAL", "");

        }

        else{
            // ritorna sulla cima
            mainPage.setVvalue(0);
        }

    }


    @FXML
    void goSell(ActionEvent event) {

        // NOT LOGGED
        if(!userControl.getUserIsLogged()){

            goRegisterOrLogin();

        }

        // LOGGED, BUSINESS
        else{

            SellPageView sellPageView = new SellPageView(this, userControl);
            mainPage.setContent(sellPageView);

            mainPage.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


            sellPageView.prefWidthProperty().bind(mainPage.widthProperty());
            sellPageView.prefHeightProperty().bind(mainPage.heightProperty());


        }



    }



    public void showCatalog(String type, String title){

        mainPage.setContent(null);
        DisplayPageView displayPageView = new DisplayPageView(this, type, title);
        mainPage.setContent(displayPageView);

        mainPage.setVvalue(0);
        mainPage.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        displayPageView.prefWidthProperty().bind(mainPage.widthProperty());
        displayPageView.minHeightProperty().bind(mainPage.heightProperty());


    }


    public void goToArticle(Article articolo){

        mainPage.setContent(null);
        ArticlePageView articlePageView = new ArticlePageView(this, userControl, articolo);
        mainPage.setContent(articlePageView);

        mainPage.setVvalue(0);
        mainPage.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        articlePageView.prefWidthProperty().bind(mainPage.widthProperty());
        articlePageView.minHeightProperty().bind(mainPage.heightProperty());




    }








    public void goLogin(User user){

        userControl.setUser(user);

        String messaggio = "Benvenuto " + user.username() + "!";

        messaggioDiBenvenuto.setText(messaggio);

        // Disattiva il pulsante di vendita se non è un venditore

        if(!user.venditore()){
            vendere.setDisable(true);
        }



        showCatalog("NORMAL", "");




    }


    public void goLogout(){

        userControl.logout();

        showCatalog("NORMAL", "");

        vendere.setDisable(false);

        messaggioDiBenvenuto.setText("");


    }









    @FXML
    void goCartCheck(ActionEvent event) {

        // NOT LOGGED

        if(!userControl.getUserIsLogged()){

            goRegisterOrLogin();

        }

        // LOGGED

        else{

            goCart();


        }

    }



    @FXML
    void goProfile(ActionEvent event) {

        // NOT LOGGED

        if(!userControl.getUserIsLogged()){

            goRegisterOrLogin();

        }

        // LOGGED

        else{

            ProfilePageView profilePageView = new ProfilePageView(this, userControl);
            mainPage.setContent(profilePageView);


            mainPage.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


            profilePageView.prefWidthProperty().bind(mainPage.widthProperty());
            profilePageView.prefHeightProperty().bind(mainPage.heightProperty());


        }

    }


    @FXML
    public void initialize() throws SQLException {
        DBConnection.createConnection();

        carrello.setGraphic((new FontIconClass("mdi2c-cart", 20)));
        profilo.setGraphic((new FontIconClass("mdi2a-account-circle", 20)));

        showCatalog("NORMAL","");


    }

}

