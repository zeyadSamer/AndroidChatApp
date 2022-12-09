package com.example.chatapp.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class AuthenticatedUser extends RegisteredUser implements Serializable {


  @Exclude public UserSettings settings;

      public AuthenticatedUser() {
        super();
        this.fetchAuthenticatedUserData();
        settings=new UserSettings();
    }

    public AuthenticatedUser(String username, String email,String password,String profilePic) {
        super(username, email,password,profilePic);
        this.fetchAuthenticatedUserData();
        settings=new UserSettings();
    }



    @Exclude public static boolean isThereCurrentUser(){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Log.d("isThereCurrentUser: ",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            return true;
        }
        return false;

    }

    @Exclude public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }





    @Exclude   public void fetchAuthenticatedUserData(){

 //   setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
 this.email=FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Log.d("email;", "fetchAuthenticatedUserData: "+this.email);

    }







}
