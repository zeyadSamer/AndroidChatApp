package com.example.chatapp.models;

import android.util.Log;

import java.io.Serializable;

public class Friend extends RegisteredUser implements Serializable{


    @Override
    public void logIn() {
        System.out.println(" Cannot login from friend instance");
    }


}