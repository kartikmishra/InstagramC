package com.example.kartikmishra.instagramc.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.kartikmishra.instagramc.Login.LoginActivity;
import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.models.User;
import com.example.kartikmishra.instagramc.models.UserSettings;
import com.example.kartikmishra.instagramc.models.User_account_settings;
import com.example.kartikmishra.instagramc.utils.BottomNavigationViewHelper;
import com.example.kartikmishra.instagramc.utils.FirebaseMethods;
import com.example.kartikmishra.instagramc.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private static final int ACTIVITY_INT = 4;


    private TextView mPosts,mFollowers,mFollowing,mDisplayName,mUsername,mWebsite,mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfileImage;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private BottomNavigationViewEx bottomNavigationView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        mDisplayName = (TextView) view.findViewById(R.id.display_name);
        mUsername = (TextView) view.findViewById(R.id.username);
        mWebsite = (TextView) view.findViewById(R.id.website);
        mDescription = (TextView) view.findViewById(R.id.description);
        mFollowers = (TextView) view.findViewById(R.id.textFollowers);
        mPosts = (TextView) view.findViewById(R.id.tvPosts);
        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        mProgressBar = (ProgressBar) view.findViewById(R.id.profile_progressBar);
        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_photo);
        profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
        gridView = (GridView)view.findViewById(R.id.gridView);
        toolbar = (Toolbar) view.findViewById(R.id.profile_toolbar);
        bottomNavigationView = (BottomNavigationViewEx) view.findViewById(R.id.bottom_nav_view);
        mFirebaseMethods = new FirebaseMethods(getContext());
        mContext = getActivity();
        Log.d(TAG, "onCreateView: started.");


        setUpBottomNavigationView();
        setUpToolbar();

        setUpFirebaseAuth();

        return view;
    }


    private void setUpProfileWidgets(UserSettings userSettings){
        //Log.d(TAG, "setUpProfileWidgets: setting widgets with data retrieved " + userSettings.toString());

        //User user = userSettings.getUser();
        User_account_settings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(),mProfileImage,null,"");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
        mProgressBar.setVisibility(View.GONE);


    }




    private void setUpToolbar() {

            ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setUpBottomNavigationView(){
       Log.d("setUpbottomNavigationView","setting up bottom navigation view");
        BottomNavigationViewHelper.setUpBottomNavigationViewEx(bottomNavigationView);
       BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
       MenuItem menuItem = menu.getItem(ACTIVITY_INT);
        menuItem.setChecked(true);

    }

    /**
     * ******************Firebase**********************************
     */


    private void setUpFirebaseAuth() {
        Log.d(TAG, "setUpFirebaseAuth: setting up firebase auth");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                setUpProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));



                //retrieve images for the user in question


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
