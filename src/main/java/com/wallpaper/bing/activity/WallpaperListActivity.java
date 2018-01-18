package com.wallpaper.bing.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wallpaper.bing.R;
import com.wallpaper.bing.adapter.WallpaperListAdapter;
import com.wallpaper.bing.listener.OnItemClickListener;
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

public class WallpaperListActivity extends BaseAppCompatActivity<WallpaperListPresenterImpl> implements IWallpaperListContract.CoverStoryView
        , OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout refreshLayout;
    private WallpaperListAdapter wallpaperListAdapter;
    private ArrayList<WallpaperBean> list;

    private int page = 1;
    private String date;
    private final int OPTION_REFRESH = 1;
    private final int OPTION_LOAD = 2;
    private final int OPTION_JUMP = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_list);
        date = getIntent().getStringExtra(MainActivity.DATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_wallpaper_list_toolbar);
        toolbar.inflateMenu(R.menu.menu_wallpaper_list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        refreshLayout = (BGARefreshLayout) findViewById(R.id.activity_wallpapers_list_refresh);
        refreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        refreshLayout.setRefreshViewHolder(refreshViewHolder);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorAccent);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_wallpapers_list_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        wallpaperListAdapter = new WallpaperListAdapter(Glide.with(this), list);
        recyclerView.setAdapter(wallpaperListAdapter);
        wallpaperListAdapter.setOnItemClickListener(this);

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
            case OPTION_JUMP:
                list.clear();
                list.addAll(bean);
                break;
        }
        wallpaperListAdapter.notifyItemRangeChanged(list.size() - bean.size(), bean.size());
    }

    @Override
    public void onSuccess(List<WallpaperBean> bean) {

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
        if (TextUtils.isEmpty(list.get(position).getImageUrl())){
            Toast.makeText(WallpaperListActivity.this,"年代久远，图片已丢失", Toast.LENGTH_SHORT).show();
            return;
        }
        String date = list.get(position).getId();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.DATE, date);
        startActivity(intent);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_jump:
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.stringToDate(date, DateUtil.DatePattern.yyyyMMdd));
                    DatePickerDialog dialog = new DatePickerDialog(WallpaperListActivity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
            calendar.set(year,month,dayOfMonth);
            date = DateUtil.getDateToString(calendar.getTime().getTime(), DateUtil.DatePattern.yyyyMMdd);
            page=1;
            presenter.getWallpapers(date, 1, 10, OPTION_JUMP);
        }
    };
}
