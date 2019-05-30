package com.bignerdranch.myrxmeizi.net;

import com.bignerdranch.myrxmeizi.bean.NewsDetail;
import com.bignerdranch.myrxmeizi.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ClientApi
{
    @GET("news/latest")
    Observable<Result> getTodayNews();

    @GET("news/{id}")
    Observable<NewsDetail> getDetailNews(@Path("id") long id);

    @GET("news/before/{date}")
    Observable<Result> getBeforeNews(@Path("date") String date);
}
