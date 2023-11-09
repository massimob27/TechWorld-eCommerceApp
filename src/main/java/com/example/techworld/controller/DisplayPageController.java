package com.example.techworld.controller;


import com.example.techworld.model.Article;
import com.example.techworld.model.DatabaseManager;
import com.example.techworld.view.ArticleCardView;
import com.example.techworld.view.FontIconClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.Vector;

public class DisplayPageController {



    @FXML
    private GridPane articleContainer;



    @FXML
    private TextField searchByTitle;

    @FXML
    private AnchorPane displayPage;

    @FXML
    private Separator displayPageSeparator;

    @FXML
    private Button researchButton;

    @FXML
    private Label messaggioErroreRicerca;


    MainController mainPageController;

    DatabaseManager DBManager = new DatabaseManager();

    Vector<Article> articoli;




    static boolean paginaDiRicerca;



    public void goSearch(ActionEvent actionEvent) {

        // se non è una pagina di ricerca e non viene scritto niente nella ricerca, non fa niente
        if (searchByTitle.getText() == null || searchByTitle.getText().isEmpty() && !paginaDiRicerca){
        }

        // se è scritto qualcosa nella ricerca, la fa
        else if (searchByTitle.getText() != null && !searchByTitle.getText().isEmpty()){
            paginaDiRicerca = true;
            mainPageController.showCatalog("SEARCH", searchByTitle.getText());

        }

        // se non viene scritto niente nella ricerca ma si è su una pagina di ricerca, si ritorna all'inizio
        else if ( (searchByTitle.getText() == null || searchByTitle.getText().isEmpty()) && paginaDiRicerca) {
            paginaDiRicerca = false;
            riempi("NORMAL","");
        }

    }








    @FXML
    public void init(MainController mainController, String type, String title) {

        mainPageController = mainController;

        articoli = DBManager.findArticles();

        articleContainer.setAlignment(Pos.CENTER);


        researchButton.setGraphic((new FontIconClass("mdi2c-card-search", 40)));


        if (!articoli.isEmpty()){

            riempi(type, title);

        }





        searchByTitle.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                researchButton.fire();
            }
        });





    }








    public void riempi(String type, String title){

        int column = 0;
        int row = 0;

        if (type.equals("NORMAL")) {

            messaggioErroreRicerca.setVisible(false);

            for (Article articolo : articoli) {

                ArticleCardView articleCard = new ArticleCardView(mainPageController, articolo);


                if (column == 3) {
                    column = 0;
                    row++;
                }

                articleContainer.add(articleCard, column++, row);
                GridPane.setMargin(articleCard, new Insets(15));

            }

            articleContainer.setMinHeight(((407) * (row + 1)) + ((50) * (row + 1)));

        }


        else if (type.equals("SEARCH")) {

            boolean trovatoAlmenoUno = false;

            for (Article articolo : articoli) {
                if (articolo.nome_articolo().contains(title) ||
                        articolo.nome_articolo().contains(title.substring(0, 1).toUpperCase() + title.substring(1))) {
                    trovatoAlmenoUno = true;
                    ArticleCardView articleCard = new ArticleCardView(mainPageController, articolo);


                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    articleContainer.add(articleCard, column++, row);
                    GridPane.setMargin(articleCard, new Insets(15));

                }
            }

            if (row <= 1) {
                articleContainer.setMinHeight(((622) * (row + 1)) + ((50) * (row + 1)));
            }
            else{
                articleContainer.setMinHeight(((407) * (row + 1)) + ((50) * (row + 1)));
            }

            if (!trovatoAlmenoUno){
                messaggioErroreRicerca.setVisible(true);
            }
            else{
                messaggioErroreRicerca.setVisible(false);
            }


        }
    }
}
