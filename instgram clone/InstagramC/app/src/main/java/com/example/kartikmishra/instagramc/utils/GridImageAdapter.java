package com.example.kartikmishra.instagramc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.kartikmishra.instagramc.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImageAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int layoutResource;
    private String append;
    private ArrayList<String> imageURLs;

    public GridImageAdapter(Context mContext, int layoutResource, String append, ArrayList<String> imageURLs) {
        super(mContext,layoutResource,imageURLs);
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        this.layoutResource = layoutResource;
        this.append = append;
        this.imageURLs = imageURLs;
    }

    private static class ViewHolder{
             SquareImageView profileImage;
             ProgressBar mProgressBar;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView== null){

            convertView = layoutInflater.inflate(layoutResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.gridImageProgressBar);
            viewHolder.profileImage = (SquareImageView) convertView.findViewById(R.id.gridImageView);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String imageURLs = getItem(position);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(append + imageURLs, viewHolder.profileImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.mProgressBar!=null){
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.mProgressBar!=null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.mProgressBar!=null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.mProgressBar!=null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}
