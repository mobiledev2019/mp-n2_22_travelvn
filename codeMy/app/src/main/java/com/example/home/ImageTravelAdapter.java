package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.R;
import com.example.bottomnavigation.ItemClickListener;
import com.example.model.Travel;

import java.util.ArrayList;

public class ImageTravelAdapter extends RecyclerView.Adapter<ImageTravelViewHolder> {
    private Context mContext;
    private ArrayList<String> imageTravels;

    public ImageTravelAdapter(Context mContext, ArrayList<String> imageTravels) {
        this.mContext = mContext;
        this.imageTravels = imageTravels;
        for (int i = 0; i < imageTravels.size(); i++) {
            Log.d("ReadJson", "++++++++++++++++++++++++++++");
            Log.d("url image", imageTravels.get(i));
        }


    }
    @NonNull
    @Override
    public ImageTravelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_travel, viewGroup, false);
        return new ImageTravelViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageTravelViewHolder holder, int i) {
        Glide.with(mContext)
            .load(imageTravels.get(i))
            .into(holder.getImage());

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if (isLongClick)
//                    Toast.makeText(mContext, "Long Click: " + listTravels.get(position).getName(), Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(mContext, " click " + listTravels.get(position).getName(), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(mContext, com.example.home.Travel.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putStringArrayListExtra("IMAGE_TRAVEL", listTravels.get(position).getImageUrl());
//                mContext.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return imageTravels.size();
    }
}
class ImageTravelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    ImageView mImage;


    public ImageTravelViewHolder(@NonNull View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.image_travel_detail);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
    public ImageView getImage() {
        return this.mImage;
    }

}
