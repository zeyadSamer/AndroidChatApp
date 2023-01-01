package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.adapters.MessageAdapter;
import com.example.chatapp.models.AuthenticatedUser;
import com.example.chatapp.models.Friend;
import com.example.chatapp.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MessageActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private EditText editMessageText;
    private TextView txtChattingWith;
    private ProgressBar progressBar;
    private ImageView friendImagetoolBar;
    private ImageView sendMessageImg;
   private AuthenticatedUser authenticatedUser;
    private Friend roomMate;

private MessageAdapter messageAdapter;
    private ArrayList<Message>messages;

    String roomMateUserName,chatRoomId,roomMateEmail,roomMateImg,myImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView=findViewById(R.id.recyclerMessages);
        editMessageText=findViewById(R.id.editText);
        txtChattingWith =findViewById(R.id.txt_chatting_with);
        progressBar=findViewById(R.id.progressMessagesBar);
        messages=new ArrayList<>();



        sendMessageImg=findViewById(R.id.imgSendMessage);
        friendImagetoolBar=findViewById(R.id.img_toolbar);

        authenticatedUser= (AuthenticatedUser) getIntent().getSerializableExtra("authenticatedUser");

        authenticatedUser.fetchDataAndFriends();

        roomMateEmail=getIntent().getStringExtra("emailOfRoomMate");

        roomMateImg=getIntent().getStringExtra("imgOfRoomMate");
        myImg=getIntent().getStringExtra("myImg");

        sendMessageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).push().setValue(new Message(authenticatedUser.email,roomMateEmail,editMessageText.getText().toString()));

            editMessageText.setText("");
            }
        });



        roomMateUserName=getIntent().getStringExtra("usernameOfRoomMate");
        Log.d("em","roomMateUsernamegot:"+roomMateUserName);

        txtChattingWith.setText(roomMateUserName);


        messageAdapter=new MessageAdapter(messages,myImg,roomMateImg,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);


        Glide.with(this).load(roomMateImg).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(friendImagetoolBar);


        setUpChatRoom();





    }





    private void setUpChatRoom(){

        FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String myusername= Objects.requireNonNull(snapshot.getValue(AuthenticatedUser.class)).username;

                if(roomMateUserName.compareTo(myusername)>=0){
                    chatRoomId=myusername+roomMateUserName;

                }else{

                    chatRoomId=roomMateUserName+myusername;

                }


                ListenForMessages(chatRoomId);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }



    private void ListenForMessages(String chatRoomId){
        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messages.clear();

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    messages.add(dataSnapshot.getValue(Message.class));


                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size()-1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



}