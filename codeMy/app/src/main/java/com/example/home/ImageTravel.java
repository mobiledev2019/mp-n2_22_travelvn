package com.example.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.R;

import java.util.ArrayList;

public class ImageTravel  extends PagerAdapter {
    public Context context;
    public LayoutInflater inflater;
    int images[];
    public ArrayList<String> imageURL;

    public ImageTravel(Context context, ArrayList<String> images) {
        this.context = context;
        this.imageURL = images;

    }


    @Override
    public int getCount() {
        return imageURL.size();
    }

    //return ra view linearLayout
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slider,container,false);
        ImageView imageSlider = view.findViewById(R.id.imageSlider);
        Glide.with(context).load(imageURL.get(position)).into(imageSlider);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
