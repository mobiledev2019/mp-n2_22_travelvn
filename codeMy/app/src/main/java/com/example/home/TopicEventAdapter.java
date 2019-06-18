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
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.bottomnavigation.ItemClickListener;
import com.example.model.Category;
import com.example.model.TopicEvent;
import com.example.model.Travel;

import java.util.ArrayList;
import java.util.List;

public class TopicEventAdapter  extends RecyclerView.Adapter<TopicEventViewHolder> {
    private Context mContext;
    private ArrayList<TopicEvent> listTopicEvent;


    TopicEventAdapter(Context mContext, ArrayList<TopicEvent> listTopic) {
        this.mContext = mContext;
        this.listTopicEvent = listTopic;
    }


    @Override
    public TopicEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_event, parent, false);
        return new TopicEventViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final TopicEventViewHolder holder, int position) {
        holder.mImage.setImageResource(listTopicEvent.get(position).getImage());
        holder.mTopic.setText(listTopicEvent.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(mContext, "Long Click: "+listTopicEvent.get(position).getName(), Toast.LENGTH_SHORT).show();
                Log.d("event","Long Click: "+listTopicEvent.get(position).getName() );}
                else{

                    Toast.makeText(mContext, " click "+ listTopicEvent.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Log.d("event","Long Click: "+listTopicEvent.get(position).getName() );
//                Intent intent = new Intent(mContext, CategoryTravel.class);
//                intent.putExtra(ID_TRAVEL, listTopicEvent.get(position).getId());

//                mContext.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTopicEvent.size();
    }
}
class TopicEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    ImageView mImage;
    TextView mTopic;
    private ItemClickListener itemClickListener; // Khai báo interface

    TopicEventViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.imageTopicEvent);
        mTopic = itemView.findViewById(R.id.topic);

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
