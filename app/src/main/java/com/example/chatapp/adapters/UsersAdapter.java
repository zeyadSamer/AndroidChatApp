package com.example.chatapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.models.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {



    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    class UserHolder extends RecyclerView.ViewHolder{

        public UserHolder(@NonNull View itemView) {
            super(itemView);
        }



    }
}
