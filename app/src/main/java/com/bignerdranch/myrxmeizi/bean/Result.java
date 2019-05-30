package com.bignerdranch.myrxmeizi.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 2016/11/24.
 * Api返回结果
 */

public class Result implements Serializable {
    //日期
    @SerializedName("date")
    private String mDate;
    //消息
    @SerializedName("stories")
    private List<Stories> mStories;
    //顶部消息
    @SerializedName("top_stories")
    private List<TopStories> mTopStories;

    public Result() {
        mStories = new ArrayList<>();
        mTopStories = new ArrayList<>();
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getDate() {
        return mDate;
    }

    public List<TopStories> getTopStories() {
        return mTopStories;
    }

    public List<Stories> getStories() {
        return mStories;
    }

    public void setStories(List<Stories> mStories) {
        this.mStories = mStories;
    }

    public void setTopStories(List<TopStories> mTopStories) {
        this.mTopStories = mTopStories;
    }

    @Override
    public String toString() {
        return "Result{" +
                "mDate='" + mDate + '\'' +
                ", mStories=" + mStories.toString() +
                ", mTopStories=" + mTopStories.toString() +
                '}';
    }
}
