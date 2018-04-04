package com.example.kartikmishra.instagramc.Profile;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.utils.BottomNavigationViewHelper;
import com.example.kartikmishra.instagramc.utils.GridImageAdapter;
import com.example.kartikmishra.instagramc.utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by KARTIK MISHRA on 26-03-2018.
 */

public class ProfileActivity extends AppCompatActivity{
    private static final String TAG = "ProfileActivity";

    private Context mContext = ProfileActivity.this;
    private static final int ACTIVITY_INT = 4;
    private ProgressBar progressBar;
    private ImageView profilePhoto;
    private  static final int NUM_GRID_COLUMNS = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");


          init();


//        setUpBottomNavigationView();
//        setUpToolbar();
//       setUpActivityWidgets();
//       setProfileImage();
//
//       tempGridSetup();
//
        }

        private void init(){
            Log.d(TAG, "init: " +getString(R.string.profile_fragment) );

            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,fragment);
            transaction.addToBackStack(getString(R.string.profile_fragment));
            transaction.commit();
        }
//
//    private void setUpToolbar() {
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
//        setSupportActionBar(toolbar);
//
//        ImageView profileMenu=(ImageView) findViewById(R.id.profileMenu);
//        profileMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext,AccountSettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void setProfileImage(){
//
//        String imageURL = "www.android.com/static/2016/img/share/oreo-lg.jpg";
//        UniversalImageLoader.setImage(imageURL,profilePhoto,null,"https://");
//
//    }
//
//    private void tempGridSetup(){
//
//        ArrayList<String> imageURLs = new ArrayList<>();
//        imageURLs.add("https://www.android.com/static/2016/img/share/oreo-lg.jpg");
//        imageURLs.add("https://images.pexels.com/photos/33109/fall-autumn-red-season.jpg");
//        imageURLs.add("https://images.pexels.com/photos/45170/kittens-cat-cat-puppy-rush-45170.jpeg");
//        imageURLs.add("https://images.pexels.com/photos/66898/elephant-cub-tsavo-kenya-66898.jpeg");
//        imageURLs.add("https://images.pexels.com/photos/288621/pexels-photo-288621.jpeg");
//        imageURLs.add("https://images.pexels.com/photos/60023/baboons-monkey-mammal-freeze-60023.jpeg");
//        imageURLs.add("https://images.pexels.com/photos/66898/elephant-cub-tsavo-kenya-66898.jpeg");
//        imageURLs.add("https://images.pexels.com/photos/288621/pexels-photo-288621.jpeg");
//        imageURLs.add("https://images.pexels.com/photos/60023/baboons-monkey-mammal-freeze-60023.jpeg");
//        imageURLs.add("https://www.android.com/static/2016/img/share/oreo-lg.jpg");
//        imageURLs.add("https://images.pexels.com/photos/33109/fall-autumn-red-season.jpg");
//        imageURLs.add("https://images.pexels.com/photos/45170/kittens-cat-cat-puppy-rush-45170.jpeg");
//
//
//
//
//        setUpImageGrid(imageURLs);
//
//    }
//
//    public void setUpImageGrid(ArrayList<String> imageURLs){
//        GridView gridView = (GridView) findViewById(R.id.gridView);
//
//
//        int gridWidth = getResources().getDisplayMetrics().widthPixels;
//        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//        gridView.setColumnWidth(imageWidth);
//        GridImageAdapter gridImageAdapter =  new GridImageAdapter(mContext,R.layout.layout_grid_imageview,"",imageURLs);
//        gridView.setAdapter(gridImageAdapter);
//
//        }
//
//    private void setUpActivityWidgets(){
//
//
//        progressBar = (ProgressBar) findViewById(R.id.profile_progressBar);
//        progressBar.setVisibility(View.GONE);
//        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
//
//    }
//
//    private void setUpBottomNavigationView(){
//        Log.d("setUpbottomNavigationView","setting up bottom navigation view");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view);
//        BottomNavigationViewHelper.setUpBottomNavigationViewEx(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
//
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_INT);
//        menuItem.setChecked(true);
//
//    }

}
