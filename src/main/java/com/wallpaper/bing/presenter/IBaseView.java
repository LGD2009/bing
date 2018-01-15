package com.wallpaper.bing.presenter;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public interface IBaseView<T> {

    void onSuccess(T bean);

    void onFailed(String s);

}
