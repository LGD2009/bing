package com.wallpaper.bing.util;

/**
 * author GaoPC
 * date 2017-12-18 20:21
 * description
 */

public class StringUtil {


    /**
     * @param storyImageUrl coverstory 接口返回的 imageUrl
     * @return 图片的baseUrl
     *
     * @see com.wallpaper.bing.network.BingApi#getCoverStory(String)
     * imageUrl --------  http://hpimges.blob.core.chinacloudapi.cn/coverstory/watermark_snowflake_zh-cn7496591838_1920x1080.jpg
     * @see com.wallpaper.bing.network.BingApi#getWallpaper(String, int, int)
     * urlbase --------- /az/hprichbg/rb/Snowflake_ZH-CN7496591838
     * <p>
     * 根据coverstory的imageUrl得到baseUrl
     */
    public static String imageBaseUrl(String storyImageUrl) {
        int beginIndex = storyImageUrl.indexOf("/watermark_");
        int endIndex = storyImageUrl.lastIndexOf("_");
        return storyImageUrl.substring(beginIndex, endIndex).toLowerCase();
    }

}
