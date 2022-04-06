package net.retrocarnage.editor.renderer.editor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.renderer.common.MemoizedImageScaler;

/**
 * Paints the player model for size comparison.
 *
 * @author Thomas Werner
 */
class PlayerModelPainter {

    private static final Logger logger = Logger.getLogger(PlayerModelPainter.class.getName());
    private static final MemoizedImageScaler imageScaler = new MemoizedImageScaler();
    private static final String MODEL_PATH = "/net/retrocarnage/editor/renderer/images/player.png";
    private static final String MODEL_KEY = "player model";

    private final Graphics2D g2d;
    private final float scalingFactor;
    private final Rectangle viewRect;

    public PlayerModelPainter(Graphics2D g2d, float scalingFactor, final Rectangle viewRect) {
        this.g2d = g2d;
        this.scalingFactor = scalingFactor;
        this.viewRect = viewRect;
    }

    public void paintPlayerModel() {
        final BufferedImage playerModel = loadPlayerImage();
        if (null == playerModel) {
            logger.log(Level.SEVERE, String.format("Player asset not found: %s", MODEL_PATH));
            return;
        }

        try {
            final BufferedImage scaledImage = imageScaler.getScaledImage(playerModel, MODEL_KEY, scalingFactor);
            g2d.drawImage(
                    scaledImage,
                    viewRect.x + 50,
                    viewRect.y + (viewRect.height - scaledImage.getHeight()) / 2,
                    scaledImage.getWidth(),
                    scaledImage.getHeight(),
                    null
            );
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to scale or draw player model image", ex);
        }
    }

    private BufferedImage loadPlayerImage() {
        try {
            return ImageIO.read(PlayerModelPainter.class.getResource(MODEL_PATH));
        } catch (final IOException ex) {
            logger.log(Level.WARNING, "Failed to load resource", ex);
            return null;
        }
    }

}
