package com.example.chatapp.models;

import android.app.Activity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public abstract class User {
    public String username;
     public String email;
      public String password;

   @Exclude private Activity currentActivity;
    @Exclude private Activity nextActivity;



    public User(){

    }
    public User(String username,String email,String password) {
        this.username = username;
        this.email=email;
        this.password=password;
    }



    @Exclude public Activity getCurrentActivity() {
        return currentActivity;
    }

    @Exclude public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Exclude public Activity getNextActivity() {
        return nextActivity;
    }

    @Exclude public void setNextActivity(Activity nextActivity) {
        this.nextActivity = nextActivity;
    }


}
