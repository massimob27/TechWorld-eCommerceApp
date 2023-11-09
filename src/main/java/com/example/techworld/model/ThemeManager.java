package com.example.techworld.model;

public class ThemeManager {

    private static String currentTheme = "NORMAL";


    public void  setCurrentTheme(String newTheme){
        currentTheme = newTheme;
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }
}
