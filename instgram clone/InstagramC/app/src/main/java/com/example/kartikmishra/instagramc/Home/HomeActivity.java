package com.example.kartikmishra.instagramc.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kartikmishra.instagramc.Login.LoginActivity;
import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.utils.BottomNavigationViewHelper;
import com.example.kartikmishra.instagramc.utils.SectionPagerAdapter;
import com.example.kartikmishra.instagramc.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends AppCompatActivity  {

    private static final String TAG ="" ;
    private Context mContext = HomeActivity.this;
    private static final int ACTIVITY_INT = 0;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFirebaseAuth();

        initImageLoader();
        setUpBottomNavigationView();
        setUpViewpager();

       //mAuth.signOut();



    }



    private void initImageLoader(){

        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }


    /* ViewPager Setu */
    /*                 */


    private void setUpViewpager(){

        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CameraFragment());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MessagesFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_instagram);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);



    }






    /* Bottom navigation View Setup */
    /*                              */

    private void setUpBottomNavigationView(){
        Log.d("setUpbottomNavigationView","setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view);
        BottomNavigationViewHelper.setUpBottomNavigationViewEx(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_INT);
        menuItem.setChecked(true);



    }


    /**
     * ******************Firebase**********************************
     */

    private void checkCurrentUser(FirebaseUser user){

        Log.d(TAG, "checkCurrentUser: checking if user is logged in");
        if(user== null){

            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
    private void setUpFirebaseAuth() {
        Log.d(TAG, "setUpFirebaseAuth: setting up firebase auth");
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                checkCurrentUser(user);
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
        FirebaseUser user = mAuth.getCurrentUser();
        checkCurrentUser(user);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!= null){

            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



}
