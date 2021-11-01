package net.retrocarnage.editor.renderer.editor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.renderer.common.MemoizedImageScaler;

/**
 * Paints the Sprites that are contained in a stack of Layers.
 *
 * @author Thomas Werner
 */
class SpritePainter {

    private static final Logger logger = Logger.getLogger(SpritePainter.class.getName());
    private static final MemoizedImageScaler imageScaler = new MemoizedImageScaler();

    private final List<Layer> layers;
    private final Graphics2D g2d;
    private final float scalingFactor;

    public SpritePainter(final List<Layer> layers, final Graphics2D g2d, final float scalingFactor) {
        this.layers = layers;
        this.g2d = g2d;
        this.scalingFactor = scalingFactor;
    }

    public void paintSprites() {
        layers.stream()
                .sorted(Collections.reverseOrder()) // draw higher layers on top of lower layers
                .filter((l) -> l.isVisible())
                .forEach((layer)
                        -> layer.getVisualAssets()
                        .stream()
                        .sorted(Collections.reverseOrder())
                        .forEach((va) -> paintVisualAsset(va))
                );
    }

    private void paintVisualAsset(final VisualAsset va) {
        final Sprite sprite = AssetService.getDefault().getSprite(va.getAssetId());
        if (null == sprite) {
            logger.log(Level.INFO, String.format("Sprite asset not found: %s", va.getAssetId()));
            return;
        }

        try {
            final BufferedImage scaledImage = imageScaler.getScaledSpriteImage(sprite, scalingFactor);
            final Rectangle scaledPosition = (1.0f == scalingFactor)
                    ? va.getPosition()
                    : va.getScaledPosition(scalingFactor);
            if (sprite.isTile()) {
                paintTiledSprite(scaledImage, scaledPosition);
            } else {
                paintScaledSprite(scaledImage, scaledPosition);
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to read Sprite image", ex);
        }

    }

    private void paintScaledSprite(final BufferedImage image, final Rectangle position) {
        g2d.drawImage(image, position.x, position.y, position.width, position.height, null);
    }

    private void paintTiledSprite(final BufferedImage image, final Rectangle position) {
        final Rectangle anchor = new Rectangle(0, 0, position.width, position.height);
        g2d.setPaint(new TexturePaint(image, anchor));
        g2d.fill(position);
    }

}
