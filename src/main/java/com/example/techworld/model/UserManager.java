package com.example.techworld.model;
public class UserManager {

    private boolean userIsLogged;

    private User user;


    public void setUser(User user){

        this.user = user;

        userIsLogged = true;

    }

    public void logout(){
        userIsLogged = false;
        user = null;

    }

    public User getUser(){

        return user;

    }

    public boolean getUserIsLogged(){

        return userIsLogged;

    }




}
