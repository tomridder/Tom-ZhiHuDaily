package com.bignerdranch.myrxmeizi.ui.fragment;

import android.bignerdranch.myrxmeizi.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.myrxmeizi.bean.NewsDetail;
import com.bignerdranch.myrxmeizi.bean.Stories;
import com.bignerdranch.myrxmeizi.net.ClientApi;
import com.bignerdranch.myrxmeizi.utils.HtmlUtil;
import com.bumptech.glide.Glide;


import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
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

public class NewsDetailFragment extends Fragment {

    @Bind(R.id.iv_header)
    ImageView mIvHeader;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_source)
    TextView mTvSource;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.wv_news)
    WebView mWvNews;
    @Bind(R.id.nested_view)
    NestedScrollView mNestedView;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
//    @Bind(R.id.collapsingToolbarLayout)
//    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Stories stories;
    private boolean isFavourite=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        stories = (Stories) getArguments().getSerializable("stories");
        List<Stories> storiesList = LitePal.where("mId = ?",stories.getId()+"").find(Stories.class);
        if(storiesList!=null && storiesList.size()>0)
        {
            isFavourite=true;
        }
        init();
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail,menu);
        if(isFavourite) menu.findItem(R.id.favorite_one).setIcon(R.drawable.fav_active);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().finish();
                return true;
            case R.id.menu_action_share:
                    share();
                    return true;
            case R.id.favorite_one:
                if(!isFavourite)
                {
                    Stories stories1=new Stories();
                    stories1.setTitle(stories.getTitle());
                    stories1.setImages(stories.getImages());
                    stories1.setId(stories.getId());
                    stories1.setGaPrefix(stories.getGaPrefix());
                    stories1.setMultipic(stories.getMultipic());
                    stories1.setType(stories.getType());
                    stories1.save();
                    item.setIcon(R.drawable.fav_active);
                    isFavourite = true;
                }else
                {
                    LitePal.deleteAll(Stories.class,"mId = ?",stories.getId()+"");
                    item.setIcon(R.drawable.fav_normal);
                    isFavourite = false;
                }
                return true;
                    default:
                        return super.onOptionsItemSelected(item);
        }


    }

    private void share()
    {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
        intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_from)+stories.getTitle()+"，http://daily.zhihu.com/story/"+stories.getId());
        startActivity(Intent.createChooser(intent,stories.getTitle()));
    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);

    }


    private void loadData() {
        Retrofit retrofit = create();
        ClientApi api = retrofit.create(ClientApi.class);
        Observable<NewsDetail> newsDetailObservable = api.getDetailNews(stories.getId());
        newsDetailObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        //         Log.i("MainActivityATS",newsDetail.toString());
                        Glide.with(getActivity())
                                .load(newsDetail.getImage())
                                .into(mIvHeader);
                        mTvTitle.setText(newsDetail.getTitle());
                        mTvSource.setText(newsDetail.getImage_source());
                        mWvNews.setDrawingCacheEnabled(true);
                        //boolean isNight=Pre
                        String htmlData = HtmlUtil.createHtmlData(newsDetail, true);
                        mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static Fragment newInstance(Stories stories) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("stories", stories);
        Fragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
