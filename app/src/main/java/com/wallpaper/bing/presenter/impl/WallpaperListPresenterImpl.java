package com.wallpaper.bing.presenter.impl;

import com.wallpaper.bing.network.RetrofitHelper;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperBean;
import com.wallpaper.bing.presenter.contract.IWallpaperListContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author GaoPC
 * date 2017-12-16 13:29
 * description
 */

public class WallpaperListPresenterImpl extends BasePresenterImpl implements IWallpaperListContract.CoverStoryPresenter {

    private IWallpaperListContract.CoverStoryView wallpapersView;

    public WallpaperListPresenterImpl(IWallpaperListContract.CoverStoryView wallpapersView) {
        super(wallpapersView);
        this.wallpapersView = wallpapersView;
    }


    @Override
    public void getWallpapers(String date, int page, int pageSize, final int option) {
        compositeDisposable.add(RetrofitHelper.getBingApi().queryWallpapers(date, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<WallpaperBean>>>() {
                    @Override
                    public void accept(@NonNull BaseBean<List<WallpaperBean>> listBaseBean) throws Exception {
                        if (listBaseBean.getStatus().equals("success")) {
                            wallpapersView.onSuccess(listBaseBean.getMessage(),option);
                        } else {
                            wallpapersView.onFailed("请求数据失败");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        wallpapersView.onFailed(throwable.getMessage());
                    }
                }));

    }

}
