package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.models.User;

public class MainActivity extends AppCompatActivity {

    EditText usernameView;
    EditText emailView;
    EditText passwordView ;
    Button signButton ;
    TextView textAccountCheckerView;

    boolean haveAccount=false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         usernameView=findViewById(R.id.username);
         emailView=findViewById(R.id.user_email);
         passwordView = findViewById(R.id.user_password);
         signButton = findViewById(R.id.button);
         textAccountCheckerView= findViewById(R.id.have_account);



         if(haveAccount){
             signButton.setText("Log In");
             textAccountCheckerView.setText("Don't have an account ? SignUp");



         }else{
             signButton.setText("Sign Up");
             textAccountCheckerView.setText("Already have an account ? LogIn");




         }


         textAccountCheckerView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 haveAccount=!haveAccount;
                 if(haveAccount){


                     usernameView.setVisibility(View.GONE);
                     signButton.setText("Log In");
                     textAccountCheckerView.setText("Don't have an account ? SignUp");



                 }else{
                     usernameView.setVisibility(View.VISIBLE);
                     signButton.setText("Sign Up");
                     textAccountCheckerView.setText("Already have an account ? LogIn");




                 }
             }
         });






        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=usernameView.getText().toString();
                String email=emailView.getText().toString();
                String password=passwordView.getText().toString();

                User user =new User(username,email,password);

                user.postUserData();


            }
        });


    }







}