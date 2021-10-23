package net.retrocarnage.editor.model;

import java.awt.Rectangle;

/**
 * A visual asset.
 *
 * @author Thomas Werner
 */
public class VisualAsset {

    private String assetId;
    private Rectangle position;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(final String assetId) {
        this.assetId = assetId;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(final Rectangle position) {
        this.position = position;
    }

}
