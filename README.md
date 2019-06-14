### 献给Leonardo  da Vinci
### 前言
>本文的内容主要是解析Tom ZhiHuDaily APP 的制作流程，以及代码的具体实现，若有什么不足之处，还请提出建议，附上这个 APP 的 Github 地址 [Tom ZhiHuDaily](https://github.com/tomridder/Tom-ZhiHuDaily) 欢迎大家 star 和 fork.

Tom ZhiHuDaily使用的技术：

- Rxjava & Retrofit 
- Gson & RxAndroid
- Glide & Material Design
- ButterKnife & EventBus
- Banner & LitePal


Tom ZhiHuDaily可实现功能：

- 知乎Stories TopStories的展示
- 知乎TopStories的轮播
- 知乎Stories 的上翻下翻
- 知乎Stories 的收藏
- 闪屏页属性动画


![Image](https://github.com/tomridder/Tom-ZhiHuDaily/blob/master/6.png)

#### 本文的主要内容

- 知乎TopStories的轮播的实现（Rxjava & Retrofit & Banner）
- DetailStories的实现(Glide & WebView)
- 知乎Stories 收藏的实现(Litepal)
- 知乎 长评论 短评论的实现(viewpager + tabLayout)
- 闪屏页属性动画的实现

先来一波Tom ZhiHuDaily的展示吧，这款 APP 还是非常精美和优雅的
- 上翻下翻 知乎Stories 和 WebView加载NewsDetail的效果
 
![1.gif](https://github.com/tomridder/Tom-ZhiHuDaily/blob/master/1zhihu.gif)

- 知乎TopStories的轮播 和  知乎Stories 的收藏的效果

![2.gif](https://github.com/tomridder/Tom-ZhiHuDaily/blob/master/2zhihu.gif)

- 闪屏页属性动画 ，短评论，根据具体日期选择Stories的效果

![3.gif](https://github.com/tomridder/Tom-ZhiHuDaily/blob/master/3zhihu.gif)
## 一、知乎TopStories的轮播的实现

#### 1、利用Retrofit+Rxjava 从JSon数据转成对象


```
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

```

```
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

```

#### 2、利用Banner实现TopStories轮播

```
<com.youth.banner.Banner
    android:id="@+id/banner"
    android:layout_width="match_parent"
    android:layout_height="250dp">
</com.youth.banner.Banner>
```


```
public class GlideImageLoader extends ImageLoader
{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return null;
    }
}

```
## 二、DetailStories的实现

#### 1、利用Material Design 生成布局
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fitsSystemWindows="true">

        <android.support.design.widget.CollapsingToolbarLayout
            android:id="@+id/collapsingToolbarLayout"
            android:layout_width="match_parent"
            android:layout_height="256dp"
            android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
            app:contentScrim="?attr/colorPrimary"
            android:fitsSystemWindows="true"
            app:expandedTitleMarginEnd="96dp"
            app:expandedTitleTextAppearance="@style/CollapsingToolbarTitleStyle.About"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">

            <FrameLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fitsSystemWindows="true"
                app:layout_collapseMode="parallax"
                app:layout_collapseParallaxMultiplier="0.8"
                app:layout_scrollFlags="scroll|snap|enterAlways|enterAlwaysCollapsed">

                <ImageView
                    android:id="@+id/iv_header"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:contentDescription="@null"
                    android:scaleType="centerCrop" />

                <View
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_gravity="bottom"
                    android:background="@drawable/bg_shadow_mask" />

                <TextView
                    android:id="@+id/tv_title"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_gravity="bottom"
                    android:layout_marginBottom="16dp"
                    android:padding="16dp"
                    android:textColor="@android:color/white"
                    android:textSize="22sp"
                    tools:text="I am Fine" />

                <TextView
                    android:id="@+id/tv_source"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="end|bottom"
                    android:padding="8dp"
                    android:textColor="@color/color_source"
                    android:textSize="13sp"
                    tools:text="the source of pic" />
            </FrameLayout>

            <include layout="@layout/include_toolbar" />

        </android.support.design.widget.CollapsingToolbarLayout>
    </android.support.design.widget.AppBarLayout>

    <android.support.v4.widget.NestedScrollView
        android:id="@+id/nested_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="vertical"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <WebView
            android:id="@+id/wv_news"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:isScrollContainer="false"
            android:scrollbars="vertical" />
    </android.support.v4.widget.NestedScrollView>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="128dp">
    </FrameLayout>

</android.support.design.widget.CoordinatorLayout>



```


#### 2、利用HtmlUtils生成WebView要加载的 htmlData
```

public static String createHtmlData(NewsDetail newsDetail, boolean isNight) {
        final String css = HtmlUtil.createCssTag(newsDetail.getCss());
        final String js = HtmlUtil.createJsTag(newsDetail.getJs());
        final String body = handleHtml(newsDetail.getBody(), isNight).toString();
        return createHtmlData(body, css, js);
    }

    public static StringBuffer handleHtml(String body, boolean isNight) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ></head>");
        stringBuffer.append(isNight ? "<body class=\"night\">" : "<body>");
        stringBuffer.append(body);
        stringBuffer.append("</body></html>");
        return stringBuffer;
    }
```

## 三、知乎Stories 收藏的实现

#### 1、利用litepal建立表
LitePal是一款开源的Android数据库框架，采用对象关系映射（ORM）模式，将常用的数据库功能进行封装，可以不用写一行SQL语句就可以完成创建表、增删改查的操作。
相关教程可以参见链接[litepal](https://www.jianshu.com/p/8035eb5da7a2)

```
<litepal>
    <dbname value="News"></dbname>
    <version value="1"></version>
    <list>
        <mapping class="com.bignerdranch.myrxmeizi.bean.Stories"></mapping>
    </list>
</litepal>

```


#### 2、 利用litePal 进行 增删查询
```
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
```

```
        storiesList=new ArrayList<>();
        storiesList= LitePal.findAll(Stories.class);
        for(int i=0;i<storiesList.size();i++)
        {
            Log.i("MainActivityATS",storiesList.get(i).getTitle());
        }
        adapter=new FavoriteStoriesAdapter(getActivity(),storiesList);
        recyclerView.setAdapter(adapter);

```
## 四、知乎 长评论 短评论的实现
#### 1、生成继承 RecyclerView.Adapter的 CommentsAdapter
 
#### 2、生成继承 FragmentPagerAdapter的 CommentPagerAdapter

#### 3、生成 CommentsFragment，并用 CommentsFragment 构建 长评论 短评论的Fragments
```
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
```
#### 4、生成 CommentsActivity，并 为viewPager 和tabLayout setAdapter
```
        fragments.add(shortCommentsFragment);
        fragments.add(longCommentsFragment);
        CommentPagerAdapter adapter = new CommentPagerAdapter(getSupportFragmentManager(), fragments, tabList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
```
## 五、 闪屏页属性动画的实现
```
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
```

1.设置图片和缩放中心坐标

2.设置放大到最大XY坐标

3.设置动画持续时间和延迟时间

4.设置在动画结束时跳转Activity

5.设置同时执行XY坐标动画并开启属性动画

以上便是我写这个 APP 的具体实现思路，以及踩过的一些坑，记录下来，给大家看看，最后附上这个 APP 的 Github 地址 [Tom ZhiHuDaily](https://github.com/tomridder/Tom-ZhiHuDaily)

欢迎大家 star 和 fork，如果有什么想法或者建议，非常欢迎大家来讨论

-----

## License <br>
There is no fucking license.<br>

### 猜你喜欢
- [Tom-Keylogger](https://github.com/tomridder/Tom-Keylogger)

