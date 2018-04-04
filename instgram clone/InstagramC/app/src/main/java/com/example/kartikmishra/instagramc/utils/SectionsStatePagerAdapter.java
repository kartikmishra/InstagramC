package com.example.kartikmishra.instagramc.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KARTIK MISHRA on 30-03-2018.
 */

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mfragmentList = new ArrayList<>();
    private final HashMap<Fragment,Integer> mFragments = new HashMap<>();
    private final  HashMap<String,Integer> mFragmentsNumbers = new HashMap<>();
    private final HashMap<Integer,String> mFragmentsName = new HashMap<>();
    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }

    public void addFragments(Fragment fragment,String fragmentName){
         mfragmentList.add(fragment);
         mFragments.put(fragment,mfragmentList.size()-1);
         mFragmentsNumbers.put(fragmentName,mfragmentList.size()-1);
         mFragmentsName.put(mfragmentList.size()-1,fragmentName);

    }

    public Integer getFragmentNumber(String fragmentName){

        if(mFragmentsName.containsKey(fragmentName)){

            return mFragmentsNumbers.get(fragmentName);

        }else{
            return null;
        }
    }
    public Integer getFragmentNumber(Fragment fragment){

        if(mFragmentsName.containsKey(fragment)){

            return mFragmentsNumbers.get(fragment);

        }else{
            return null;
        }
    }

    public String getFragmentName(Integer fragmentNumber){

        if(mFragmentsName.containsKey(fragmentNumber)){

            return mFragmentsName.get(fragmentNumber);

        }else{
            return null;
        }
    }
}
