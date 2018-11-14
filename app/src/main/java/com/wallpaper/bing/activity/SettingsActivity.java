package com.wallpaper.bing.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;

import com.wallpaper.bing.R;
import com.wallpaper.bing.fragment.GeneralPreferenceFragment;
import com.wallpaper.bing.presenter.impl.BasePresenterImpl;


public class SettingsActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
            getWindow().setReturnTransition(new Slide().setDuration(500));
            getWindow().setReenterTransition(new Slide().setDuration(500));
            getWindow().setExitTransition(new Slide().setDuration(500));
        }

        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_settings_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.super.onBackPressed();
            }
        });

        GeneralPreferenceFragment preferenceFragment = new GeneralPreferenceFragment();

        getFragmentManager().beginTransaction().replace(R.id.activity_settings_frame, preferenceFragment).commit();

    }

    @Override
    protected BasePresenterImpl createPresenter() {
        return new BasePresenterImpl(this);
    }

}
