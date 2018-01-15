package com.wallpaper.bing.presenter.impl;

import android.app.WallpaperManager;
import android.graphics.BitmapFactory;

import com.wallpaper.bing.network.RetrofitHelper;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.contract.IMainContract;

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
    public void setDesktopWallpaper(String url) {
        mainView.showDialog();
        disposable.add(RetrofitHelper.getBingApi().getWallpaper(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        WallpaperManager manager = WallpaperManager.getInstance(mainView.getContext());
                        manager.setBitmap(BitmapFactory.decodeStream(responseBody.byteStream()));
                        mainView.onSuccess("设置成功");
                        mainView.dismissDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mainView.onFailed("设置壁纸失败：" + throwable.getMessage());
                        mainView.dismissDialog();
                    }
                }));


    }


    @Override
    public void unSubscribe() {
        disposable.clear();
    }
}
