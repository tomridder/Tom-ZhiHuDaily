package com.bignerdranch.myrxmeizi.ui.activity;

import android.bignerdranch.myrxmeizi.R;
import com.bignerdranch.myrxmeizi.bean.Stories;
import com.bignerdranch.myrxmeizi.ui.fragment.NewsDetailFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        afterCreate();
    }

    public static void start(Context context, Stories stories)
    {
        Intent intent=new Intent(context,NewsDetailActivity.class);
        intent.putExtra("stories",stories);
        context.startActivity(intent);
    }
    public void afterCreate()
    {
        Stories stories=(Stories) getIntent().getSerializableExtra("stories");
        showNewsDetailFragment(stories);
    }

    private void showNewsDetailFragment(Stories stories)
    {
        Fragment fragment= NewsDetailFragment.newInstance(stories);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fl_container,fragment);
        transaction.commit();
    }
}
