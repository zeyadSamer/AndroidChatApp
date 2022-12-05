package com.example.chatapp.models;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.chatapp.FriendsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticatedUser extends User{


    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public AuthenticatedUser() {
        super();
    }

    public AuthenticatedUser(String username, String email) {
        super(username, email);
    }


}
