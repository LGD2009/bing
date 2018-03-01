package com.wallpaper.bing.network;

import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.network.bean.WallpapersEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public interface BingApi {


    /**
     * 得到某一天的壁纸和相关信息
     * @param date  日期
     * @return  WallpaperInfoBean
     */
    @FormUrlEncoded
    @POST("wallpapers/queryWallpaperInfo")
    Observable<BaseBean<WallpaperInfoBean>> queryWallpaperInfo(@Field("date")String date);

    /**
     * 得到壁纸
     * @param date  日期
     * @return  WallpaperInfoBean
     */
    @FormUrlEncoded
    @POST("wallpapers/queryWallpapers")
    Observable<BaseBean<List<WallpaperBean>>> queryWallpapers(@Field("date")String date, @Field("page")int page, @Field("pageSize")int pageSize);

    /**
     * 下载壁纸
     * @param url   图片地址
     * @return  数据流
     */
    @GET
    Observable<ResponseBody> getWallpaper(@Url String url);

    /**
     * 得到最近的壁纸信息
     * @return WallpapersEntity
     */
    @GET("wallpapers/currentWallpaperEntity")
    Observable<BaseBean<WallpapersEntity>> getLastWallpaper();

}
