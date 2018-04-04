package com.example.kartikmishra.instagramc.Likes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by KARTIK MISHRA on 26-03-2018.
 */

public class LikesActivity extends AppCompatActivity{
    private static final String TAG = "LikesActivity";
    private Context mContext = LikesActivity.this;
    private static final int ACTIVITY_INT = 3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: started");

        setUpBottomNavigationView();
    }

    private void setUpBottomNavigationView(){
        Log.d("setUpbottomNavigationView","setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view);
        BottomNavigationViewHelper.setUpBottomNavigationViewEx(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_INT);
        menuItem.setChecked(true);

    }
}
