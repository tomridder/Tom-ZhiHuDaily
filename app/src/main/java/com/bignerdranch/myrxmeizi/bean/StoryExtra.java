package com.bignerdranch.myrxmeizi.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/30.
 */
public class StoryExtra implements Serializable {

    private int popularity;

    @SerializedName("long_comments")
    private int longComments;

    @SerializedName("short_comments")
    private int shortComments;

    private int comments;

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getLongComments() {
        return longComments;
    }

    public void setLongComments(int longComments) {
        this.longComments = longComments;
    }

    public int getShortComments() {
        return shortComments;
    }

    public void setShortComments(int shortComments) {
        this.shortComments = shortComments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "StoryExtra{" +
                "popularity=" + popularity +
                ", longComments=" + longComments +
                ", shortComments=" + shortComments +
                ", comments=" + comments +
                '}';
    }
}
