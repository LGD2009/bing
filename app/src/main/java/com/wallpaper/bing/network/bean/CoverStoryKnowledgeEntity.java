package com.wallpaper.bing.network.bean;

/**
 * author Gao
 * time 2017/12/25
 * description
 */
public class CoverStoryKnowledgeEntity {
    private String id;
    private String wallpaperId;
    private String knowledgeTitle;
    private String knowledgeSubtitle;
    private String knowledgeContent;
    private String knowledgeSrc;

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

    public String getKnowledgeTitle() {
        return knowledgeTitle;
    }

    public void setKnowledgeTitle(String knowledgeTitle) {
        this.knowledgeTitle = knowledgeTitle;
    }

    public String getKnowledgeSubtitle() {
        return knowledgeSubtitle;
    }

    public void setKnowledgeSubtitle(String knowledgeSubtitle) {
        this.knowledgeSubtitle = knowledgeSubtitle;
    }

    public String getKnowledgeContent() {
        return knowledgeContent;
    }

    public void setKnowledgeContent(String knowledgeContent) {
        this.knowledgeContent = knowledgeContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoverStoryKnowledgeEntity that = (CoverStoryKnowledgeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (wallpaperId != null ? !wallpaperId.equals(that.wallpaperId) : that.wallpaperId != null) return false;
        if (knowledgeTitle != null ? !knowledgeTitle.equals(that.knowledgeTitle) : that.knowledgeTitle != null)
            return false;
        if (knowledgeSubtitle != null ? !knowledgeSubtitle.equals(that.knowledgeSubtitle) : that.knowledgeSubtitle != null)
            return false;
        if (knowledgeContent != null ? !knowledgeContent.equals(that.knowledgeContent) : that.knowledgeContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (wallpaperId != null ? wallpaperId.hashCode() : 0);
        result = 31 * result + (knowledgeTitle != null ? knowledgeTitle.hashCode() : 0);
        result = 31 * result + (knowledgeSubtitle != null ? knowledgeSubtitle.hashCode() : 0);
        result = 31 * result + (knowledgeContent != null ? knowledgeContent.hashCode() : 0);
        return result;
    }

    public String getKnowledgeSrc() {
        return knowledgeSrc;
    }

    public void setKnowledgeSrc(String knowledgeSrc) {
        this.knowledgeSrc = knowledgeSrc;
    }
}
