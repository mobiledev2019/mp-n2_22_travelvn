package com.example.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.R;

public class Slider extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    int images[];
    String[] imagesURL;
    public Slider(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    //return ra view linearLayout
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider,container,false);
        ImageView imageSlider = (ImageView) view.findViewById(R.id.imageSlider);
        imageSlider.setBackgroundResource(images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

}
