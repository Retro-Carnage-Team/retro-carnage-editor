package net.retrocarnage.editor.renderer.editor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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

/**
 * Paints the Sprites that are contained in a stack of Layers.
 *
 * @author Thomas Werner
 */
class SpritePainter {

    private static final Logger logger = Logger.getLogger(SpritePainter.class.getName());

    private final List<Layer> layers;
    private final Graphics2D g2d;

    public SpritePainter(final List<Layer> layers, final Graphics2D g2d) {
        this.layers = layers;
        this.g2d = g2d;
    }

    public void paintSprites() {
        layers.stream()
                .sorted(Collections.reverseOrder()) // draw higher layers on top of lower layers
                .filter((l) -> l.isVisible())
                .forEach((layer) -> layer.getVisualAssets().stream().forEach((va) -> paintVisualAsset(va)));
    }

    private void paintVisualAsset(final VisualAsset va) {
        final Sprite sprite = AssetService.getDefault().getSprite(va.getAssetId());
        if (null == sprite) {
            logger.log(Level.INFO, String.format("Sprite asset not found: %s", va.getAssetId()));
            return;
        }

        if (sprite.isTile()) {
            // TODO: paint image tiles to fill rect
        } else {
            paintScaledSprite(sprite, va.getPosition());
        }
    }

    private void paintScaledSprite(final Sprite sprite, final Rectangle position) {
        try {
            final BufferedImage image = sprite.getImage();
            g2d.drawImage(image, position.x, position.y, position.width, position.height, null);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to read Sprite image", ex);
        }
    }

}
