package com.example.chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.models.AuthenticatedUser;

public class Profile extends AppCompatActivity  {


    Button logOutButton;
    TextView userEmailTextView;
    ImageView profilePicture;
    AuthenticatedUser authenticatedUser;

    Button uploadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logOutButton=findViewById(R.id.buttonLogOut);
        userEmailTextView=findViewById(R.id.textUserEmail);
        profilePicture=findViewById(R.id.profile_img);
        uploadButton =findViewById(R.id.buttonUploadImage);

        //getCurrentUserImage();

        authenticatedUser=(AuthenticatedUser) getIntent().getSerializableExtra("authenticatedUser");

        authenticatedUser.settings.setActivity(this);

        //just incase we automatically signedin
        authenticatedUser.fetchAuthenticatedUserEmail();

        Log.d("profile","email:"+authenticatedUser.email);


        userEmailTextView.setText(authenticatedUser.email);


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticatedUser.signOut();
                Intent i=new Intent(Profile.this,MainActivity.class);
                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });



       profilePicture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                  authenticatedUser.settings.getPhotoFromUserDevice();
           }

       });

      uploadButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            authenticatedUser.settings.uploadImage();
          }
      });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
 //           imagePath=data.getData();
            authenticatedUser.settings.setImagePath(data.getData());
            getImageInImageView();

        }



    }


    public void getImageInImageView(){
        Bitmap bitmap=authenticatedUser.settings.createImageBitmap();
        profilePicture.setImageBitmap(bitmap);

    }

//   public void getCurrentUserImage(){
//       String url=FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profilePic").toString();
//      imagePath=Uri.parse(url);
////      getImageInImageView();
//
//
//   }





}