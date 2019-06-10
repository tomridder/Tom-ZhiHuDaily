package com.bignerdranch.myrxmeizi.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.bignerdranch.myrxmeizi.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bignerdranch.myrxmeizi.ui.fragment.NewsListFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class SplashActivity extends AppCompatActivity
{

    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv=(ImageView)findViewById(R.id.iv);
        SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                iv.setImageBitmap(bitmap);
                iv.setPivotX(bitmap.getWidth() * 0.3f);
                iv.setPivotY(bitmap.getHeight() * 0.25f);
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv, "scaleX", 1, 1.25f);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv, "scaleY", 1, 1.25f);
                AnimatorSet set = new AnimatorSet();
                set.setDuration(2000).setStartDelay(1000);
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startActivity(new Intent(SplashActivity.this, NewsListActivity.class));
                        finish();
                    }
                });
                set.playTogether(objectAnimatorX, objectAnimatorY);
                set.start();
            }
        };
        Glide.with(this).load(R.drawable.davinci2).asBitmap().into(target);
    }
}
