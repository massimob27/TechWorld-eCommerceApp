
package com.example.techworld.controller;


import com.example.techworld.model.*;
import com.example.techworld.view.CartInstanceView;
import com.example.techworld.view.ImageSelector;
import com.example.techworld.view.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CartInstanceController {

    @FXML
    private ImageView cartInstanceImage;

    @FXML
    private Button cartInstanceMinus;

    @FXML
    private Button cartInstancePlus;

    @FXML
    private Label cartInstancePrice;

    @FXML
    private Label cartInstanceRemove;

    @FXML
    private Label cartInstanceTitle;

    @FXML
    private Label cartInstanceQuantity;

    @FXML
    private AnchorPane cartInstancePane;

    @FXML
    private Button buySingleArticleButton;




    CartInstance cartInstance;

    Article articolo;

    CartPageController cartPageController;

    MainController mainPageController;

    CartInstanceView cartInstanceView;

    DatabaseManager DBManager = new DatabaseManager();










    @FXML
    void addToInstance(ActionEvent event) {

        cartInstanceQuantity.setText(String.valueOf( Integer.parseInt(cartInstanceQuantity.getText()) + 1 ) );

        DBManager.addToCart(cartInstance.username(),cartInstance.articolo());

        if (Integer.parseInt(cartInstanceQuantity.getText()) == 2 ){
            cartInstanceMinus.setDisable(false);
        }


    }

    @FXML
    void removeFromInstance(ActionEvent event) {
        cartInstanceQuantity.setText(String.valueOf( Integer.parseInt(cartInstanceQuantity.getText()) - 1 ) );

        DBManager.removeInstanceFromCart(cartInstance.username(),cartInstance.articolo());

        if (Integer.parseInt(cartInstanceQuantity.getText()) == 1 ){
            cartInstanceMinus.setDisable(true);
        }


    }



    @FXML
    void buySingleArticle(ActionEvent event) {
        ImageSelector imageSelector = new ImageSelector();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Conferma pagamento");
        alert.setHeaderText("Acquisto");
        alert.setContentText("Cliccare 'OK' completerà la procedura di acquisto dell'articolo.");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(ImageSelector.alertIcon());



        alert.getDialogPane().setMinHeight(200);
        alert.getDialogPane().setMinWidth(200);


        alert.getDialogPane().setGraphic(new ImageView(imageSelector.checkMark()));

        DialogPane dialogPane = alert.getDialogPane();

        if (ThemeManager.getCurrentTheme().equals("NORMAL")) {

            for (String style : List.of("css/alertDialog.css", "css/alertNormalTheme.css")) {
                String resource = Objects.requireNonNull(MainApplication.class.getResource(style)).toExternalForm();
                dialogPane.getStylesheets().add(resource);
            }

        }

        else{

            for (String style : List.of("css/alertDialog.css", "css/alertBlueTheme.css")) {
                String resource = Objects.requireNonNull(MainApplication.class.getResource(style)).toExternalForm();
                dialogPane.getStylesheets().add(resource);
            }

        }


        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            UUID uuid = UUID.randomUUID();

            double costo_totale = Integer.parseInt(cartInstanceQuantity.getText()) * articolo.costo_singolo();

            double roundedPrice = (double) Math.round(costo_totale * 100) / 100;

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String currentDate = now.format(dtf);

            Ordine ordine = new Ordine(uuid.toString(), cartInstance.username(), cartInstance.articolo(),
                    Integer.parseInt(cartInstanceQuantity.getText()), roundedPrice, currentDate );

            DBManager.makeOrder(ordine);
            DBManager.removeFromCart(cartInstance.username(), cartInstance.articolo());
            cartPageController.removeFromCart(this.cartInstanceView);



        }

    }





    public void init(MainController mainController, CartPageController cartPageController,
                     CartInstanceView cartInstanceView, CartInstance cartInstance) {

        this.cartInstance = cartInstance;

        mainPageController = mainController;

        this.cartInstanceView = cartInstanceView;

        this.cartPageController = cartPageController;

        articolo = DBManager.findArticleByTitle(cartInstance.articolo());



        ImageSelector imageSelector = new ImageSelector();


        cartInstanceImage.setImage(imageSelector.articleImage(articolo.nome_articolo()));

        cartInstancePrice.setText(Double.toString(articolo.costo_singolo())+"€");

        cartInstanceTitle.setText(articolo.nome_articolo());

        cartInstanceQuantity.setText(String.valueOf(cartInstance.istanze_desiderate()));



        if (cartInstance.istanze_desiderate() == 1){
            cartInstanceMinus.setDisable(true);
        }




        cartInstanceRemove.setOnMousePressed(event -> {
            DBManager.removeFromCart(cartInstance.username(), cartInstance.articolo());
            cartPageController.removeFromCart(this.cartInstanceView);
        });





        cartInstanceImage.setOnMousePressed(event -> {

            mainPageController.goToArticle(articolo);

        });







    }









}

