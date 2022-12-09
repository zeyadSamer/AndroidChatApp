package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.models.AuthenticatedUser;
import com.example.chatapp.models.RegisteringUser;

public class MainActivity extends AppCompatActivity {

    EditText usernameView;
    EditText emailView;
    EditText passwordView ;
    Button signButton ;
    TextView textAccountCheckerView;
    boolean isSigningUp=true;

    boolean haveAccount=false;
    RegisteringUser registeringUser;

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
        //Parameter boolean nonRoot - If false then this only works if the activity is the root of a task; if true it will work for any activity in a task.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         usernameView=findViewById(R.id.username);
         emailView=findViewById(R.id.user_email);
         passwordView = findViewById(R.id.user_password);
         signButton = findViewById(R.id.button);
         textAccountCheckerView= findViewById(R.id.have_account);
        if(AuthenticatedUser.isThereCurrentUser()){

            Intent i=new Intent(this,FriendsActivity.class);

            AuthenticatedUser authenticatedUser=new AuthenticatedUser();
            i.putExtra("authUser",authenticatedUser);

            startActivity(i);

            finish();

        }



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
                     isSigningUp=false;


                 }else{

                     usernameView.setVisibility(View.VISIBLE);
                     signButton.setText("Sign Up");
                     textAccountCheckerView.setText("Already have an account ? LogIn");
                     isSigningUp=true;



                 }
             }
         });






        signButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                if(isSigningUp){
                    handleSignup();
                }else{
                    handleLogIn();
                }






            }
        });


    }


    private  void handleSignup(){
        String username=usernameView.getText().toString();
        String email=emailView.getText().toString();
        String password=passwordView.getText().toString();
        if(password.isEmpty() || email.isEmpty() || ( username.isEmpty() &&  isSigningUp)){

            Toast.makeText(MainActivity.this,"Invalid input",Toast.LENGTH_LONG).show();
            return ;
        }
        registeringUser =new RegisteringUser(username,email,password);
        registeringUser.setCurrentActivity( this);
        registeringUser.setNextActivity(new FriendsActivity());
        registeringUser.signUp();

    }

    private void handleLogIn(){

        String email=emailView.getText().toString();
        String password=passwordView.getText().toString();

        if(password.isEmpty() || email.isEmpty()){

            Toast.makeText(MainActivity.this,"Invalid input",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            registeringUser =new RegisteringUser("",email,password);
            registeringUser.setCurrentActivity(this);

            registeringUser.setNextActivity(new FriendsActivity());


            registeringUser.logIn();


        }










    }





}