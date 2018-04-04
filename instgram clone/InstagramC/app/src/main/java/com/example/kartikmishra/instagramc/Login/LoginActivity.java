package com.example.kartikmishra.instagramc.Login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.example.kartikmishra.instagramc.Home.HomeActivity;
import com.example.kartikmishra.instagramc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar progressBar;
    private EditText mEmail,mPassword;
    private TextView mPleaseWait;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.loginRequestLoadingProgressBar);
        mPleaseWait = (TextView) findViewById(R.id.loginRequesTextView);
        mEmail = (EditText)findViewById(R.id.input_email);
        mPassword =(EditText) findViewById(R.id.input_password);
        mContext = LoginActivity.this;

        mPleaseWait.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        setUpFirebaseAuth();
        init();
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
     * ******************Firebase**********************************
     */

    private void init(){

        //initialize the button for logging
        Button loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: attempting logging");

                String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if(isStringNull(email) && isStringNull(password)){

                    Toast.makeText(mContext, "Fields Can't be Empty!", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    mPleaseWait.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    Log.d(TAG, "signInWithEmail:success" + task.isSuccessful());
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (!task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "onFailed: "+task.getException());
                                        Toast.makeText(mContext,getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        mPleaseWait.setVisibility(View.GONE);

                                    }else { try {
                                        if(user.isEmailVerified()){
                                            Log.d(TAG, "onComplete: success,email is verified.");
                                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                            startActivity(intent);

                                        }else{
                                            Toast.makeText(mContext, "Email is not verified \n check your inbox", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            mPleaseWait.setVisibility(View.GONE);
                                            mAuth.signOut();
                                        }

                                    }catch (NullPointerException e){
                                        Log.d(TAG, "onComplete: NullPointerException" + e.getMessage());
                                    }

                                    }
                                }
                            });

                }

            }
        });


        TextView linkSignUp =(TextView) findViewById(R.id.link_signUp);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RegisterActivity.class);
                startActivity(intent);
            }
        });

        if(mAuth.getCurrentUser() != null){

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
    /**
     * ******************Firebase**********************************
     */




    private void setUpFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if(user!=null){
                    Log.d(TAG, "onAuthStateChanged: user_signedIn"+ user.getUid());
                }else {

                    Log.d(TAG, "onAuthStateChanged: user_signedOut");
                }
            }
        };
       //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){

           mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
