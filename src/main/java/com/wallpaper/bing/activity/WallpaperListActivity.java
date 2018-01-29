package com.wallpaper.bing.activity;

import android.animation.TimeInterpolator;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wallpaper.bing.R;
import com.wallpaper.bing.adapter.WallpaperListAdapter;
import com.wallpaper.bing.listener.OnItemClickListener;
import com.wallpaper.bing.network.BingUrl;
import com.wallpaper.bing.network.bean.WallpaperBean;
import com.wallpaper.bing.presenter.contract.IWallpaperListContract;
import com.wallpaper.bing.presenter.impl.WallpaperListPresenterImpl;
import com.wallpaper.bing.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * author GaoPC
 * date 2017-12-12 19:19
 * description
 */

public class WallpaperListActivity extends BaseAppCompatActivity<WallpaperListPresenterImpl,List<WallpaperBean>> implements IWallpaperListContract.CoverStoryView
        , OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BGARefreshLayout refreshLayout;
    private WallpaperListAdapter wallpaperListAdapter;
    private ArrayList<WallpaperBean> list;

    private int page = 1;
    private String date;
    private final int OPTION_REFRESH = 1;
    private final int OPTION_LOAD = 2;

    private static final int ANIM_DURATION_TOOLBAR = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_list);

        date = getIntent().getStringExtra(MainActivity.DATE);

        toolbar = (Toolbar) findViewById(R.id.activity_wallpaper_list_toolbar);
        toolbar.inflateMenu(R.menu.menu_wallpaper_list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        startToolbarAnim(toolbar, true);

        refreshLayout = (BGARefreshLayout) findViewById(R.id.activity_wallpapers_list_refresh);
        refreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        refreshLayout.setRefreshViewHolder(refreshViewHolder);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorAccent);
        recyclerView = (RecyclerView) findViewById(R.id.activity_wallpapers_list_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        wallpaperListAdapter = new WallpaperListAdapter(Glide.with(this), list);
        recyclerView.setAdapter(wallpaperListAdapter);
        wallpaperListAdapter.setOnItemClickListener(this);

        startContentViewAnim(refreshLayout, true);

        refreshLayout.beginRefreshing();

    }

    @Override
    protected WallpaperListPresenterImpl createPresenter() {
        return new WallpaperListPresenterImpl(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.getWallpapers(date, page, 10, OPTION_REFRESH);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        page++;
        presenter.getWallpapers(date, page, 10, OPTION_LOAD);
        return true;
    }

    @Override
    public void onSuccess(List<WallpaperBean> bean, int option) {
        switch (option) {
            case OPTION_REFRESH:
                list.clear();
                list.addAll(bean);
                refreshLayout.endRefreshing();
                break;
            case OPTION_LOAD:
                list.addAll(bean);
                refreshLayout.endLoadingMore();
                break;
        }
        wallpaperListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String s) {
        Toast.makeText(WallpaperListActivity.this, s, Toast.LENGTH_SHORT).show();
        if (refreshLayout.isLoadingMore()) {
            refreshLayout.endLoadingMore();
        } else {
            refreshLayout.endRefreshing();
        }
    }

    @Override
    public void itemClickListener(int position) {
        if (TextUtils.isEmpty(list.get(position).getImageUrl())) {
            Toast.makeText(WallpaperListActivity.this, "年代久远，图片已丢失", Toast.LENGTH_SHORT).show();
            return;
        }
        String date = list.get(position).getId();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.DATE, date);
        intent.putExtra(MainActivity.IMAGE_URL, BingUrl.BASE_IMAGE_URL + list.get(position).getImageUrlMobile());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View targetView = recyclerView.findViewHolderForLayoutPosition(position).itemView.findViewById(R.id.item_wallpaper_list_image);
            ActivityOptions
                    compat = ActivityOptions.makeSceneTransitionAnimation(this, targetView, getString(R.string.transitionImage));
            ActivityCompat.startActivity(this, intent, compat.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_jump:
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.stringToDate(date, DateUtil.DatePattern.YYYYMMDD));
                    DatePickerDialog dialog = new DatePickerDialog(WallpaperListActivity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setWindowAnimations(R.style.dialog_anim);
                    }
                    dialog.show();
                    break;
            }
            return true;
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            date = DateUtil.getDateToString(calendar.getTime().getTime(), DateUtil.DatePattern.YYYYMMDD);
            page = 1;
            recyclerView.scrollToPosition(0);
            refreshLayout.beginRefreshing();
        }
    };

    //toolbar 动画  isAESC 进入或退出时的动画
    private void startToolbarAnim(Toolbar toolbar, boolean isAESC) {
        int start = isAESC ? -tintManager.getConfig().getActionBarHeight() : 0;
        int end = isAESC ? 0 : -tintManager.getConfig().getActionBarHeight();

        toolbar.setTranslationY(start);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            toolbar.getChildAt(i).setTranslationY(start);
        }

        toolbar.animate()
                .translationY(end)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);

        for (int i = 0; i < toolbar.getChildCount() - 1; i++) {
            toolbar.getChildAt(i).animate()
                    .translationY(end)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                    .setStartDelay(400);

        }
        toolbar.getChildAt(toolbar.getChildCount() - 1).animate()
                .translationY(end)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500);

    }

    private void startContentViewAnim(View view, boolean isAESC) {
        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        int start = isAESC ? point.y - tintManager.getConfig().getActionBarHeight() : 0;
        int end = isAESC ? 0 : point.y - tintManager.getConfig().getActionBarHeight();

        TimeInterpolator interpolator;
        if (isAESC) {
            interpolator = new DecelerateInterpolator(2f);
        } else {
            interpolator = new AccelerateInterpolator(2f);
        }

        view.setTranslationY(start);
        view.animate()
                .translationY(end)
                .setInterpolator(interpolator)
                .setDuration(ANIM_DURATION_TOOLBAR).setStartDelay(300);

    }

    private long time = 0;    //返回键防抖

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - time > 1000) {
            startToolbarAnim(toolbar, false);
            startContentViewAnim(refreshLayout, false);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WallpaperListActivity.super.onBackPressed();
                }
            }, 1000);

            time = System.currentTimeMillis();
        }


    }


}
