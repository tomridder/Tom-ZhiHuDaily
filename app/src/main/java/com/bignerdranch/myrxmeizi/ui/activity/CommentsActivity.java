package com.bignerdranch.myrxmeizi.ui.activity;

import android.app.Activity;
import android.bignerdranch.myrxmeizi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bignerdranch.myrxmeizi.adapter.CommentPagerAdapter;
import com.bignerdranch.myrxmeizi.bean.StoryExtra;
import com.bignerdranch.myrxmeizi.ui.fragment.CommentsFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsActivity extends AppCompatActivity
{

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private long id;
    private StoryExtra storyExtra;
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        id=getIntent().getLongExtra("id",0);
        Log.i("MainActivityATS","id"+id);
        storyExtra=(StoryExtra)getIntent().getSerializableExtra("storyExtra");
        initToolbar(toolbar);

        ArrayList<String> tabList=new ArrayList<>();
        tabList.add("短评论(" + storyExtra.getShortComments() + ")");
        tabList.add("长评论(" + storyExtra.getLongComments() + ")");
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));

        CommentsFragment shortCommentsFragment = new CommentsFragment();
        Bundle bundleForShortComments = new Bundle();
        bundleForShortComments.putLong("id", id);
        bundleForShortComments.putSerializable("storyExtra", storyExtra);
        bundleForShortComments.putString("url", "http://news-at.zhihu.com/api/4/story/%1$s/short-comments");
        bundleForShortComments.putInt("count", storyExtra.getShortComments());
        shortCommentsFragment.setArguments(bundleForShortComments);

        CommentsFragment longCommentsFragment = new CommentsFragment();
        Bundle bundleForLongComments = new Bundle();
        bundleForLongComments.putLong("id", id);

        bundleForLongComments.putSerializable("storyExtra", storyExtra);
        bundleForLongComments.putString("url", "http://news-at.zhihu.com/api/4/story/%1$s/long-comments");
        bundleForShortComments.putInt("counts", storyExtra.getLongComments());
        longCommentsFragment.setArguments(bundleForLongComments);

        fragments.add(shortCommentsFragment);
        fragments.add(longCommentsFragment);
        CommentPagerAdapter adapter = new CommentPagerAdapter(getSupportFragmentManager(), fragments, tabList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void initToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_18pt_2x);//设置导航栏图标
        toolbar.setTitle("共" + (storyExtra.getShortComments()+storyExtra.getLongComments()) + "条");//设置主标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
