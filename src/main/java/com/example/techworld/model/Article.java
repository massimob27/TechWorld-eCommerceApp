package com.example.techworld.model;

public record Article(String nome_articolo,String accountCreatore, double costo_singolo,String descrizione) {
    @Override
    public String nome_articolo() {
        return nome_articolo;
    }

    @Override
    public String accountCreatore() {
        return accountCreatore;
    }

    @Override
    public double costo_singolo() {
        return costo_singolo;
    }

    @Override
    public String descrizione() {
        return descrizione;
    }
}
