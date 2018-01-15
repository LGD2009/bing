package com.wallpaper.bing.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wallpaper.bing.R;
import com.wallpaper.bing.adapter.StoryKnowledgeAdapter;
import com.wallpaper.bing.adapter.StoryRelevantAdapter;
import com.wallpaper.bing.network.BingUrl;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.CoverStoryKnowledgeEntity;
import com.wallpaper.bing.network.bean.CoverStoryRelevantEntity;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.contract.IMainContract;
import com.wallpaper.bing.presenter.impl.MainPresenterImpl;
import com.wallpaper.bing.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity<MainPresenterImpl> implements IMainContract.IMainView, View.OnClickListener {

    public static String DATE = "WALLPAPER_INFO_DATE";

    private BottomSheetBehavior bottomSheetBehavior;

    private ImageView wallpaperImage;
    private TextView copyrightText;
    private TextView dateText;
    private NestedScrollView rootScrollView;
    private ConstraintLayout constraintLayout;
    private String date;
    private String imageUrl;
    private Typeface fontFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fontFace= Typeface.createFromAsset(getAssets(),
                "fonts/SegoeSlab-SemiLight.ttf");


        tintManager.setStatusBarTintEnabled(false);
        wallpaperImage = (ImageView) findViewById(R.id.activity_main_image);

        constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main_constraint);
        rootScrollView = (NestedScrollView) findViewById(R.id.activity_main_scroll);
        //展开时顶部留出状态栏
        rootScrollView.setPadding(0, tintManager.getConfig().getStatusBarHeight(), 0, 0);

        bottomSheetBehavior = BottomSheetBehavior.from(rootScrollView);
        copyrightText = (TextView) findViewById(R.id.activity_main_copyright_text);
        dateText = (TextView) findViewById(R.id.activity_main_date_text);

        findViewById(R.id.activity_main_menu_list).setOnClickListener(this);
        findViewById(R.id.activity_main_set_wallpaper).setOnClickListener(this);

        date = getIntent().getStringExtra(DATE) == null ? DateUtil.getCurrentDate() : getIntent().getStringExtra(DATE);

        presenter.getWallpaper(date);

    }

    @Override
    protected MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_menu_list:
                Intent intent =new Intent(MainActivity.this, CoverStoriesActivity.class);
                intent.putExtra(DATE,date);
                startActivity(intent);
                break;
            case R.id.activity_main_set_wallpaper:
                if (!TextUtils.isEmpty(imageUrl))
                    presenter.setDesktopWallpaper(imageUrl);
                break;
        }
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void dismissDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onSuccess(BaseBean<WallpaperInfoBean> baseBean) {
        final WallpaperInfoBean infoBean = baseBean.getMessage();
        imageUrl = BingUrl.BASE_IMAGE_URL + infoBean.getWallpapersEntity().getImageUrl().replace("1920x1080", "1080x1920");
        Glide.with(this).load(imageUrl)
                .animate(R.anim.image_scale)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(wallpaperImage);

        copyrightText.setText(infoBean.getWallpapersEntity().getCopyright());

        dateText.setText(DateUtil.stringToString(date, DateUtil.DatePattern.yyyyMMdd, DateUtil.DatePattern.yyyy_MM_dd));
        dateText.setTypeface(fontFace);

        //设置bottomsheet的peek高度
        copyrightText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomSheetBehavior.setPeekHeight(copyrightText.getHeight() + dateText.getHeight() + tintManager.getConfig().getStatusBarHeight());
            }
        });
        copyrightText.setTypeface(fontFace);

        initBottomSheet(infoBean);

    }

    @Override
    public void onSuccess(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void initBottomSheet(WallpaperInfoBean infoBean) {

        ImageView thumbnail = (ImageView) constraintLayout.findViewById(R.id.activity_main_story_thumbnail);
        TextView coverAttribute = (TextView) constraintLayout.findViewById(R.id.activity_main_cover_attribute);
        TextView coverTitle = (TextView) constraintLayout.findViewById(R.id.activity_main_cover_title);
        TextView coverContent = (TextView) constraintLayout.findViewById(R.id.activity_main_cover_content);
        TextView storyTitle = (TextView) constraintLayout.findViewById(R.id.activity_main_story_title);
        TextView storySubtitle = (TextView) constraintLayout.findViewById(R.id.activity_main_story_subtitle);
        coverContent.setTypeface(fontFace);

        Glide.with(this).load(infoBean.getCoverStoryEntity().getThumbnail()).into(thumbnail);
        coverAttribute.setText(infoBean.getCoverStoryEntity().getCoverAttribute());
        coverTitle.setText(infoBean.getCoverStoryEntity().getCoverTitle());
        coverContent.setText(String.format(getResources().getString(R.string.space),infoBean.getCoverStoryEntity().getContent()));
        storyTitle.setText(infoBean.getCoverStoryEntity().getTitle());
        storySubtitle.setText(infoBean.getCoverStoryEntity().getSubtitle());

        RecyclerView recyclerView = (RecyclerView) constraintLayout.findViewById(R.id.activity_main_relevant_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CoverStoryRelevantEntity> relevantEntityList = new ArrayList<>();
        StoryRelevantAdapter relevantAdapter = new StoryRelevantAdapter(relevantEntityList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(relevantAdapter);
        RecyclerView recyclerKnowledge = (RecyclerView) constraintLayout.findViewById(R.id.activity_main_knowledge_recycler);
        recyclerKnowledge.setLayoutManager(new LinearLayoutManager(this));
        List<CoverStoryKnowledgeEntity> knowledgeEntityList = new ArrayList<>();
        StoryKnowledgeAdapter knowledgeAdapter = new StoryKnowledgeAdapter(knowledgeEntityList, this);
        recyclerKnowledge.setHasFixedSize(true);
        recyclerKnowledge.setAdapter(knowledgeAdapter);

        relevantEntityList.addAll(infoBean.getRelevantEntities());
        relevantAdapter.notifyItemRangeInserted(0, infoBean.getRelevantEntities().size());
        knowledgeEntityList.addAll(infoBean.getKnowledgeEntities());
        knowledgeAdapter.notifyItemRangeInserted(0, infoBean.getKnowledgeEntities().size());


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                constraintLayout.setBackgroundColor(Color.argb((int) (155 * slideOffset + 100), (int) (255 * slideOffset), (int) (255 * slideOffset), (int) (255 * slideOffset)));
                copyrightText.setTextColor(Color.rgb((int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset))));
                dateText.setTextColor(Color.rgb((int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset))));
                rootScrollView.smoothScrollTo(0, (int) (bottomSheetBehavior.getPeekHeight()*slideOffset));
            }
        });

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
