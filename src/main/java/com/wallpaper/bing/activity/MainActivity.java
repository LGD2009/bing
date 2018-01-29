package com.wallpaper.bing.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wallpaper.bing.R;
import com.wallpaper.bing.adapter.StoryKnowledgeAdapter;
import com.wallpaper.bing.adapter.StoryRelevantAdapter;
import com.wallpaper.bing.fragment.GeneralPreferenceFragment;
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

import okhttp3.ResponseBody;

public class MainActivity extends BaseAppCompatActivity<MainPresenterImpl, BaseBean<WallpaperInfoBean>> implements IMainContract.IMainView
        , Toolbar.OnMenuItemClickListener {

    public static String DATE = "WALLPAPER_INFO_DATE";
    public static String IMAGE_URL = "WALLPAPER_URL";

    private BottomSheetBehavior bottomSheetBehavior;

    private ImageView wallpaperImage;
    private TextView copyrightText;
    private TextView dateText;
    private NestedScrollView rootScrollView;
    private ConstraintLayout constraintLayout;
    private String date;    //日期
    private String imageUrl;    //1080x1920 图片的地址

    private final int DESKTOP_WALLPAPER = 12;    //设为桌面壁纸
    private final int DOWNLOAD_WALLPAPER = 13;   //下载图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

        tintManager.setStatusBarTintEnabled(false);
        wallpaperImage = (ImageView) findViewById(R.id.activity_main_image);

        constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main_constraint);
        rootScrollView = (NestedScrollView) findViewById(R.id.activity_main_scroll);
        //展开时顶部留出状态栏
        rootScrollView.setPadding(0, tintManager.getConfig().getStatusBarHeight(), 0, 0);
        bottomSheetBehavior = BottomSheetBehavior.from(rootScrollView);
        copyrightText = (TextView) findViewById(R.id.activity_main_copyright_text);
        dateText = (TextView) findViewById(R.id.activity_main_date_text);

        date = getIntent().getStringExtra(DATE) == null ? DateUtil.getCurrentDate() : getIntent().getStringExtra(DATE);
        imageUrl = getIntent().getStringExtra(IMAGE_URL) == null ? "" : getIntent().getStringExtra(IMAGE_URL);

        if (!TextUtils.isEmpty(imageUrl)) {
            //Glide 可以根据地址缓存图片，减少等待时间
            Glide.with(this).load(imageUrl).asBitmap().into(wallpaperImage);
        }

        presenter.getWallpaper(date);

    }

    @Override
    protected MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_desktop:
                if (!TextUtils.isEmpty(imageUrl)) {
                    requestPermission(DESKTOP_WALLPAPER);
                }
                break;
            case R.id.menu_main_download:
                if (!TextUtils.isEmpty(imageUrl)) {
                    requestPermission(DOWNLOAD_WALLPAPER);
                }
                break;
            case R.id.menu_main_list:
                Intent intent = new Intent(MainActivity.this, WallpaperListActivity.class);
                intent.putExtra(DATE, date);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.menu_main_settings:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
                break;
        }
        return true;
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
        WallpaperInfoBean infoBean = baseBean.getMessage();

        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = BingUrl.BASE_IMAGE_URL + infoBean.getWallpapersEntity().getImageUrlMobile();
            Glide.with(this).load(imageUrl).asBitmap().listener(new RequestListener<String, Bitmap>() {
                @Override
                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    ObjectAnimator animatorX = ObjectAnimator.ofFloat(wallpaperImage,
                            "scaleX", 1.2f, 1f);
                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(wallpaperImage,
                            "scaleY", 1.2f, 1f);
                    AnimatorSet set = new AnimatorSet();
                    set.setDuration(2000);
                    set.playTogether(animatorX, animatorY);
                    set.start();
                    return false;
                }
            }).into(wallpaperImage);
        }

        copyrightText.setText(infoBean.getWallpapersEntity().getCopyright());
        dateText.setText(DateUtil.stringToString(date, DateUtil.DatePattern.YYYYMMDD, DateUtil.DatePattern.YYYY_MM_DD));
        //设置bottomsheet的peek高度
        copyrightText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int peekHeight = copyrightText.getHeight() + dateText.getHeight() + tintManager.getConfig().getStatusBarHeight();

                bottomSheetBehavior.setPeekHeight(peekHeight);
                rootScrollView.setTranslationY(peekHeight);
                rootScrollView.animate().translationY(0).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                copyrightText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        initBottomSheet(infoBean);

    }

    @Override
    public void onFailed(String s) {
        if (s.contains("Unable to resolve host")) {
            Toast.makeText(MainActivity.this, "网络连接出现问题", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    private void initBottomSheet(WallpaperInfoBean infoBean) {

        ImageView thumbnail = (ImageView) constraintLayout.findViewById(R.id.activity_main_story_thumbnail);
        TextView coverAttribute = (TextView) constraintLayout.findViewById(R.id.activity_main_cover_attribute);
        TextView coverTitle = (TextView) constraintLayout.findViewById(R.id.activity_main_cover_title);
        TextView coverContent = (TextView) constraintLayout.findViewById(R.id.activity_main_cover_content);
        TextView storyTitle = (TextView) constraintLayout.findViewById(R.id.activity_main_story_title);
        TextView storySubtitle = (TextView) constraintLayout.findViewById(R.id.activity_main_story_subtitle);

        Glide.with(this).load(infoBean.getCoverStoryEntity().getThumbnail()).into(thumbnail);
        coverAttribute.setText(infoBean.getCoverStoryEntity().getCoverAttribute());
        coverTitle.setText(infoBean.getCoverStoryEntity().getCoverTitle());
        coverContent.setText(String.format(getResources().getString(R.string.space), infoBean.getCoverStoryEntity().getContent()));
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
                boolean isNight = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean(GeneralPreferenceFragment.NIGHT_SWITCH, false);
                if (isNight) {
                    constraintLayout.setBackgroundColor(Color.argb((int) (155 * slideOffset + 100), (int) (48 * slideOffset), (int) (48 * slideOffset), (int) (48 * slideOffset)));
                } else {
                    constraintLayout.setBackgroundColor(Color.argb((int) (155 * slideOffset + 100), (int) (255 * slideOffset), (int) (255 * slideOffset), (int) (255 * slideOffset)));
                    copyrightText.setTextColor(Color.rgb((int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset))));
                    dateText.setTextColor(Color.rgb((int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset)), (int) (255 * (1 - slideOffset))));
                }

            }
        });

    }

    @Override
    public void onSuccess(Object bean, int option) {
        ResponseBody responseBody = (ResponseBody) bean;
        switch (option) {
            case DESKTOP_WALLPAPER:
                presenter.setDesktopWallpaper(BitmapFactory.decodeStream(responseBody.byteStream()));
                break;
            case DOWNLOAD_WALLPAPER:
                String picName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                presenter.downloadWallpaper(responseBody.byteStream(), picName);
                break;
        }
    }


    //请求存储读写的权限，设置壁纸
    private void requestPermission(int option) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    option);
        } else {
            presenter.getWallpaper(imageUrl, option);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == DESKTOP_WALLPAPER || requestCode == DOWNLOAD_WALLPAPER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户已授权
                presenter.getWallpaper(imageUrl, requestCode);
            } else {
                //用户拒绝权限
                Toast.makeText(MainActivity.this, "权限被拒绝，不能设置壁纸", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            super.onBackPressed();
        } else {
            rootScrollView.smoothScrollTo(0, 0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

}
