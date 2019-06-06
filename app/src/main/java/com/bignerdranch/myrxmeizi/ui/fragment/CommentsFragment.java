package com.bignerdranch.myrxmeizi.ui.fragment;

import android.bignerdranch.myrxmeizi.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.myrxmeizi.adapter.CommentsAdapter;
import com.bignerdranch.myrxmeizi.bean.Comment;
import com.bignerdranch.myrxmeizi.bean.CommentJson;
import com.bignerdranch.myrxmeizi.bean.StoryExtra;
import com.bignerdranch.myrxmeizi.net.ClientApi;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentsFragment  extends Fragment
{

    RecyclerView rvComments;
    private ArrayList<Comment> comments;
    private CommentsAdapter commentsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private long id;
    private StoryExtra storyExtra;
    private String url;
    private int count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_short_comments,null);
        rvComments=(RecyclerView)view.findViewById(R.id.rvComments);
        Bundle bundle=getArguments();
        id=bundle.getLong("id",0);
        storyExtra=(StoryExtra)bundle.getSerializable("storyExtra");
        url=bundle.getString("url");
        count=bundle.getInt("count");
        rvComments.setLayoutManager(linearLayoutManager=new LinearLayoutManager(getActivity()));
        rvComments.setAdapter(commentsAdapter=new CommentsAdapter(getActivity()));

        getComments(url);

        return view;
    }

    private void getComments(String url)
    {
        if (url.endsWith("short-comments"))
        {
            Retrofit retrofit=create();
            ClientApi api=retrofit.create(ClientApi.class);
            Observable<CommentJson> resultObservable=api.getShortComments(id);
            resultObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CommentJson>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CommentJson commentJson) {
                                comments= commentJson.getComments();
                                for(int i=0;i<comments.size();i++)
                                {
                                    Log.i("MainActivityATS",comments.get(i).toString());
                                }
                                commentsAdapter.addList(comments);
                                commentsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("MainActivityATS","error"+e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else
            {
                Retrofit retrofit=create();
                ClientApi api=retrofit.create(ClientApi.class);
                Observable<CommentJson> resultObservable=api.getLongComments(id);
                resultObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CommentJson>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CommentJson commentJson) {
                                comments= commentJson.getComments();
                                commentsAdapter.addList(comments);
                                commentsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

        }

    }

    private static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
