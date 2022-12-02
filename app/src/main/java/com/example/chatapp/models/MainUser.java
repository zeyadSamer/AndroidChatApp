package com.example.chatapp.models;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.chatapp.FriendsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Objects;

public class MainUser extends User {



    private String password;
    private final FirebaseAuth firebaseAuth;
    public boolean isSignedUp=false;
    public boolean isLoggedIn=false;
    private Activity currentActivity;
    private Activity nextActivity;


    public MainUser(){
        super();


        firebaseAuth=FirebaseAuth.getInstance();

    }



    public MainUser(String username, String email, String password) {
        super(username,email);

        this.password = password;
        firebaseAuth=FirebaseAuth.getInstance();

    }




    public void signUp(){
       firebaseAuth.createUserWithEmailAndPassword(this.getEmail(),this.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                Log.d("success register",String.valueOf(task.isSuccessful()));
                if(task.isSuccessful()){

                    MainUser.this.isSignedUp=true;
                    Toast.makeText(MainUser.this.currentActivity,"SignedUp Successfully",Toast.LENGTH_LONG).show();


                }else if(!task.isSuccessful()){
                    MainUser.this.isSignedUp=false;

                    Log.d("firebase:", Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    Toast.makeText(MainUser.this.currentActivity,"signUp failed",Toast.LENGTH_LONG).show();



                }


            }

        });





    }



    public void logIn(){


        firebaseAuth.signInWithEmailAndPassword(this.getEmail(),this.password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){
                    MainUser.this.isLoggedIn=true;

                    Toast.makeText(MainUser.this.currentActivity,"Successfully loggedIn",Toast.LENGTH_LONG).show();

                    Intent i= new Intent(MainUser.this.currentActivity, MainUser.this.nextActivity.getClass());
                    Log.d("emailDebug",MainUser.this.getEmail());
//

                 i.putExtra("email",MainUser.this.getEmail());
                 i.putExtra("username" ,MainUser.this.getUsername());


                   // i.putExtra("mainUser", (Serializable) MainUser.this);

                    currentActivity.startActivity(i);



                }else{
                    MainUser.this.isLoggedIn=false;

                    Log.d("SignUp error", task.getException().getLocalizedMessage());
                    Toast.makeText(MainUser.this.currentActivity,"Login Failed",Toast.LENGTH_LONG).show();


                }
            }
        });


    }


    public void setCurrentActivity(Activity currentActivity){
        this.currentActivity = currentActivity;

    }

    public Activity getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(Activity nextActivity) {
        this.nextActivity = nextActivity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
