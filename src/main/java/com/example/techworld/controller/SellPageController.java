package com.example.techworld.controller;

import com.example.techworld.model.Article;
import com.example.techworld.model.DatabaseManager;
import com.example.techworld.model.ThemeManager;
import com.example.techworld.model.UserManager;
import com.example.techworld.view.ImageSelector;
import com.example.techworld.view.MainApplication;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



public class SellPageController {

    @FXML
    private Label erroreCosto;

    @FXML
    private Label erroreDescrizione;

    @FXML
    private Label erroreImmagine;

    @FXML
    private Label erroreTitolo;

    @FXML
    private TextField newCosto;

    @FXML
    private TextArea newDescrizione;

    @FXML
    private TextField newTitolo;

    @FXML
    private Button pulsanteInserimento;

    @FXML
    private Button pulsanteEliminazioneFoto;

    @FXML
    private ImageView schermoImmagine;

    @FXML
    private Button sellButton;

    @FXML
    private StackPane stackPaneImmagine;




    private File currentImage = null;


    // CHECK
    private boolean imageCheck;

    private boolean titleCheck;

    private boolean descriptionCheck;

    private boolean priceCheck;





    MainController mainPageController;

    UserManager userControl;

    DatabaseManager DBManager = new DatabaseManager();




    @FXML
    void actionScegliFoto(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli una foto");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(pulsanteInserimento.getScene().getWindow());

        if (selectedFile != null){

            currentImage = selectedFile;

            pulsanteEliminazioneFoto.setDisable(false);

            stackPaneImmagine.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");

            erroreImmagine.setText("");

            Image image = new Image(selectedFile.getPath());

            schermoImmagine.setImage(image);


        }



    }


    @FXML
    void actionEliminaFoto(ActionEvent event) {

        schermoImmagine.setImage(null);

        currentImage = null;

        imageCheck = false;

        pulsanteEliminazioneFoto.setDisable(true);

        stackPaneImmagine.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");

        erroreImmagine.setText("");



    }



    @FXML
    void completeProduct(ActionEvent event) {


        // CHECK TITLE

        if (newTitolo.getText()==null|| newTitolo.getText().isEmpty()) {
            titleCheck = false;

            newTitolo.setStyle("-fx-border-color: red");
            erroreTitolo.setText("Compila questo campo");
            erroreTitolo.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreTitolo);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreTitolo.setVisible(true);
        }

        else{

            if (newTitolo.getText().length()<15 || newTitolo.getText().length()>100){
                titleCheck = false;

                newTitolo.setText(null);

                newTitolo.setStyle("-fx-border-color: red");
                erroreTitolo.setText("Il titolo deve contenere dai 15 ai 100 caratteri");
                erroreTitolo.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreTitolo);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreTitolo.setVisible(true);


            }

            // NON CONTIENE CARATTERI SPECIALI (NON PERMETTE LA CREAZIONE DEL FILE [in questo caso immagine] SU WINDOWS)

            else if( newTitolo.getText().contains("/") || newTitolo.getText().contains(":") || newTitolo.getText().contains("*") ||
                    newTitolo.getText().contains("?") || newTitolo.getText().contains("”") || newTitolo.getText().contains("<") ||
                    newTitolo.getText().contains(">") || newTitolo.getText().contains("|") || newTitolo.getText().contains("\"")||
                    newTitolo.getText().contains("\\") ) {

                titleCheck = false;

                newTitolo.setText(null);

                newTitolo.setStyle("-fx-border-color: red");
                erroreTitolo.setText("Il titolo non può contenere i caratteri: \\  / : *  ? ” < > | \"");
                erroreTitolo.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreTitolo);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreTitolo.setVisible(true);


            }

            else{
                if (DBManager.findArticleByTitle(newTitolo.getText())!= null) {
                    titleCheck = false;

                    newTitolo.setText(null);

                    newTitolo.setStyle("-fx-border-color: red");
                    erroreTitolo.setText("Esiste già un articolo con questo titolo");
                    erroreTitolo.setStyle("-fx-text-fill: red");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreTitolo);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreTitolo.setVisible(true);
                }

                else{

                    titleCheck = true;

                    newTitolo.setStyle("-fx-border-color: green");

                    erroreTitolo.setVisible(false);

                    erroreTitolo.setText("✓");
                    erroreTitolo.setStyle("-fx-text-fill: green");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreTitolo);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreTitolo.setVisible(true);




                }

            }
        }


        // CHECK DESC



        if (newDescrizione.getText()==null|| newDescrizione.getText().isEmpty()) {
            descriptionCheck = false;

            newDescrizione.setStyle("-fx-border-color: red");
            erroreDescrizione.setText("Compila questo campo");
            erroreDescrizione.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreDescrizione);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreDescrizione.setVisible(true);
        }

        else{

            if (newDescrizione.getText().length()<50||newDescrizione.getText().length()>5000){
                descriptionCheck = false;

                newDescrizione.setText(null);

                newDescrizione.setStyle("-fx-border-color: red");
                erroreDescrizione.setText("La descrizione deve contenere dai 50 ai 5000 caratteri");
                erroreDescrizione.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreDescrizione);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreDescrizione.setVisible(true);


            }

            else{
                descriptionCheck = true;

                newDescrizione.setStyle("-fx-border-color: green");

                erroreDescrizione.setVisible(false);

                erroreDescrizione.setText("✓");
                erroreDescrizione.setStyle("-fx-text-fill: green");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreDescrizione);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreDescrizione.setVisible(true);

            }
        }



        // PRICE CHECK

        if (newCosto.getText()==null|| newCosto.getText().isEmpty()) {
            priceCheck = false;

            newCosto.setStyle("-fx-border-color: red");
            erroreCosto.setText("Compila questo campo");
            erroreCosto.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreCosto);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreCosto.setVisible(true);
        }


        else{

            try {
                Double.parseDouble(newCosto.getText());
                if (Double.parseDouble(newCosto.getText()) > 0 && Double.parseDouble(newCosto.getText()) < 10000 ) {

                    priceCheck = true;

                    newCosto.setStyle("-fx-border-color: green");

                    erroreCosto.setVisible(false);

                    erroreCosto.setText("✓");
                    erroreCosto.setStyle("-fx-text-fill: green");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreCosto);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreCosto.setVisible(true);
                }

                else{

                    priceCheck = false;

                    newCosto.setText(null);

                    newCosto.setStyle("-fx-border-color: red");
                    erroreCosto.setText("Costi pari o minori a 0 o superiori a 10000");
                    erroreCosto.setStyle("-fx-text-fill: red");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreCosto);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreCosto.setVisible(true);



                }


            }

            catch (NumberFormatException e) {

                priceCheck = false;

                newCosto.setText(null);

                newCosto.setStyle("-fx-border-color: red");
                erroreCosto.setText("Costo non valido");
                erroreCosto.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreCosto);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreCosto.setVisible(true);

            }

        }


        // IMAGE CHECK



        if ( currentImage == null) {
            imageCheck = false;

            erroreImmagine.setText("Devi selezionare una foto");
            erroreImmagine.setStyle("-fx-text-fill: red");

            stackPaneImmagine.setStyle("-fx-border-width: 2; -fx-border-color: red;");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreImmagine);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreImmagine.setVisible(true);
        }

        else{

            imageCheck = true;


            erroreImmagine.setText("✓");
            erroreImmagine.setStyle("-fx-text-fill: green");

            stackPaneImmagine.setStyle("-fx-border-width: 2; -fx-border-color: green;");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreImmagine);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreImmagine.setVisible(true);




        }




        if (imageCheck && titleCheck && descriptionCheck && priceCheck && currentImage != null){

            ImageSelector imageSelector = new ImageSelector();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Conferma inserimento");
            alert.setHeaderText("Inserimento");
            alert.setContentText("Cliccare 'OK' completerà la procedura e metterà in vendita il tuo prodotto.");

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

                Article article;

                double price = Double.parseDouble(newCosto.getText());
                double roundedPrice = (double) Math.round(price * 100) / 100;

                try {
                    article = new Article(newTitolo.getText(), userControl.getUser().username(),
                            roundedPrice, newDescrizione.getText());

                    DBManager.insertNewArticle(article);


                    FileInputStream in = new FileInputStream(currentImage);

                    String nomeDatoDaMe = newTitolo.getText() + ".png";
                    FileOutputStream ou = new FileOutputStream("src/main/resources/com/example/techworld/view/articleImageDB/"+nomeDatoDaMe);


                    BufferedInputStream bin = new BufferedInputStream(in);
                    BufferedOutputStream bou = new BufferedOutputStream(ou);

                    int b=0;
                    while(b!=-1){

                        b=bin.read();
                        bou.write(b);
                    }
                    bin.close();
                    bou.close();

                }

                catch (NumberFormatException | FileNotFoundException e) {
                    System.out.println("Errore parseInt (INSERIMENTO ARTICOLO)");

                }

                catch (IOException e) {
                    throw new RuntimeException(e);
                }

                mainPageController.showCatalog("NORMAL", "");



            }



        }


    }











    public void init(MainController mainController, UserManager userControl) {

        mainPageController = mainController;

        this.userControl = userControl;


    }

}
