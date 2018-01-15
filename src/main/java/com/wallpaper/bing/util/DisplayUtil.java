package com.wallpaper.bing.util;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * author GaoPC
 * date 2017-12-16 16:26
 * description 设备分辨率的一些方法
 */

public class DisplayUtil {

    private int widthPixels;
    private int heightPixels;

    private DisplayUtil(){}

    public static DisplayUtil getInstance(){
        return DisplayUtilHolder.displayUtil;
    }

    private static class DisplayUtilHolder{
        private final static DisplayUtil displayUtil= new DisplayUtil();
    }

    public void setPixels(int widthPixels,int heightPixels){
        this.widthPixels=widthPixels;
        this.heightPixels=heightPixels;
    }

    public String getPixels(){
        return "_"+widthPixels+"x"+heightPixels;
    }

    public String getThumbnailPixels(){
        return  "_800x480.jpg";
    }


}
