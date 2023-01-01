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

import com.example.chatapp.adapters.UsersAdapter;
import com.example.chatapp.models.AuthenticatedUser;

import java.io.Serializable;

public class FriendsActivity extends AppCompatActivity implements Serializable {


   private AuthenticatedUser authenticatedUser;
    private RecyclerView recyclerView;
//    private ArrayList<Friend> friends;
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
       // authenticatedUser.fetchAuthenticatedUserData();
        authenticatedUser.fetchDataAndFriends();


        userClickListener=new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {

                Intent i=new Intent(FriendsActivity.this,MessageActivity.class);
                i.putExtra("authenticatedUser",authenticatedUser);
                i.putExtra("usernameOfRoomMate",authenticatedUser.friends.get(position).username);
                i.putExtra("emailOfRoomMate",authenticatedUser.friends.get(position).email);
                i.putExtra("imgOfRoomMate",authenticatedUser.friends.get(position).profilePic);
                i.putExtra("myImg",authenticatedUser.profilePic);
                i.putExtra("myUsername",authenticatedUser.username);

                startActivity(i);
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





  private void getUsers(){

      usersAdapter=new UsersAdapter(FriendsActivity.this, authenticatedUser.friends,userClickListener);
      recyclerView.setLayoutManager(new LinearLayoutManager(FriendsActivity.this));
      recyclerView.setAdapter(usersAdapter);
      progressBar.setVisibility(View.GONE);
      recyclerView.setVisibility(View.VISIBLE);
      authenticatedUser.fetchDataAndFriends();



}}