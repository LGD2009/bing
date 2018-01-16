package com.wallpaper.bing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wallpaper.bing.R;
import com.wallpaper.bing.adapter.CoverStoriesAdapter;
import com.wallpaper.bing.listener.OnItemClickListener;
import com.wallpaper.bing.network.bean.WallpaperBean;
import com.wallpaper.bing.presenter.contract.ICoverStoryContract;
import com.wallpaper.bing.presenter.impl.CoverStoryPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

/**
 * author GaoPC
 * date 2017-12-12 19:19
 * description
 */

public class CoverStoriesActivity extends BaseAppCompatActivity<CoverStoryPresenterImpl> implements ICoverStoryContract.CoverStoryView
        , OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout refreshLayout;
    private CoverStoriesAdapter coverStoriesAdapter;
    private ArrayList<WallpaperBean> list;
    private ArrayList<Boolean> expendsList;//用来记录展开的cardview的position

    private int page = 1;
    private String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_story);
        date = getIntent().getStringExtra(MainActivity.DATE);

        refreshLayout = (BGARefreshLayout) findViewById(R.id.activity_wallpapers_refresh);
        refreshLayout.setDelegate(this);
        BGAStickinessRefreshViewHolder refreshViewHolder = new BGAStickinessRefreshViewHolder(this, true);
        refreshViewHolder.setRotateImage(R.drawable.refresh_icon);
        refreshViewHolder.setStickinessColor(R.color.colorAccent);

        refreshLayout.setRefreshViewHolder(refreshViewHolder);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorAccent);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_wallpapers_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        expendsList = new ArrayList<>();

        coverStoriesAdapter = new CoverStoriesAdapter(Glide.with(this), list, expendsList);
        recyclerView.setAdapter(coverStoriesAdapter);

        coverStoriesAdapter.setOnItemClickListener(this);

        refreshLayout.beginRefreshing();

    }

    @Override
    protected CoverStoryPresenterImpl createPresenter() {
        return new CoverStoryPresenterImpl(this);
    }


    @Override
    public void onSuccess(List<WallpaperBean> bean) {
        list.addAll(bean);
        for (int i = 0; i < bean.size(); i++) {
            expendsList.add(false);
        }
        coverStoriesAdapter.notifyDataSetChanged();

        if (refreshLayout.isLoadingMore()) {
            refreshLayout.endLoadingMore();
        } else {
            refreshLayout.endRefreshing();
        }

    }

    @Override
    public void onFailed(String s) {
        Toast.makeText(CoverStoriesActivity.this, s, Toast.LENGTH_SHORT).show();
        if (refreshLayout.isLoadingMore()) {
            refreshLayout.endLoadingMore();
        } else {
            refreshLayout.endRefreshing();
        }
    }

    @Override
    public void itemClickListener(int position) {
        String date = list.get(position).getId();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.DATE, date);
        startActivity(intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.getWallpapers(date, page, 10);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        page++;
        presenter.getWallpapers(date, page, 10);
        return true;
    }
}
