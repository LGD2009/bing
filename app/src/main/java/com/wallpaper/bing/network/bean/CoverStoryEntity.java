package com.wallpaper.bing.network.bean;

/**
 * author Gao
 * time 2017/12/25
 * description
 */
public class CoverStoryEntity {
    private String id;
    private String wallpaperId;
    private String coverTitle;
    private String coverAttribute;
    private String thumbnail;
    private String title;
    private String subtitle;
    private String content;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoverStoryEntity that = (CoverStoryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (wallpaperId != null ? !wallpaperId.equals(that.wallpaperId) : that.wallpaperId != null) return false;
        if (coverTitle != null ? !coverTitle.equals(that.coverTitle) : that.coverTitle != null) return false;
        if (coverAttribute != null ? !coverAttribute.equals(that.coverAttribute) : that.coverAttribute != null)
            return false;
        if (thumbnail != null ? !thumbnail.equals(that.thumbnail) : that.thumbnail != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (subtitle != null ? !subtitle.equals(that.subtitle) : that.subtitle != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (wallpaperId != null ? wallpaperId.hashCode() : 0);
        result = 31 * result + (coverTitle != null ? coverTitle.hashCode() : 0);
        result = 31 * result + (coverAttribute != null ? coverAttribute.hashCode() : 0);
        result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
