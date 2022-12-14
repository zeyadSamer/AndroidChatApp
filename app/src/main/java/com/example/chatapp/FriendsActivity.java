package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.adapters.UsersAdapter;
import com.example.chatapp.models.AuthenticatedUser;
import com.example.chatapp.models.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FriendsActivity extends AppCompatActivity implements Serializable {


   private AuthenticatedUser authenticatedUser;
    private RecyclerView recyclerView;
    private ArrayList<Friend> friends;
    private ProgressBar progressBar;
    private  UsersAdapter usersAdapter;
    private UsersAdapter.OnUserClickListener userClickListener;

    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
         Intent i= getIntent();
        authenticatedUser = (AuthenticatedUser) i.getSerializableExtra("authUser");
        recyclerView =findViewById(R.id.recycleView);
        progressBar =findViewById(R.id.progressBar);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        authenticatedUser.fetchAuthenticatedUserData();
        authenticatedUser.fetchFriends();


        friends = new ArrayList<>();

        userClickListener=new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {
                Toast.makeText(FriendsActivity.this,"userClicked:"+friends.get(position).username,Toast.LENGTH_LONG).show();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                getUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


       getUsers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_item_profile){
            Intent i=new Intent(FriendsActivity.this,Profile.class);
            i.putExtra("authenticatedUser",authenticatedUser);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }





  private  void getUsers(){



      synchronized (this){


      usersAdapter=new UsersAdapter(FriendsActivity.this, authenticatedUser.friends,userClickListener);
      recyclerView.setLayoutManager(new LinearLayoutManager(FriendsActivity.this));
      recyclerView.setAdapter(usersAdapter);
      progressBar.setVisibility(View.GONE);
      recyclerView.setVisibility(View.VISIBLE);
          authenticatedUser.fetchFriends();



     ;



  }









}}