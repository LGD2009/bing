package com.wallpaper.bing.network.bean;

import java.util.List;

/**
 * author Gao
 * time 2018/01/02
 * description 得到壁纸的所有信息
 */
@SuppressWarnings("unused")
public class WallpaperInfoBean {

    private WallpapersEntity wallpapersEntity;
    private CoverStoryEntity coverStoryEntity;
    private List<CoverStoryKnowledgeEntity> knowledgeEntities;
    private List<CoverStoryRelevantEntity> relevantEntities;
    private WallpaperOptionEntity optionEntity;

    public WallpapersEntity getWallpapersEntity() {
        return wallpapersEntity;
    }

    public void setWallpapersEntity(WallpapersEntity wallpapersEntity) {
        this.wallpapersEntity = wallpapersEntity;
    }

    public CoverStoryEntity getCoverStoryEntity() {
        return coverStoryEntity;
    }

    public void setCoverStoryEntity(CoverStoryEntity coverStoryEntity) {
        this.coverStoryEntity = coverStoryEntity;
    }

    public List<CoverStoryKnowledgeEntity> getKnowledgeEntities() {
        return knowledgeEntities;
    }

    public void setKnowledgeEntities(List<CoverStoryKnowledgeEntity> knowledgeEntities) {
        this.knowledgeEntities = knowledgeEntities;
    }

    public List<CoverStoryRelevantEntity> getRelevantEntities() {
        return relevantEntities;
    }

    public void setRelevantEntities(List<CoverStoryRelevantEntity> relevantEntities) {
        this.relevantEntities = relevantEntities;
    }

    public WallpaperOptionEntity getOptionEntity() {
        return optionEntity;
    }

    public void setOptionEntity(WallpaperOptionEntity optionEntity) {
        this.optionEntity = optionEntity;
    }
}
