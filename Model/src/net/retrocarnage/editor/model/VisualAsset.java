package net.retrocarnage.editor.model;

import java.awt.Rectangle;

/**
 * Some visual thing that is placed on the screen.
 *
 * @author Thomas Werner
 */
public interface VisualAsset {

    String getAssetId();

    void setAssetId(final String assetId);

    Rectangle getPosition();

    void setPosition(final Rectangle position);

}
