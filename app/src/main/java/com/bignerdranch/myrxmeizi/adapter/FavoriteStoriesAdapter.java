package com.bignerdranch.myrxmeizi.adapter;

import android.bignerdranch.myrxmeizi.R;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.myrxmeizi.bean.Stories;
import com.bignerdranch.myrxmeizi.ui.activity.NewsDetailActivity;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

public class FavoriteStoriesAdapter extends RecyclerView.Adapter<FavoriteStoriesAdapter.FavoriteViewHolder>
{

    private List<Stories> storiesList;
    private Context context;

    class FavoriteViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitleFv;
        ImageView ivImgFv;
        View storiesView;
        FavoriteViewHolder(View itemView)
        {
            super(itemView);
            storiesView=itemView;
            tvTitleFv=(TextView)itemView.findViewById(R.id.tv_title_fv);
            ivImgFv=(ImageView)itemView.findViewById(R.id.iv_img_fv);
        }
    }

    public FavoriteStoriesAdapter(Context context, List<Stories> storiesList)
    {
        this.storiesList=storiesList;
        this.context=context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_stories_card,viewGroup,false);
       final FavoriteViewHolder holder=new FavoriteViewHolder(view);
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
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i)
    {
        Stories stories=storiesList.get(i);
        favoriteViewHolder.tvTitleFv.setText(stories.getTitle());
        Glide.with(context).load(stories.getImages().get(0)).into(favoriteViewHolder.ivImgFv);
    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }
}
