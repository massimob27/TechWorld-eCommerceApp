package com.example.techworld.model;

public record User(String username, String password, String azienda,
                   boolean venditore, String num_telefono, String email) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String azienda() {
        return azienda;
    }

    @Override
    public boolean venditore() {
        return venditore;
    }

    @Override
    public String num_telefono() {
        return num_telefono;
    }

    @Override
    public String email() {
        return email;
    }

}
