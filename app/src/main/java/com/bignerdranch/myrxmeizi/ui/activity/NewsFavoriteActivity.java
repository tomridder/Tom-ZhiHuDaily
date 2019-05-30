package com.bignerdranch.myrxmeizi.ui.activity;

import android.bignerdranch.myrxmeizi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.myrxmeizi.bean.Stories;
import com.bignerdranch.myrxmeizi.ui.fragment.NewsDetailFragment;
import com.bignerdranch.myrxmeizi.ui.fragment.NewsFavoriteFragment;

public class NewsFavoriteActivity extends AppCompatActivity
{

    private Fragment mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_favorite);
        addFragment();
    }

    private void addFragment()
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        mFragment=NewsFavoriteFragment.newInstance();
        transaction.replace(R.id.fl_container2,mFragment);
        transaction.commit();
    }
}
