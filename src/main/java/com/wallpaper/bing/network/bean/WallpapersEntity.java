package com.wallpaper.bing.network.bean;

import java.sql.Timestamp;

/**
 * author Gao
 * time 2017/12/25
 * description
 */
public class WallpapersEntity {
    private String id;
    private Timestamp date;
    private String copyright;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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


}
