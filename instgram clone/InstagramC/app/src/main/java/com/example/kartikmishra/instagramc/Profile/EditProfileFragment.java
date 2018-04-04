package com.example.kartikmishra.instagramc.Profile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by KARTIK MISHRA on 30-03-2018.
 */

public class EditProfileFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "EditProfileFragment";
    private ImageView profilePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_editprofile,container,false);
        profilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

        setProfileImage();

        final ImageView editprofile = (ImageView) view.findViewById(R.id.backarrow);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;


    }

    private void setProfileImage(){

         String imageURL = "www.android.com/static/2016/img/share/oreo-lg.jpg";
        UniversalImageLoader.setImage(imageURL,profilePhoto,null,"https://");
    }
}
