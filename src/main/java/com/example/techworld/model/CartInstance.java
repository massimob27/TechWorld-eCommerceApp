package com.example.techworld.model;

public record CartInstance(String username, String articolo, int istanze_desiderate) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public String articolo() {
        return articolo;
    }

    @Override
    public int istanze_desiderate() {
        return istanze_desiderate;
    }
}
