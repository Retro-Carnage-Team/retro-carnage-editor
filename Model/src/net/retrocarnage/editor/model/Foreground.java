package net.retrocarnage.editor.model;

import java.awt.Rectangle;

/**
 * A graphical asset used to draw the foreground of a mission.
 *
 * @author Thomas Werner
 */
public class Foreground implements VisualAsset {

    private String assetId;
    private Rectangle position;

    @Override
    public String getAssetId() {
        return assetId;
    }

    @Override
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

}
