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
import java.util.*;

public class CartPageController {


    @FXML
    private AnchorPane cartPage;

    @FXML
    private Separator cartPageSeparator;

    @FXML
    private ListView<CartInstanceView> cartPageDisplay;

    @FXML
    private Label cartPageLabel;






    UserManager userControl;

    MainController mainPageController;

    DatabaseManager DBManager = new DatabaseManager();

    Vector<CartInstance> carrello;


    @FXML
    private Button buyAllButton;







    @FXML
    void buyAllAction(ActionEvent event) {

        ImageSelector imageSelector = new ImageSelector();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Conferma pagamento");
        alert.setHeaderText("Acquisto");
        alert.setContentText("Cliccare 'OK' completerà la procedura di acquisto di " +
                "tutti gli articoli presenti nel tuo carrello nelle specificate quantità.");

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

            carrello = DBManager.findCartByUser(userControl.getUser().username());

            for (CartInstance cartInstance : carrello) {

                UUID uuid = UUID.randomUUID();

                double costo_totale = DBManager.findArticleByTitle(cartInstance.articolo()).costo_singolo() *
                        cartInstance.istanze_desiderate();

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                String currentDate = now.format(dtf);

                double roundedPrice = (double) Math.round(costo_totale * 100) / 100;

                Ordine ordine = new Ordine(uuid.toString(), cartInstance.username(), cartInstance.articolo(),
                        cartInstance.istanze_desiderate(), roundedPrice, currentDate );

                DBManager.makeOrder(ordine);
                DBManager.removeFromCart(cartInstance.username(), cartInstance.articolo());


            }

            cartPageDisplay.getItems().clear();
            cartPageLabel.setText("Il tuo carrello è attualmente vuoto");
            buyAllButton.setVisible(false);


        }


    }





    void removeFromCart(CartInstanceView cartInstanceView) {

        cartPageDisplay.getItems().remove(cartInstanceView);
        cartPageDisplay.getSelectionModel().clearSelection();

        if (cartPageDisplay.getItems().isEmpty()){
            cartPageLabel.setText("Il tuo carrello è attualmente vuoto");
            buyAllButton.setVisible(false);
        }

    }






    public void init(MainController mainController, UserManager userControl){



        this.userControl = userControl;

        mainPageController = mainController;


        carrello = DBManager.findCartByUser(userControl.getUser().username());


        if (!carrello.isEmpty()) {

            for (CartInstance cartInstance : carrello) {

                CartInstanceView cartInstanceView = new CartInstanceView(mainPageController, this, cartInstance);

                cartPageDisplay.getItems().add(cartInstanceView);


            }

            cartPage.setMinHeight(150+cartPageDisplay.getItems().size() * 190);
            cartPageDisplay.setMinHeight(cartPageDisplay.getItems().size() * 190);

        }

        else{

            cartPageLabel.setText("Il tuo carrello è attualmente vuoto");
            buyAllButton.setVisible(false);

        }







    }








}
