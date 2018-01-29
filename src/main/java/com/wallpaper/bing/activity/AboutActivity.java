package com.wallpaper.bing.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

import com.wallpaper.bing.R;
import com.wallpaper.bing.presenter.impl.BasePresenterImpl;

/**
 * author GaoPC
 * date 2018-01-26 14:16
 * description 关于页面
 */

public class AboutActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
            getWindow().setReturnTransition(new Slide().setDuration(500));
            getWindow().setReenterTransition(new Slide().setDuration(500));
            getWindow().setExitTransition(new Slide().setDuration(500));
        }

        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_about_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.super.onBackPressed();
            }
        });

        TextView versionText = (TextView) findViewById(R.id.activity_about_version);
        try {
            String versionName = "v " + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            versionText.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected BasePresenterImpl createPresenter() {
        return new BasePresenterImpl(this);
    }
}
