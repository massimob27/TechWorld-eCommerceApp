package com.example.techworld.model;

public record Ordine(String id, String username, String articolo, int istanze, double costo_totale, String data) {
    @Override
    public String id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String articolo() {
        return articolo;
    }

    @Override
    public int istanze() {
        return istanze;
    }

    @Override
    public double costo_totale() {
        return costo_totale;
    }

    @Override
    public String data() {
        return data;
    }
}
