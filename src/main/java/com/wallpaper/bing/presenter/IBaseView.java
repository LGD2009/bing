package com.wallpaper.bing.presenter;

import android.support.v7.app.AppCompatActivity;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public interface IBaseView<T> {

    AppCompatActivity getAppCompatContext();

    void onSuccess(T bean);

    void onFailed(String s);

}
