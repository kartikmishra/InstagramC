package com.example.kartikmishra.instagramc.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContext;
    private String email,username,password;
    private EditText mEmail,mPassword,mUsername;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String append = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);

        initWidgets();
        setUpFirebaseAuth();
        init();
    }

    private boolean checkInputs(String email,String username,String password){
        Log.d(TAG, "checkInputs: checking Inputs for null values.");
        if(email.equals("") || username.equals("") || password.equals("")){

            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }else{

            return true;
        }


    }

    private void init(){

      btnRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             email= mEmail.getText().toString();
             password = mPassword.getText().toString();
             username = mUsername.getText().toString();
             if(checkInputs(email,username,password)){

                 progressBar.setVisibility(View.VISIBLE);
                 loadingPleaseWait.setVisibility(View.VISIBLE);

                 firebaseMethods.registerNewEmail(email,username,password);
             }

          }
      });

    }

    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing Widgets");
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mUsername = (EditText) findViewById(R.id.input_name);
        btnRegister =(Button) findViewById(R.id.button_register);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        loadingPleaseWait = (TextView) findViewById(R.id.pleaseWait);
        mContext = RegisterActivity.this;
        progressBar.setVisibility(View.GONE);
        loadingPleaseWait.setVisibility(View.GONE);

    }


    private boolean isStringNull(String string) {

        Log.d(TAG, "isStringNull: checking if string is null");
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }


    }
    /**
     * ********************************Firebase********************************
     */

    private void setUpFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null){
                    Log.d(TAG, "onAuthStateChanged: user_signedIn"+ user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //ist check:Make sure the username is not already not in use
                            if(firebaseMethods.checkIfUsernameAlreadyExists(username,dataSnapshot)){

                                         append = myRef.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username already exists.Appending random string to name: "+ append);
                            }
                            username = username + append;

                            //add a new user to the database
                             firebaseMethods.addNewUser(email,username,"","","");
                            Toast.makeText(mContext, "SignUp successful,Sending verification email", Toast.LENGTH_SHORT).show();
                             mAuth.signOut();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                }else {

                    Log.d(TAG, "onAuthStateChanged: user_signedOut");
                }
            }
        };
       // mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!= null){

            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
