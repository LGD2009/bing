package com.wallpaper.bing.network.bean;

/**
 * author GaoPC
 * date 2018-01-14 17:14
 * description
 */

@SuppressWarnings("unused")
public class WallpaperBean {

    /**
     * id : 20180113
     * date : 2018-01-13
     * copyright : 沃尔夫克里克山口附近被火灾破坏的森林，美国科罗拉多州
     * imageUrl : bing/201801/EnglemannSpruceForest_ZH-CN11994077642_1920x1080.jpg
     * coverTitle : 不容小觑的力量
     * coverAttribute : 美国，米纳勒尔县
     * thumbnail : http://s4.cn.bing.net/th?id=OJ.iEH5PhIDztnM0g&pid=MSNJVFeeds
     * title : 大自然的强大力量
     * subtitle : 那些骇人听闻的自然灾害
     * content : 沃尔夫克里克山口是一条高山路线，在冬天很难通行，随着道路从山顶向下蔓延，海拔高度也不断下降。虽然路边的这些树被野火破坏了，但周围森林里的树木还是受到了云杉甲虫的入侵。这种微小但致命的甲虫侵染了科罗拉多州高海拔地区多达90%的英格曼云杉树，包括沃尔夫克里克山口附近的树木，破坏了大片森林。
     * heart : 0
     * download : 0
     */

    private String id;
    private String date;
    private String copyright;
    private String imageUrl;
    private String imageUrlMobile;
    private String coverTitle;
    private String coverAttribute;
    private String thumbnail;
    private String title;
    private String subtitle;
    private String content;
    private int heart;
    private int download;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlMobile() {
        return imageUrlMobile;
    }

    public void setImageUrlMobile(String imageUrlMobile) {
        this.imageUrlMobile = imageUrlMobile;
    }

    public String getCoverTitle() {
        return coverTitle;
    }

    public void setCoverTitle(String coverTitle) {
        this.coverTitle = coverTitle;
    }

    public String getCoverAttribute() {
        return coverAttribute;
    }

    public void setCoverAttribute(String coverAttribute) {
        this.coverAttribute = coverAttribute;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }
}
