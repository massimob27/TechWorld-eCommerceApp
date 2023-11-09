package com.example.techworld.controller;


import com.example.techworld.model.*;
import com.example.techworld.view.*;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ProfilePageController {

    @FXML
    private Label businessGraphic;

    @FXML
    private Label businessLabel;

    @FXML
    private Button downloadDataButton;

    @FXML
    private Label emailGraphic;

    @FXML
    private Label emailLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<OrderInstanceView> orderListDisplay;

    @FXML
    private Label phoneGraphic;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label usernameGraphic;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label orderListLabel;








    MainController mainPageController;

    UserManager userControl;

    DatabaseManager DBManager = new DatabaseManager();

    ArrayList<Ordine> orders = new ArrayList<Ordine>();




    @FXML
    void doLogout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Conferma azione");
        alert.setHeaderText("Logout");
        alert.setContentText("Clicca 'OK' se sei sicuro di voler uscire dal tuo account.");


        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(ImageSelector.alertIcon());


        alert.getDialogPane().setMinHeight(200);
        alert.getDialogPane().setMinWidth(200);


        alert.getDialogPane().setGraphic(new FontIconClass("mdi2l-logout", 30));


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

        if (result.isPresent() && result.get() == ButtonType.OK) {

            mainPageController.goLogout();

        }


    }





    @FXML
    void downloadAction(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        File destination = fileChooser.showSaveDialog(downloadDataButton.getScene().getWindow());

        if (destination != null) {

            FileWriter outputfile = new FileWriter(destination + ".csv");

            // creo oggetto cswriter dandogli file di output e il separatore
            CSVWriter writer = new CSVWriter(outputfile, '|',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // aggiungo header al csv
            String[] header = { "Articolo", "Quantita'", "Costo totale", "Data acquisto" };
            writer.writeNext(header);

            List<String[]> dati = new ArrayList<String[]>();


            for (Ordine ordine : orders) {

                String[] data = new String[4];

                data[0] = ordine.articolo();
                data[1] = String.valueOf(ordine.istanze());
                data[2] = String.valueOf(ordine.costo_totale());
                data[3] = ordine.data();

                dati.add(data);

            }
            writer.writeAll(dati);

            // chiudo connessione
            writer.close();


        }


    }









    public void init(MainController mainController, UserManager userControl) {

        mainPageController = mainController;

        this.userControl = userControl;

        orders = DBManager.findOrdersByUser(userControl.getUser().username());

        // creando un nuovo comparatore, riordiniamo la lista, dall'ordine piu recente al meno recente

        Collections.sort(orders, new OrderCompare());



        if (!orders.isEmpty()) {

            for (Ordine ordine : orders) {

                OrderInstanceView orderInstanceView = new OrderInstanceView(ordine);

                orderListDisplay.getItems().add(orderInstanceView);


            }


        }

        else{
            orderListLabel.setText("Lo storico degli ordini è vuoto.");
            downloadDataButton.setVisible(false);

        }




        usernameGraphic.setGraphic((new FontIconClass("mdi2a-account", 20)));
        phoneGraphic.setGraphic((new FontIconClass("mdi2p-phone", 20)));
        emailGraphic.setGraphic((new FontIconClass("mdi2e-email", 20)));
        businessGraphic.setGraphic((new FontIconClass("mdi2e-email-variant", 20)));

        usernameLabel.setText(userControl.getUser().username());
        phoneLabel.setText(userControl.getUser().num_telefono());
        emailLabel.setText(userControl.getUser().email());

        if (userControl.getUser().venditore()){
            businessLabel.setText(userControl.getUser().azienda());
        }
        else{
            businessLabel.setText("Nessuna.");
        }


    }










}
