package com.bignerdranch.myrxmeizi.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CommentPagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private FragmentManager fm;

    public CommentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments,ArrayList<String> titles)
    {
        super(fm);
        this.fm=fm;
        this.fragments=fragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        Object obj=super.instantiateItem(container,position);
        return obj;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
