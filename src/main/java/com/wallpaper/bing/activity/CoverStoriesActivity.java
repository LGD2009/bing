package com.wallpaper.bing.activity;

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
import com.wallpaper.bing.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author GaoPC
 * date 2017-12-12 19:19
 * description
 */

public class CoverStoriesActivity extends BaseAppCompatActivity<CoverStoryPresenterImpl> implements ICoverStoryContract.CoverStoryView {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CoverStoriesAdapter coverStoriesAdapter;
    private ArrayList<WallpaperBean> list;
    private ArrayList<Boolean> expendsList;//用来记录展开的cardview的position

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_story);
        String date = getIntent().getStringExtra(MainActivity.DATE);

        recyclerView = (RecyclerView) findViewById(R.id.activity_wallpapers_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        expendsList = new ArrayList<>();

        coverStoriesAdapter = new CoverStoriesAdapter(Glide.with(this), list, expendsList);
        recyclerView.setAdapter(coverStoriesAdapter);


        coverStoriesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClickListener(int position) {

            }
        });

        int page = 1;
        presenter.getWallpapers(date, page,10);

    }

    @Override
    protected CoverStoryPresenterImpl createPresenter() {
        return new CoverStoryPresenterImpl(this);
    }


    @Override
    public void onSuccess(List<WallpaperBean> bean) {
        list.addAll(bean);
        for (int i=0;i<bean.size();i++){
            expendsList.add(false);
        }
        coverStoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String s) {
        Toast.makeText(CoverStoriesActivity.this, s, Toast.LENGTH_SHORT).show();
    }


    public boolean isSlideToBottom(RecyclerView recyclerView) {
        //int itemHeight = layoutManager.getFocusedChild().getHeight();
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;

        return false;
    }
}
