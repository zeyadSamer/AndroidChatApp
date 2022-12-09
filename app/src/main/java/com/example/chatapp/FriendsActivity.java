package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chatapp.models.AuthenticatedUser;

import java.io.Serializable;

public class FriendsActivity extends AppCompatActivity implements Serializable {


    AuthenticatedUser authenticatedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
         Intent i= getIntent();
        authenticatedUser = (AuthenticatedUser) i.getSerializableExtra("authUser");



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
}