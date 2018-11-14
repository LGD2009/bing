package com.wallpaper.bing.network.bean;

/**
 * author Gao
 * time 2017/12/25
 * description
 */
public class CoverStoryRelevantEntity {
    private String id;
    private String wallpaperId;
    private String relevantTitle;
    private String relevantContent;
    private Integer relevantIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWallpaperId() {
        return wallpaperId;
    }

    public void setWallpaperId(String wallpaperId) {
        this.wallpaperId = wallpaperId;
    }

    public String getRelevantTitle() {
        return relevantTitle;
    }

    public void setRelevantTitle(String relevantTitle) {
        this.relevantTitle = relevantTitle;
    }

    public String getRelevantContent() {
        return relevantContent;
    }

    public void setRelevantContent(String relevantContent) {
        this.relevantContent = relevantContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoverStoryRelevantEntity that = (CoverStoryRelevantEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (wallpaperId != null ? !wallpaperId.equals(that.wallpaperId) : that.wallpaperId != null) return false;
        if (relevantTitle != null ? !relevantTitle.equals(that.relevantTitle) : that.relevantTitle != null)
            return false;
        if (relevantContent != null ? !relevantContent.equals(that.relevantContent) : that.relevantContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (wallpaperId != null ? wallpaperId.hashCode() : 0);
        result = 31 * result + (relevantTitle != null ? relevantTitle.hashCode() : 0);
        result = 31 * result + (relevantContent != null ? relevantContent.hashCode() : 0);
        return result;
    }

    public Integer getRelevantIndex() {
        return relevantIndex;
    }

    public void setRelevantIndex(Integer relevantIndex) {
        this.relevantIndex = relevantIndex;
    }
}
