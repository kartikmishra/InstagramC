package com.example.kartikmishra.instagramc.Profile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kartikmishra.instagramc.Login.LoginActivity;
import com.example.kartikmishra.instagramc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by KARTIK MISHRA on 30-03-2018.
 */

public class SignOutFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "SignOutFragment";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private TextView tvSignOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signout,container,false);
        tvSignOut =(TextView) view.findViewById(R.id.tvSignout);
        mProgressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);

        tvSignOut.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        setUpFirebaseAuth();

        Button btnSignOut = (Button) view.findViewById(R.id.btnConfirmSignout);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sign out");
                tvSignOut.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mAuth.signOut();
                getActivity().finish();
            }
        });

        return view;

    }


    /**
     * ******************Firebase**********************************
     */


    private void setUpFirebaseAuth() {
        Log.d(TAG, "setUpFirebaseAuth: setting up firebase auth");
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null){
                    Log.d(TAG, "onAuthStateChanged: user_signedIn"+ user.getUid());
                }else {
                    Log.d(TAG, "onAuthStateChanged: user_signedOut");
                    Log.d(TAG, "onAuthStateChanged: navigating back to login activity");
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);



    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener!= null){

            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
