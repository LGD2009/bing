package com.wallpaper.bing.presenter.contract;

import com.wallpaper.bing.network.bean.WallpaperBean;
import com.wallpaper.bing.presenter.IBasePresenter;
import com.wallpaper.bing.presenter.IBaseView;

import java.util.List;

/**
 * author GaoPC
 * date 2017-12-16 13:29
 * description
 */

public interface IWallpaperListContract {

    interface CoverStoryView extends IBaseView<List<WallpaperBean>> {

        void onSuccess(List<WallpaperBean> bean, int option);

    }


    interface CoverStoryPresenter extends IBasePresenter {

        /**
         * 返回某一页的壁纸
         *
         * @param dates    从date日期开始，之前的壁纸
         * @param page     第几页，从1开始
         * @param pageSize 每页数量
         * @param option   操作类型
         */
        void getWallpapers(String dates, int page, int pageSize, int option);

    }


}
