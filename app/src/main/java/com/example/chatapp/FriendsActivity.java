package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.chatapp.models.AuthenticatedUser;
import com.example.chatapp.models.MainUser;
import com.google.gson.Gson;

import java.io.Serializable;

public class FriendsActivity extends AppCompatActivity implements Serializable {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Intent intent = getIntent();

        String email=intent.getStringExtra("email");
        String username=intent.getStringExtra("username");
        AuthenticatedUser authenticatedUser=new AuthenticatedUser(username,email);
        Log.d("friend", authenticatedUser.getEmail());



        textView= findViewById(R.id.textView);
        textView.setText(authenticatedUser.getEmail());




    }
}