package com.bignerdranch.myrxmeizi.ui.activity;

import android.bignerdranch.myrxmeizi.R;

import com.bignerdranch.myrxmeizi.adapter.FavoriteStoriesAdapter;
import com.bignerdranch.myrxmeizi.ui.fragment.NewsListFragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class NewsListActivity extends AppCompatActivity
{

    private NewsListFragment mFragment;
    private FavoriteStoriesAdapter favoriteStoriesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        addFragment();
    }

    private void addFragment()
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        mFragment=NewsListFragment.newInstance();
        transaction.replace(R.id.fl_container1,mFragment);
        transaction.commit();
    }
}
