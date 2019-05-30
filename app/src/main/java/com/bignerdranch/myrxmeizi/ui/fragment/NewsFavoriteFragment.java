package com.bignerdranch.myrxmeizi.ui.fragment;

import android.bignerdranch.myrxmeizi.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.bignerdranch.myrxmeizi.adapter.FavoriteStoriesAdapter;
import com.bignerdranch.myrxmeizi.bean.Stories;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class NewsFavoriteFragment extends Fragment
{

    private FavoriteStoriesAdapter adapter;
    private List<Stories>  storiesList;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    public static Fragment newInstance() {
        NewsFavoriteFragment fragment = new NewsFavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initToolbar(Toolbar toolbar)
    {
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar=appCompatActivity.getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("收藏列表");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
                getActivity().finish();
               return true;

           default:
           return super.onOptionsItemSelected(item);
       }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_news_favorite,container,false);
       recyclerView=(RecyclerView)view.findViewById(R.id.favorite_recyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       toolbar=(Toolbar)view.findViewById(R.id.toolbarss);
       return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initToolbar(toolbar);
        storiesList=new ArrayList<>();
        storiesList= LitePal.findAll(Stories.class);
        for(int i=0;i<storiesList.size();i++)
        {
            Log.i("MainActivityATS",storiesList.get(i).getTitle());
        }
        adapter=new FavoriteStoriesAdapter(getActivity(),storiesList);
        recyclerView.setAdapter(adapter);
    }
}
