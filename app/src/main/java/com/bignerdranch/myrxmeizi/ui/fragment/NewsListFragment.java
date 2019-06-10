package com.bignerdranch.myrxmeizi.ui.fragment;

import android.app.Activity;
import android.bignerdranch.myrxmeizi.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.myrxmeizi.adapter.StoriesAdapter;
import com.bignerdranch.myrxmeizi.bean.Result;
import com.bignerdranch.myrxmeizi.bean.Stories;
import com.bignerdranch.myrxmeizi.bean.TopStories;
import com.bignerdranch.myrxmeizi.net.ClientApi;
import com.bignerdranch.myrxmeizi.net.GlideImageLoader;
import com.bignerdranch.myrxmeizi.ui.activity.NewsDetailActivity;
import com.bignerdranch.myrxmeizi.ui.activity.NewsFavoriteActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

public class NewsListFragment extends Fragment {


    @Bind(R.id.toolbars)
    Toolbar toolbars;
    Banner mBanner;
    FloatingActionButton fabAdd;
    private StoriesAdapter storiesAdapter;
    private List<Stories> storiesList;
    private RecyclerView recyclerView;
    private Calendar calendar = Calendar.getInstance();
    private  String text;
    private List<String> bannerImages;
    private List<String> bannerTitles;
    private List<Stories> bannerStories;
    private Context context;

    private static final int REQUEST_DATE=0;



    Date date=new Date();

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        text=sdf.format(calendar.getTime());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        fabAdd=(FloatingActionButton)view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager manager=getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance(date);
                dialog.setTargetFragment(NewsListFragment.this,REQUEST_DATE);
                dialog.show(manager,"date");
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        storiesList = new ArrayList<>();
        storiesAdapter = new StoriesAdapter(getActivity(), storiesList);
        recyclerView.setAdapter(storiesAdapter);


        bannerImages=new ArrayList<>();
        bannerTitles=new ArrayList<>();
        bannerStories=new ArrayList<>();

        mBanner=(Banner)view.findViewById(R.id.banner);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        mBanner.setViewPagerIsScroll(true);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                NewsDetailActivity.start(getActivity(), bannerStories.get(position));
            }
        });

        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode==REQUEST_DATE)
        {
            date=(Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
           // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
           // text=sdf.format(date);
//            AppCompatActivity activity = (AppCompatActivity) getActivity();
//            ActionBar actionBar = activity.getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setTitle(text);
//            }
            getSomdayNews(date);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.last_page:
                getlastNews();
                return true;
            case R.id.next_page:
                getNextNews();
                return true;
            case R.id.favorite:
                Intent intent=new Intent(getActivity(), NewsFavoriteActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getNextNews()
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        date=new Date(date.getTime()+(long)24*60*60*1000);
        final String time=sdf.format(date.getTime());

        Retrofit retrofit = create();
        if(date.before(new Date()))
        {
            ClientApi api = retrofit.create(ClientApi.class);
            Observable<Result> resultObservable = api.getBeforeNews(time);
            resultObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Result result) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            ActionBar actionBar = activity.getSupportActionBar();
                            if (actionBar != null) {
                                actionBar.setTitle(time);
                            }
                            storiesList.clear();
                            storiesList.addAll(result.getStories());
                            storiesAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            Log.i("MainActivityATS", time);
            Snackbar bar=Snackbar.make(getView(),"翻阅成功",Snackbar.LENGTH_LONG).setActionTextColor(Color.GREEN);
            ((TextView)(bar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.CYAN);
            bar.show();
        }else
        {
            date=new Date(date.getTime()-(long)24*60*60*1000);

            Snackbar bar=Snackbar.make(getView(),"没有了",Snackbar.LENGTH_LONG).setActionTextColor(Color.GREEN);
            ((TextView)(bar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.CYAN);
            bar.show();
        }
        // Observable<Result> observable=api.getBeforeNew
    }



    private void getSomdayNews(final Date date1)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        date=date1;
        final String time=sdf.format(date.getTime());

        Retrofit retrofit = create();
        if(date.before(new Date()))
        {
            ClientApi api = retrofit.create(ClientApi.class);
            Observable<Result> resultObservable = api.getBeforeNews(time);
            resultObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Result result) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            ActionBar actionBar = activity.getSupportActionBar();
                            if (actionBar != null) {
                                actionBar.setTitle(time);
                            }
                            storiesList.clear();
                            storiesList.addAll(result.getStories());
                            storiesAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            Log.i("MainActivityATS", time);
            Snackbar bar=Snackbar.make(getView(),"翻阅成功",Snackbar.LENGTH_LONG).setActionTextColor(Color.GREEN);
            ((TextView)(bar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.CYAN);
            bar.show();
        }else
        {
           // date=new Date(date.getTime()-(long)24*60*60*1000);

           // Toast.makeText(getActivity(),"没有了",Toast.LENGTH_SHORT).show();
          //  Snackbar.make(getView(),"没有了",Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.topbar_Background_Day)).show();
        Snackbar bar=Snackbar.make(getView(),"没有了",Snackbar.LENGTH_LONG).setActionTextColor(Color.GREEN);
        ((TextView)(bar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.CYAN);
        bar.show();
        }
        // Observable<Result> observable=api.getBeforeNew
    }

    private void getlastNews()
    {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        date=new Date(date.getTime()-(long)24*60*60*1000);
        final String time=sdf.format(date.getTime());
        Retrofit retrofit = create();
        ClientApi api = retrofit.create(ClientApi.class);
        Observable<Result> resultObservable=api.getBeforeNews(time);
        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        AppCompatActivity activity = (AppCompatActivity) getActivity();
                        ActionBar actionBar = activity.getSupportActionBar();
                        if (actionBar != null) {
                            actionBar.setTitle(time);
                        }
                        storiesList.clear();
                        storiesList.addAll(result.getStories());
                        storiesAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Log.i("MainActivityATS", time);
        Snackbar bar=Snackbar.make(getView(),"翻阅成功",Snackbar.LENGTH_LONG).setActionTextColor(Color.GREEN);
        ((TextView)(bar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.CYAN);
        bar.show();
        // Observable<Result> observable=api.getBeforeNews()
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        Retrofit retrofit = create();
        ClientApi api = retrofit.create(ClientApi.class);
        Observable<Result> observable = api.getTodayNews();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        storiesList.addAll(result.getStories());
                        storiesAdapter.notifyDataSetChanged();
                        int topSize=result.getTopStories().size();
                        for(int i=0;i<topSize;i++)
                        {
                            TopStories topStories=result.getTopStories().get(i);
                            bannerStories.add(convertStory(topStories));
                            bannerImages.add(topStories.getImage());
                            bannerTitles.add(topStories.getTitle());
                        }

                        mBanner.setImages(bannerImages);
                        mBanner.setBannerTitles(bannerTitles);
                        mBanner.start();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbars);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private Stories convertStory(TopStories topStories)
    {
        Stories res=new Stories();
        res.setId(topStories.getId());
        res.setTitle(topStories.getTitle());
        res.setImages(Arrays.asList(topStories.getImage()));
        return  res;
    }
}
