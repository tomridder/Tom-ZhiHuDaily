package com.bignerdranch.myrxmeizi.adapter;

import android.bignerdranch.myrxmeizi.R;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.myrxmeizi.bean.Comment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ShortCommentViewHolder>
{
    private static final int COMMENT=0;
    private static final int COMMENT_WITH_REPLY=1;
    private LayoutInflater inflater;
    private ArrayList<Comment> comments=new ArrayList<>();
    private Context ctx;

    public CommentsAdapter(Context ctx)
    {
        this.ctx=ctx;
        inflater=LayoutInflater.from(ctx);
    }

    public void addList(ArrayList<Comment> comments)
    {
        this.comments.addAll(comments);
    }

    @NonNull
    @Override
    public ShortCommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==COMMENT)
        {
            return new ShortCommentViewHolder(inflater.inflate(R.layout.item_short_comment,null),COMMENT);
        }else
        {
            return new ShortCommentViewHolder(inflater.inflate(R.layout.item_short_comment_with_reply,null),COMMENT_WITH_REPLY);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ShortCommentViewHolder shortCommentViewHolder, int i)
    {
        Comment c=comments.get(i);
        Glide.with(ctx).load(c.getAvatar()).asBitmap().centerCrop().into(shortCommentViewHolder.ivAvatar);
        shortCommentViewHolder.tvAuthor.setText(c.getAuthor());
        shortCommentViewHolder.tvContent.setText(c.getContent());
        shortCommentViewHolder.tvTime.setText(c.getTimeStr());
        shortCommentViewHolder.tvLikes.setText(String.valueOf(c.getLikes()));
        if(c.getReplyTo()!=null)
        {
            String replyAnthor="@"+c.getReplyTo().getAuthor()+":";
            shortCommentViewHolder.tvReplyAuthor.setText(replyAnthor);
            shortCommentViewHolder.tvReplyContent.setText(replyAnthor+c.getReplyTo().getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(comments.get(position).getReplyTo()==null)
        {
            return COMMENT;
        }else
        {
            return COMMENT_WITH_REPLY;
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ShortCommentViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.ivAvatar)
        ImageView ivAvatar;
        @Bind(R.id.tvAuthor)
        TextView tvAuthor;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvLikes)
        TextView tvLikes;
        @Nullable
        @Bind(R.id.tvReplyAuthor)
        TextView tvReplyAuthor;
        @Nullable
        @Bind(R.id.tvReplyContent)
        TextView tvReplyContent;

        public ShortCommentViewHolder(View itemView,int type)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }


}
