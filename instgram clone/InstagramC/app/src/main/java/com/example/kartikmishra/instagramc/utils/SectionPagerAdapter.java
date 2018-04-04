package com.example.kartikmishra.instagramc.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kartikmishra.instagramc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KARTIK MISHRA on 26-03-2018.
 */

public class SectionPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

    private static final String TAG = "SectionPagerAdapter";

    private  final List<Fragment> mFragmentList = new ArrayList<>();

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {return mFragmentList.get(position);}

    @Override
    public int getCount() {return mFragmentList.size();}

    public void addFragment(Fragment fragment){ mFragmentList.add(fragment);}


}
