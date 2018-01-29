package com.wallpaper.bing.presenter.impl;

import android.support.v7.app.AppCompatDelegate;

import com.wallpaper.bing.activity.SettingsActivity;
import com.wallpaper.bing.presenter.IBasePresenter;
import com.wallpaper.bing.presenter.IBaseView;
import com.wallpaper.bing.rxbus.RxBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * author GaoPC
 * date 2018-01-26 11:05
 * description basePresenter 设置夜间模式
 * <p>
 * Activity 要实现 夜间模式 需要继承 @see {@link com.wallpaper.bing.activity.BaseAppCompatActivity}
 * 并实现BasePresenterImpl @sse {@link SettingsActivity#createPresenter()}
 * </p>
 */

public class BasePresenterImpl implements IBasePresenter {

    private IBaseView baseView;

    CompositeDisposable compositeDisposable;

    public BasePresenterImpl(IBaseView baseView) {
        this.baseView = baseView;
        compositeDisposable = new CompositeDisposable();
    }

    public void setOnChangeNightListener() {
        compositeDisposable.add(
                RxBus.getInstance().getObservable().subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        boolean b = (boolean) o;
                        baseView.getAppCompatContext().getDelegate().setLocalNightMode(b ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                        baseView.getAppCompatContext().recreate();
                    }
                }));
    }

    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }
}
