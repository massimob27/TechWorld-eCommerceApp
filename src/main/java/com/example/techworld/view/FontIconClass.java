package com.example.techworld.view;

import org.kordamp.ikonli.javafx.FontIcon;

public class FontIconClass extends FontIcon {
    public FontIconClass(String name, int size) {
        super(name);
        this.setIconSize(size);
        this.getStyleClass().add("icons-color");
    }

}

