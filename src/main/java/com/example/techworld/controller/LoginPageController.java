package com.example.techworld.controller;


import com.example.techworld.model.DatabaseManager;
import com.example.techworld.model.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.regex.Pattern;


public class LoginPageController {


    @FXML
    private Label erroreConfermaPassword;

    @FXML
    private Label erroreConfermaPassword2;

    @FXML
    private Label erroreEmail;

    @FXML
    private Label erroreEmailAziendale;

    @FXML
    private Label erroreEmailAziendale2;

    @FXML
    private Label erroreNumeroTelefono;

    @FXML
    private Label erroreUtente;

    @FXML
    private Label errorePassword;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField newConfirmPassword;

    @FXML
    private TextField newEmail;

    @FXML
    private PasswordField newPassword;

    @FXML
    private TextField newTelephone;

    @FXML
    private TextField newUsername;

    @FXML
    private TextField newEmailAziendale;

    @FXML
    private Button registerButton;

    @FXML
    private VBox regolePassword;

    @FXML
    private VBox regoleUsername;


    @FXML
    private TextField loginNomeUtente;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label erroreLogin;


    MainController mainPageController;

    DatabaseManager DBManager = new DatabaseManager();



    @FXML
    void loginAction(ActionEvent event) {

        // UNO DEI DUE CAMPI è VUOTO

        if ( (loginNomeUtente.getText()==null|| loginNomeUtente.getText().isEmpty()) ||
                (loginPassword.getText()==null|| loginPassword.getText().isEmpty()) ){

            loginNomeUtente.setText(null);
            loginPassword.setText(null);



            loginNomeUtente.setStyle("-fx-border-color: red");
            loginPassword.setStyle("-fx-border-color: red");

            erroreLogin.setText("Inserire nome utente e password");
            erroreLogin.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreLogin);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreLogin.setVisible(true);



        }

        else{

            if ( DBManager.verificaLogin(loginNomeUtente.getText(), loginPassword.getText()) ){

                User user = DBManager.findUserByUsername(loginNomeUtente.getText());
                mainPageController.goLogin(user);

            }

            else{

                loginNomeUtente.setText(null);
                loginPassword.setText(null);

                erroreLogin.setVisible(false);

                loginNomeUtente.setStyle("-fx-border-color: red");
                loginPassword.setStyle("-fx-border-color: red");

                erroreLogin.setText("Le tue credenziali sono errate");
                erroreLogin.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreLogin);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreLogin.setVisible(true);

            }

        }





    }
















    @FXML
    void registerAction(ActionEvent event) {

        boolean username;
        boolean password;
        boolean confirmPassword;
        boolean email;
        boolean telephoneNumber;
        boolean business;

        boolean failedBusinessAttempt;
        /* Se l'user cerca di creare un account business ma fallisce allora l'applicazione
           non gli crea un account normale da cliente, prima ripropone la possibilità */


        if (newUsername.getText()==null|| newUsername.getText().isEmpty()) {

            username = false;

            newUsername.setStyle("-fx-border-color: red");
            erroreUtente.setText("Compila questo campo");
            erroreUtente.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreUtente);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreUtente.setVisible(true);

        }

        // IL CAMPO NON è VUOTO

        else {

            // RISPETTA I CRITERI

            if (Pattern.matches("[a-zA-Z][a-zA-Z0-9]{4,19}", newUsername.getText())) {
                if (DBManager.findUserByUsername(newUsername.getText()) != null) {
                    // USERNAME GIA IN UTILIZZO

                    username = false;

                    newUsername.setText(null);

                    newUsername.setStyle("-fx-border-color: red");
                    erroreUtente.setText("Nome utente già in uso");
                    erroreUtente.setStyle("-fx-text-fill: red");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreUtente);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreUtente.setVisible(true);

                } else {

                    username = true;

                    newUsername.setStyle("-fx-border-color: green");

                    erroreUtente.setVisible(false);
                    erroreUtente.setText("✓");
                    erroreUtente.setStyle("-fx-text-fill: green");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreUtente);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreUtente.setVisible(true);


                }

            }

            // NON RISPETTA I CRITERI

            else {

                username = false;

                newUsername.setText(null);

                newUsername.setStyle("-fx-border-color: red");
                erroreUtente.setText("Nome utente non valido");
                erroreUtente.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreUtente);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreUtente.setVisible(true);


            }
        }


        // ----------------------------------------------------------------------------- //


        // CHECK PASSWORD

        if (newPassword.getText()==null|| newPassword.getText().isEmpty()) {

            password = false;

            newPassword.setStyle("-fx-border-color: red");
            errorePassword.setText("Compila questo campo");
            errorePassword.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), errorePassword);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            errorePassword.setVisible(true);

        }

        else {
            if (Pattern.matches("[a-zA-Z0-9]{10,20}", newPassword.getText())) {
                password = true;
                newPassword.setStyle("-fx-border-color: green");

                errorePassword.setVisible(false);
                errorePassword.setText("✓");
                errorePassword.setStyle("-fx-text-fill: green");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), errorePassword);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                errorePassword.setVisible(true);


            }

            else {

                password = false;

                newPassword.setText(null);
                newConfirmPassword.setText(null);

                newPassword.setStyle("-fx-border-color: red");
                errorePassword.setText("Password non valida");
                errorePassword.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), errorePassword);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                errorePassword.setVisible(true);

            }

        }


        // ----------------------------------------------------------------------------- //


        // CHECK CONFIRM PASSWORD

        if ( newConfirmPassword.getText()==null|| newConfirmPassword.getText().isEmpty()) {

            confirmPassword = false;

            erroreConfermaPassword2.setVisible(false);



            newConfirmPassword.setStyle("-fx-border-color: red");
            erroreConfermaPassword.setText("Compila questo campo");
            erroreConfermaPassword.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreConfermaPassword);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreConfermaPassword.setVisible(true);

        }

        else{

            if ( !newConfirmPassword.getText().equals(newPassword.getText()) ) {

                confirmPassword = false;

                newPassword.setText(null);
                newConfirmPassword.setText(null);

                erroreConfermaPassword.setVisible(false);
                erroreConfermaPassword2.setVisible(false);

                newConfirmPassword.setStyle("-fx-border-color: red");
                erroreConfermaPassword.setText("Le due password non sono uguali");
                erroreConfermaPassword.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreConfermaPassword);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreConfermaPassword.setVisible(true);


            }

            else{

                confirmPassword = true;
                newConfirmPassword.setStyle("-fx-border-color: green");

                erroreConfermaPassword.setVisible(false);
                erroreConfermaPassword2.setText("✓");
                erroreConfermaPassword2.setStyle("-fx-text-fill: green");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreConfermaPassword2);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreConfermaPassword2.setVisible(true);



            }


        }


        // ----------------------------------------------------------------------------- //


        // CHECK EMAIL

        if (newEmail.getText()==null|| newEmail.getText().isEmpty()) {

            email = false;

            newEmail.setStyle("-fx-border-color: red");
            erroreEmail.setText("Compila questo campo");
            erroreEmail.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreEmail);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreEmail.setVisible(true);

        }

        else {

            // RISPETTA I CRITERI

            if (Pattern.matches("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})", newEmail.getText())) {
                if (DBManager.findEmails().contains(newEmail.getText())) {

                    email = false;

                    // EMAIL GIA IN UTILIZZO

                    newEmail.setText(null);

                    newEmail.setStyle("-fx-border-color: red");
                    erroreEmail.setText("Email già in uso");
                    erroreEmail.setStyle("-fx-text-fill: red");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreEmail);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreEmail.setVisible(true);

                }
                else {

                    email = true;

                    newEmail.setStyle("-fx-border-color: green");

                    erroreEmail.setVisible(false);
                    erroreEmail.setText("✓");
                    erroreEmail.setStyle("-fx-text-fill: green");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreEmail);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreEmail.setVisible(true);


                }

            }

            // NON RISPETTA I CRITERI

            else {

                email = false;

                newEmail.setText(null);

                newEmail.setStyle("-fx-border-color: red");
                erroreEmail.setText("Email non valida");
                erroreEmail.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreEmail);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreEmail.setVisible(true);


            }
        }


        // ----------------------------------------------------------------------------- //


        // CHECK CELLPHONE


        if (newTelephone.getText()==null|| newTelephone.getText().isEmpty()) {

            telephoneNumber = false;

            newTelephone.setStyle("-fx-border-color: red");
            erroreNumeroTelefono.setText("Compila questo campo");
            erroreNumeroTelefono.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreNumeroTelefono);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            erroreNumeroTelefono.setVisible(true);

        }

        else {

            // RISPETTA I CRITERI

            if (Pattern.matches("[0-9]{10}", newTelephone.getText())) {
                if (DBManager.findTelephoneNumbers().contains(newTelephone.getText())) {

                    // TELEFONO GIA IN UTILIZZO

                    telephoneNumber = false;

                    newTelephone.setText(null);

                    newTelephone.setStyle("-fx-border-color: red");
                    erroreNumeroTelefono.setText("Numero di telefono già in uso");
                    erroreNumeroTelefono.setStyle("-fx-text-fill: red");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreNumeroTelefono);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreNumeroTelefono.setVisible(true);

                }
                else {

                    telephoneNumber = true;

                    newTelephone.setStyle("-fx-border-color: green");

                    erroreNumeroTelefono.setVisible(false);
                    erroreNumeroTelefono.setText("✓");
                    erroreNumeroTelefono.setStyle("-fx-text-fill: green");

                    FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreNumeroTelefono);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();

                    erroreNumeroTelefono.setVisible(true);


                }

            }

            // NON RISPETTA I CRITERI

            else {

                telephoneNumber = false;

                newTelephone.setText(null);

                newTelephone.setStyle("-fx-border-color: red");
                erroreNumeroTelefono.setText("Numero di telefono non valido");
                erroreNumeroTelefono.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreNumeroTelefono);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreNumeroTelefono.setVisible(true);


            }
        }


        // ----------------------------------------------------------------------------- //


        // CHECK BUSINESS



        if (newEmailAziendale.getText() == null || newEmailAziendale.getText().isEmpty()) {
            business = false;
            failedBusinessAttempt = false;

            erroreEmailAziendale2.setVisible(false);
            erroreEmailAziendale.setVisible(false);
            newEmailAziendale.setStyle("");
        }


        else{
            // HA MESSO QUALCOSA, VERIFICA

            if (Pattern.matches("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})", newEmailAziendale.getText())) {

                failedBusinessAttempt = false;

                business = true;

                erroreEmailAziendale2.setVisible(false);
                erroreEmailAziendale.setVisible(false);


                newEmailAziendale.setStyle("-fx-border-color: green");
                erroreEmailAziendale2.setText("✓");
                erroreEmailAziendale2.setStyle("-fx-text-fill: green");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreEmailAziendale2);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreEmailAziendale2.setVisible(true);



            }

            else {

                failedBusinessAttempt = true;

                business = false;

                newEmailAziendale.setText(null);

                erroreEmailAziendale.setVisible(false);
                erroreEmailAziendale2.setVisible(false);

                newEmailAziendale.setStyle("-fx-border-color: maroon");
                erroreEmailAziendale.setText("Email aziendale non valida");
                erroreEmailAziendale.setStyle("-fx-text-fill: maroon");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), erroreEmailAziendale);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                erroreEmailAziendale.setVisible(true);



            }

        }

        // FINALIZZA REGISTRAZIONE (se è andato tutto bene)


        if( (username && password && confirmPassword && email && telephoneNumber) && !failedBusinessAttempt){

            String encryptedPassword = BCrypt.hashpw(newPassword.getText(), BCrypt.gensalt(12));

            User user;

            if (!business){

                user = new User(newUsername.getText(),encryptedPassword,"EMPTY",
                        false,newTelephone.getText(),newEmail.getText());

                DBManager.insertNewUser(user);

            }
            else{

                user = new User(newUsername.getText(),encryptedPassword,newEmailAziendale.getText(),
                        true,newTelephone.getText(),newEmail.getText());

                DBManager.insertNewUser(user);

            }

            // LOGGED

            mainPageController.goLogin(user);

        }



    }




    public void init(MainController mainController) {

        mainPageController = mainController;

        newUsername.setOnMousePressed(event -> {
            if (!regoleUsername.isVisible()) {
                FadeTransition ft = new FadeTransition(Duration.seconds(1), regoleUsername);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
            }

            regoleUsername.setVisible(true);

        });


        newPassword.setOnMousePressed(event -> {
            if (!regolePassword.isVisible()) {
                FadeTransition ft = new FadeTransition(Duration.seconds(1), regolePassword);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
            }

            regolePassword.setVisible(true);

        });


        loginNomeUtente.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                loginButton.fire();
            }
        });

        loginPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                loginButton.fire();
            }
        });



    }






}
