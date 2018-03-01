package com.wallpaper.bing.receiver;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;

import com.wallpaper.bing.fragment.GeneralPreferenceFragment;
import com.wallpaper.bing.network.BingUrl;
import com.wallpaper.bing.network.RetrofitHelper;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpapersEntity;
import com.wallpaper.bing.util.DateUtil;
import com.wallpaper.bing.util.NetworkUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author Gao
 * date 2018/2/9
 * description
 */

public class AutoReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(final Context context, Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean auto = sharedPreferences.getBoolean(GeneralPreferenceFragment.AUTO_CHANGE_WALLPAPER, false);
        if (auto && intent.getAction() != null && intent.getAction().equals("android.intent.action.USER_PRESENT")
                && NetworkUtil.isNetworkAvailable(context)) {
            Log.i("AlarmReceiver", "onReceive: android.intent.action.USER_PRESENT");
            String autoDate = sharedPreferences.getString("wallpaper_date", "");
            if (!autoDate.equals(DateUtil.getCurrentDate())) {
                RetrofitHelper.getBingApi().getLastWallpaper()
                        .map(new Function<BaseBean<WallpapersEntity>, String>() {

                            @Override
                            public String apply(BaseBean<WallpapersEntity> baseBean) throws Exception {
                                return baseBean.getMessage().getImageUrlMobile();
                            }
                        }).flatMap(new Function<String, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(String s) throws Exception {
                        return RetrofitHelper.getBingApi().getWallpaper(BingUrl.BASE_IMAGE_URL+s);
                    }
                }).map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                        wallpaperManager.setBitmap(BitmapFactory.decodeStream(responseBody.byteStream()));
                        return "success";
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {

                            @Override
                            public void accept(String s) throws Exception {
                                sharedPreferences.edit().putString("wallpaper_date",DateUtil.getCurrentDate()).apply();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
            }
        }
    }


}
