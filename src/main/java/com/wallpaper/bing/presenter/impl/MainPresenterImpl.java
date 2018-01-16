package com.wallpaper.bing.presenter.impl;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.wallpaper.bing.network.RetrofitHelper;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.contract.IMainContract;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public class MainPresenterImpl implements IMainContract.IMainPresenter {

    private CompositeDisposable disposable;
    private IMainContract.IMainView mainView;

    public MainPresenterImpl(IMainContract.IMainView mainView) {
        this.mainView = mainView;
        disposable = new CompositeDisposable();
    }

    @Override
    public void getWallpaper(String date) {
        disposable.add(RetrofitHelper.getBingApi().queryWallpaperInfo(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<WallpaperInfoBean>>() {
                    @Override
                    public void accept(@NonNull BaseBean<WallpaperInfoBean> infoBean) throws Exception {
                        mainView.onSuccess(infoBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mainView.onFailed(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void setDesktopWallpaper(String firstUrl, String secondUrl) {
        mainView.showDialog();
        disposable.add(Observable.concatDelayError(Arrays.asList(RetrofitHelper.getBingApi().getWallpaper(firstUrl), RetrofitHelper.getBingApi().getWallpaper(secondUrl)))
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        mainView.dismissDialog();
                        mainView.onSuccess(responseBody);

                        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra("mimeType", "image/*");
                        Uri uri = Uri.parse(MediaStore.Images.Media
                                .insertImage(mainView.getContext().getContentResolver(),
                                        BitmapFactory.decodeStream(responseBody.byteStream()), "设置壁纸", "a"));
                        intent.setData(uri);
                        mainView.getContext().startActivity(intent);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mainView.onFailed("设置壁纸失败：" + throwable.getMessage());
                        mainView.dismissDialog();
                    }
                }));


    }

    //如果没有1080x1920的图片，就加载1920x1080的图片
    @Override
    public void getWallpaperConcat(String firstUrl, String secondUrl) {
        disposable.add(Observable.concatDelayError(Arrays.asList(RetrofitHelper.getBingApi().getWallpaper(firstUrl), RetrofitHelper.getBingApi().getWallpaper(secondUrl)))
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        mainView.onSuccess(responseBody);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mainView.onFailed(throwable.getMessage());
                    }
                })
        );
    }


    @Override
    public void unSubscribe() {
        disposable.clear();
    }
}
