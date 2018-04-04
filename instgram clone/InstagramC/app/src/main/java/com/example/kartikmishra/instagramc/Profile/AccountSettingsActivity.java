package com.example.kartikmishra.instagramc.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.utils.BottomNavigationViewHelper;
import com.example.kartikmishra.instagramc.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by KARTIK MISHRA on 27-03-2018.
 */

public class AccountSettingsActivity extends AppCompatActivity {

    private static final String TAG = "AccountSettingsActivity";
    private Context mContext = AccountSettingsActivity.this;
    private static final int ACTIVITY_ = 0;
    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accountsettings);
        mViewPager = (ViewPager) findViewById(R.id.container);
        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);
       setUpFragments();

        ImageView accountSettinsImageView = (ImageView) findViewById(R.id.backarrow);
        accountSettinsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                }
        });


        ListView listView = (ListView) findViewById(R.id.listView_accountSettings);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(getString(R.string.Edit_Profile));
        arrayList.add(getString(R.string.Sign_out));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        setUpBottomNavigationView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setUpViewPager(position);
            }
        });

    }

    private void setUpFragments(){

        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new EditProfileFragment(),getString(R.string.Edit_Profile));
        pagerAdapter.addFragments(new SignOutFragment(),getString(R.string.Sign_out));
    }

    private void setUpViewPager(int fragmentNumber){

        relativeLayout.setVisibility(View.GONE);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);

    }

    private void setUpBottomNavigationView() {
        Log.d("setUpbottomNavigationView", "setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view);
        BottomNavigationViewHelper.setUpBottomNavigationViewEx(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_);
        menuItem.setChecked(true);


    }
}