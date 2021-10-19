package net.retrocarnage.editor.model;

import java.awt.Rectangle;
import java.awt.Shape;

/**
 * A graphical asset used to draw the background of a mission.
 *
 * @author Thomas Werner
 */
public class Background implements VisualAsset {

    private String assetId;
    private Shape clip;
    private Rectangle position;

    @Override
    public String getAssetId() {
        return assetId;
    }

    @Override
    public void setAssetId(final String assetId) {
        this.assetId = assetId;
    }

    public Shape getClip() {
        return clip;
    }

    public void setClip(Shape clip) {
        this.clip = clip;
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
