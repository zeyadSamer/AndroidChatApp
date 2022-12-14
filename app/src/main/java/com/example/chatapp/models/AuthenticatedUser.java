package com.example.chatapp.models;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatapp.FriendsActivity;
import com.example.chatapp.adapters.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class AuthenticatedUser extends RegisteredUser implements Serializable {


    public ArrayList<Friend> friends;


  @Exclude public UserSettings settings;

      public AuthenticatedUser() {
        super();
        this.fetchAuthenticatedUserData();
        settings=new UserSettings();
          //friends= new ArrayList<>();

      }

    public AuthenticatedUser(String username, String email,String password,String profilePic) {
        super(username, email,password,profilePic);
        this.fetchAuthenticatedUserData();
        settings=new UserSettings();
       // friends= new ArrayList<>();


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






   @Exclude public synchronized void fetchFriends(){



          friends=new ArrayList<>();
          Object lock=new Object();

        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren()){


                    // in order not to add current user in list of friends
                    if(!Objects.equals(Objects.requireNonNull(dataSnapshot.getValue(Friend.class)).email, AuthenticatedUser.this.email)) {
                     friends.add(dataSnapshot.getValue(Friend.class));
                    }



                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });






   }





}
