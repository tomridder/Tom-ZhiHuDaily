package com.bignerdranch.myrxmeizi.ui.fragment;

import android.bignerdranch.myrxmeizi.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

        MenuItem searchItem=menu.findItem(R.id.menu_item_search);
        final SearchView searchView=(SearchView)searchItem.getActionView();

        EditText textView = (EditText) searchView
                .findViewById(
                        android.support.v7.appcompat.R.id.search_src_text
                );

        textView.setHintTextColor(
                ContextCompat.getColor(
                        getActivity(),
                        R.color.background_Day)
        );
        searchView.setQueryHint(
                getActivity().getString(R.string.search_hint)
        );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
//                if(s.equals(""))
//                {
//                    storiesList=new ArrayList<>();
//                    storiesList= LitePal.findAll(Stories.class);
//                    adapter=new FavoriteStoriesAdapter(getActivity(),storiesList);
//                    recyclerView.setAdapter(adapter);
//                    return true;
//                }
//                storiesList=new ArrayList<>();
//                storiesList= LitePal.where("(mTitle like ?)","%"+s+"%").find(Stories.class);
//                adapter=new FavoriteStoriesAdapter(getActivity(),storiesList);
//                recyclerView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {

                EditText textView = (EditText) searchView
                        .findViewById(
                                android.support.v7.appcompat.R.id.search_src_text
                        );

                textView.setHintTextColor(
                        ContextCompat.getColor(
                                getActivity(),
                                R.color.background_Day)
                );
                searchView.setQueryHint(
                        getActivity().getString(R.string.search_hint)
                );
                int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                TextView textView1 = (TextView) searchView.findViewById(id);
                textView1.setTextColor(getResources().getColor(R.color.background_Day));

                storiesList=new ArrayList<>();
                storiesList= LitePal.where("(mTitle like ?)","%"+s+"%").find(Stories.class);
                adapter=new FavoriteStoriesAdapter(getActivity(),storiesList);
                recyclerView.setAdapter(adapter);
                return true;
            }
        });
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
