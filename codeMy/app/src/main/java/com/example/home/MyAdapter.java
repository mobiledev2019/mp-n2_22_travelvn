package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.model.Category;
import com.example.bottomnavigation.ItemClickListener;
import com.example.model.Travel;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private Context mContext;
    private List<Category> categories;
    public static final String ID_TRAVEL = "com.example.application.example.ID_TRAVEL";
    public static final ArrayList<Travel> TRAVELS = new ArrayList<>();

    MyAdapter(Context mContext, List< Category > categories) {
        this.mContext = mContext;
        this.categories = categories;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single, parent, false);
        return new CategoryViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
        holder.mImage.setImageResource(categories.get(position).getImage());
        holder.mTitle.setText(categories.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(mContext, "Long Click: "+categories.get(position).getName(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext, " click "+ categories.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, CategoryTravel.class);
                intent.putExtra(ID_TRAVEL, categories.get(position).getId());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}


class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    ImageView mImage;
    TextView mTitle;
    private ItemClickListener itemClickListener; // Khai báo interface

    CategoryViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.grid_image);
        mTitle = itemView.findViewById(R.id.grid_text);

        itemView.setOnClickListener(this); // Mấu chốt ở đây , set sự kiên onClick cho View
        itemView.setOnLongClickListener(this); // Mấu chốt ở đây , set sự kiên onLongClick cho View
    }
    //Tạo setter cho biến itemClickListenenr
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false); // Gọi interface , false là vì đây là onClick
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true); // Gọi interface , true là vì đây là onLongClick
        return true;    }

}
