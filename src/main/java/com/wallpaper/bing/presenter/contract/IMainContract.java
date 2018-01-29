package com.wallpaper.bing.presenter.contract;

import android.graphics.Bitmap;

import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.IBasePresenter;
import com.wallpaper.bing.presenter.IBaseView;

import java.io.InputStream;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public interface IMainContract {

    interface IMainView extends IBaseView<BaseBean<WallpaperInfoBean>> {

        void onSuccess(Object bean,int option);

        void showDialog();

        void dismissDialog();

    }

    interface IMainPresenter extends IBasePresenter {

        void getWallpaper(String date);

        void getWallpaper(String imageUrl,int option);

        void setDesktopWallpaper(Bitmap bitmap);

        void downloadWallpaper(InputStream inputStream, String picName);

    }

}
