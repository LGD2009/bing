package com.wallpaper.bing.network.bean;

/**
 * author Gao
 * time 2017/12/27
 * description
 */
public class WallpaperOptionEntity {
    private int id;
    private String wallpaperId;
    private Integer heart;
    private Integer download;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWallpaperId() {
        return wallpaperId;
    }

    public void setWallpaperId(String wallpaperId) {
        this.wallpaperId = wallpaperId;
    }

    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallpaperOptionEntity that = (WallpaperOptionEntity) o;

        if (id != that.id) return false;
        if (wallpaperId != null ? !wallpaperId.equals(that.wallpaperId) : that.wallpaperId != null) return false;
        if (heart != null ? !heart.equals(that.heart) : that.heart != null) return false;
        if (download != null ? !download.equals(that.download) : that.download != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (wallpaperId != null ? wallpaperId.hashCode() : 0);
        result = 31 * result + (heart != null ? heart.hashCode() : 0);
        result = 31 * result + (download != null ? download.hashCode() : 0);
        return result;
    }
}
