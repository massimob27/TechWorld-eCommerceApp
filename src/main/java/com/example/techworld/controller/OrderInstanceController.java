package com.example.techworld.controller;

import com.example.techworld.model.Ordine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderInstanceController {

    @FXML
    private Label orderInstanceCost;

    @FXML
    private Label orderInstanceDate;

    @FXML
    private Label orderInstanceQuantity;

    @FXML
    private Label orderInstanceTitle;




    Ordine ordine;


    public void init(Ordine ordine){

        this.ordine = ordine;

        orderInstanceTitle.setText(ordine.articolo());
        orderInstanceQuantity.setText(String.valueOf(ordine.istanze()));
        orderInstanceCost.setText(String.valueOf(ordine.costo_totale())+"€");
        orderInstanceDate.setText(ordine.data());

    }







}
