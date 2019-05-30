package com.bignerdranch.myrxmeizi.adapter;

import android.bignerdranch.myrxmeizi.R;
import com.bignerdranch.myrxmeizi.bean.Stories;
import com.bignerdranch.myrxmeizi.ui.activity.NewsDetailActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder>
{

    private List<Stories> storiesList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_Title;
        ImageView ivImg;
        View storiesView;
        MyViewHolder(View itemView)
        {
            super(itemView);
            storiesView=itemView;
            tv_Title=(TextView)itemView.findViewById(R.id.tv_title);
            ivImg=(ImageView)itemView.findViewById(R.id.iv_img);
        }
    }

    public StoriesAdapter(Context context,List<Stories> storiesList)
    {
        this.storiesList=storiesList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


       View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_stories_card, viewGroup, false);
       final MyViewHolder holder=new MyViewHolder(view);
       holder.storiesView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int position=holder.getAdapterPosition();
               Stories stories=storiesList.get(position);
               NewsDetailActivity.start(context,stories);
           }
       });
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Stories stories=storiesList.get(i);
        myViewHolder.tv_Title.setText(stories.getTitle());
        Glide.with(context).load(stories.getImages().get(0)).into(myViewHolder.ivImg);
    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }
}
