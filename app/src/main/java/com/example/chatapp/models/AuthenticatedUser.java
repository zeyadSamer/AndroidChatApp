package com.example.chatapp.models;

public class AuthenticatedUser extends User{


    public AuthenticatedUser() {
        super();
    }

    public AuthenticatedUser(String username, String email) {
        super(username, email);
    }
}
