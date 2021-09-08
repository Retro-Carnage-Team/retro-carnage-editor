package net.retrocarnage.editor.assetmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A sprite that can be used as a visual asset of a level.
 * 
 * @author Thomas Werner
 */
public class Sprite {

    private String id;
    private AttributionData attributionData;
    private String relativePath;
    private String relativePathThumbnail;
    private String name;
    private List<String> tags;

    public Sprite() {
        this.tags = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }    
    
    public AttributionData getAttributionData() {
        return attributionData;
    }

    public void setAttributionData(final AttributionData attributionData) {
        this.attributionData = attributionData;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(final String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePathThumbnail() {
        return relativePathThumbnail;
    }

    public void setRelativePathThumbnail(final String relativePathThumbnail) {
        this.relativePathThumbnail = relativePathThumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }    
    
    public void setTags(final List<String> tags) {
        this.tags = tags;
    }
    
}
