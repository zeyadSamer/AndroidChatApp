package com.example.chatapp.models;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Objects;

public class RegisteringUser extends User implements Serializable  {



    private String password="";
    private final FirebaseAuth firebaseAuth;

    private Activity currentActivity;
    private Activity nextActivity;


    public RegisteringUser(){
        super();


        firebaseAuth=FirebaseAuth.getInstance();

    }



    public RegisteringUser(String username, String email, String password) {
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
                    Toast.makeText(RegisteringUser.this.currentActivity,"SignedUp Successfully",Toast.LENGTH_LONG).show();

                    AuthenticatedUser authenticatedUser=new AuthenticatedUser(RegisteringUser.this.getUsername(), RegisteringUser.this.getEmail()," ");
                    FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(authenticatedUser);
                    Intent i= new Intent(RegisteringUser.this.currentActivity, RegisteringUser.this.nextActivity.getClass());

                    i.putExtra("authUser",authenticatedUser);
                    currentActivity.startActivity(i);
                    currentActivity.finish();



                }else if(!task.isSuccessful()){


                    Log.d("firebase:", Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    Toast.makeText(RegisteringUser.this.currentActivity,"signUp failed",Toast.LENGTH_LONG).show();



                }


            }

        });





    }



    public void logIn(){


        firebaseAuth.signInWithEmailAndPassword(this.getEmail(),this.password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){


                    Toast.makeText(RegisteringUser.this.currentActivity,"Successfully loggedIn",Toast.LENGTH_LONG).show();

                    //we may need to get userdata from db here

                    AuthenticatedUser authenticatedUser=new AuthenticatedUser(RegisteringUser.this.getUsername(), RegisteringUser.this.getEmail()," ");

                    Intent i= new Intent(RegisteringUser.this.currentActivity, RegisteringUser.this.nextActivity.getClass());

                    i.putExtra("authUser",authenticatedUser);
                    currentActivity.startActivity(i);
                    currentActivity.finish();



                }else if(!task.isSuccessful()){


                    Log.d("SignUp error", task.getException().getLocalizedMessage());
                    Toast.makeText(RegisteringUser.this.currentActivity,"Login Failed",Toast.LENGTH_LONG).show();


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
