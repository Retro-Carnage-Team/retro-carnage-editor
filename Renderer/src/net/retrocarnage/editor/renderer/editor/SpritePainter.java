package net.retrocarnage.editor.renderer.editor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Position;
import net.retrocarnage.editor.model.Rotation;
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
                .filter(l -> (l.isVisible()))
                .map(l -> l.getVisualAssets())
                .forEachOrdered(visualAssets -> {
                    for (int idx = visualAssets.size() - 1; idx >= 0; idx--) {
                        paintVisualAsset(visualAssets.get(idx));
                    }
                });
    }

    private void paintVisualAsset(final VisualAsset va) {
        final Sprite sprite = AssetService.getDefault().getSprite(va.getAssetId());
        if (null == sprite) {
            logger.log(Level.INFO, String.format("Sprite asset not found: %s", va.getAssetId()));
            return;
        }

        try {
            BufferedImage scaledImage = imageScaler.getScaledSpriteImage(sprite, scalingFactor);
            final Position scaledPosition = (1.0f == scalingFactor)
                    ? va.getPosition()
                    : va.getScaledPosition(scalingFactor);

            if (Rotation.None != va.getRotation()) {
                scaledImage = rotateImage(scaledImage, va.getRotation());
            }

            if (sprite.isTile()) {
                paintTiledSprite(
                        scaledImage,
                        new Dimension(scaledImage.getWidth(), scaledImage.getHeight()),
                        scaledPosition
                );
            } else {
                paintScaledSprite(scaledImage, scaledPosition);
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to read Sprite image", ex);
        }
    }

    private void paintScaledSprite(final BufferedImage image, final Position position) {
        g2d.drawImage(image, position.getX(), position.getY(), position.getWidth(), position.getHeight(), null);
    }

    private void paintTiledSprite(final BufferedImage image, final Dimension tileSize, final Position position) {
        final Rectangle anchor = new Rectangle(0, 0, tileSize.width, tileSize.height);
        g2d.setPaint(new TexturePaint(image, anchor));
        g2d.fill(position.toRectangle());
    }

    /**
     * Rotates a given BufferedImages by a given angle.
     *
     * @param image source image to be rotated
     * @param rotation rotation to be applied
     * @return the rotated image
     * @see https://blog.idrsolutions.com/2019/05/image-rotation-in-java/
     */
    private static BufferedImage rotateImage(final BufferedImage image, final Rotation rotation) {
        final double rads = Math.toRadians(rotation.getDegrees());
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads, 0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image, rotatedImage);
        return rotatedImage;
    }

}
