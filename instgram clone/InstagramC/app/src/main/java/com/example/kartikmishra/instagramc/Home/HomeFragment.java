package com.example.kartikmishra.instagramc.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikmishra.instagramc.R;

/**
 * Created by KARTIK MISHRA on 26-03-2018.
 */

public class HomeFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "HomeFragment";

    public  static HomeFragment create(){

        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }
}
