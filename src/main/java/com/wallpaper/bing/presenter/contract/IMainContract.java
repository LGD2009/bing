package com.wallpaper.bing.presenter.contract;

import android.content.Context;

import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.IBasePresenter;
import com.wallpaper.bing.presenter.IBaseView;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public interface IMainContract {

    interface IMainView extends IBaseView<BaseBean<WallpaperInfoBean>> {

        Context getContext();

        void onSuccess(String s);

        void showDialog();

        void dismissDialog();

    }

    interface IMainPresenter extends IBasePresenter {

        void getWallpaper(String date);

        void setDesktopWallpaper(String url);

    }

}
