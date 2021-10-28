package net.retrocarnage.editor.model;

import java.awt.Rectangle;

/**
 * A visual asset.
 *
 * @author Thomas Werner
 */
public class VisualAsset implements Selectable {

    private String assetId;
    private Rectangle position;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(final String assetId) {
        this.assetId = assetId;
    }

    @Override
    public Rectangle getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Rectangle position) {
        this.position = position;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

}
