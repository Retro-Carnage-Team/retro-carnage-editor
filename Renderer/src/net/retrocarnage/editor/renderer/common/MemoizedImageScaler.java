package net.retrocarnage.editor.renderer.common;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.retrocarnage.editor.model.Sprite;

/**
 * Scales images by a certain scaling factor.
 *
 * @author Thomas Werner
 */
public class MemoizedImageScaler {

    private final Map<String, BufferedImage> images;
    private float lastScalingFactor = 1f;

    public MemoizedImageScaler() {
        images = new HashMap<>();
    }

    /**
     * Returns a scaled instance of the given sprite.
     *
     * @param sprite Sprite to be scaled
     * @param scalingFactor scaling factor to be applied
     * @return scaled instance of the sprite
     * @throws IOException when something goes wrong
     */
    public BufferedImage getScaledSpriteImage(final Sprite sprite, float scalingFactor) throws IOException {
        if (lastScalingFactor != scalingFactor) {
            images.clear();
            lastScalingFactor = scalingFactor;
        }

        if (images.containsKey(sprite.getId())) {
            return images.get(sprite.getId());
        }

        final BufferedImage image = sprite.getImage();
        final BufferedImage scaledImage = (1.0f == scalingFactor)
                ? image
                : bufferImage(image.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH));
        images.put(sprite.getId(), scaledImage);
        return scaledImage;
    }

    /**
     * Convert an Image to a BufferedImage.
     *
     * @param img the Image to be buffered
     * @return the BufferedImage
     * @see https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
     */
    private static BufferedImage bufferImage(final Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        final BufferedImage bimage = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        final Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

}
