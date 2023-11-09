package com.example.techworld.view;

import javafx.scene.image.Image;

import java.io.File;

public class ImageSelector {

    public static Image checkMark(){

        File file = new File("src/main/resources/com/example/techworld/view/images/confirmation.png");


        return new Image(file.toURI().toString(), 30, 30, false, false);


    }

    public static Image alertIcon(){

        File file = new File("src/main/resources/com/example/techworld/view/images/shop.png");


        return new Image(file.toURI().toString(), 10, 10, false, false);


    }


    public Image articleImage(String titolo){


        String nomeImmagine = titolo + ".png";


        File file = new File("src/main/resources/com/example/techworld/view/articleImageDB/"+nomeImmagine);


        return new Image(file.toURI().toString());



    }









}
