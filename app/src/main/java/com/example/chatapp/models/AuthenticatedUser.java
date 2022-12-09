package com.example.chatapp.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class AuthenticatedUser extends User implements Serializable {

    public String profilePic;

    public UserSettings settings;

    public AuthenticatedUser() {
        super();
        this.fetchAuthenticatedUserData();

        settings=new UserSettings();
    }

    public AuthenticatedUser(String username, String email,String profilePic) {
        super(username, email);
        this.profilePic=profilePic;
        this.fetchAuthenticatedUserData();
        settings=new UserSettings();
    }



    public static boolean isThereCurrentUser(){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Log.d("isThereCurrentUser: ",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            return true;
        }
        return false;

    }

    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }





    public void fetchAuthenticatedUserData(){

    setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());


    }



    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;

    }
}
