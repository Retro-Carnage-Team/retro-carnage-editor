package net.retrocarnage.editor.assetmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A song that can be used as background music for a level.
 * 
 * @author Thomas Werner
 */
public class Music {

    private String id;
    private AttributionData attributionData;
    private String name;
    private String relativePath;    
    private List<String> tags;

    public Music() {
        this.tags = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public AttributionData getAttributionData() {
        return attributionData;
    }

    public void setAttributionData(AttributionData attributionData) {
        this.attributionData = attributionData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
    
    public List<String> getTags() {
        return tags;
    }    
    
    public void setTags(final List<String> tags) {
        this.tags = tags;
    }
    
}
