package net.retrocarnage.editor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Layers are used to organized graphical assets.
 *
 * This makes it easer to work on different aspects of the mission one at a time.
 *
 * @author Thomas Werner
 */
public class Layer {

    public static final String DEFAULT_LAYER_NAME = "Default";

    private boolean locked;
    private String name;
    private boolean visible;
    private List<VisualAsset> visualAssets;

    public Layer() {
        visualAssets = new ArrayList<>();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<VisualAsset> getVisualAssets() {
        return visualAssets;
    }

    public void setVisualAssets(List<VisualAsset> visualAssets) {
        this.visualAssets = visualAssets;
    }

}
