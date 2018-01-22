package com.wallpaper.bing;

import android.app.Application;
import android.graphics.Typeface;

import com.squareup.leakcanary.LeakCanary;

import java.lang.reflect.Field;

/**
 * author GaoPC
 * date 2018-01-15 17:44
 * description
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initTypeface();

        LeakCanary.install(this);

    }

    private void initTypeface() {
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, Typeface.createFromAsset(getAssets(), "fonts/BingSMDL.ttf"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
