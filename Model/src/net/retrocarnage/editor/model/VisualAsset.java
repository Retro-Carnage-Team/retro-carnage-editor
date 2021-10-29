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

    /**
     * Returns the position - scaled by a given factor.
     *
     * @param factor scaling factor to be applied
     * @return the scaled position
     */
    public Rectangle getScaledPosition(float factor) {
        return new Rectangle(
                (int) (position.x * factor),
                (int) (position.y * factor),
                (int) (position.width * factor),
                (int) (position.height * factor)
        );
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
