package net.retrocarnage.editor.renderer.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Builds various textures.
 *
 * @author Thomas Werner
 */
public class TextureFactory {

    private TextureFactory() {
        // intentionally empty
    }

    /**
     * Gets the texture to be used to paint goals.
     *
     * @param sideLength specifies the width / height of the texture image. Used for zooming.
     * @return the texture
     */
    public static BufferedImage buildGoalTexture(final int sideLength) {
        final BufferedImage bi = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = bi.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, sideLength, sideLength);
        g2d.setComposite(AlphaComposite.SrcOver);

        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, sideLength / 2, sideLength / 2);
        g2d.fillRect(sideLength / 2, sideLength / 2, sideLength, sideLength);
        g2d.dispose();
        return bi;
    }

    /**
     * Gets the texture to be used to paint an obstacle with the given parameters.
     *
     * @param sideLength specifies the width / height of the texture image. Used for zooming.
     * @param bullets the texture will have an additional line of it stops bullets.
     * @param explosives the texture will have a diamond pattern of it stops explosives, too.
     * @return the texture
     */
    public static BufferedImage buildObstacleTexture(
            final int sideLength,
            final boolean bullets,
            final boolean explosives
    ) {
        final BufferedImage bi = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = bi.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, sideLength, sideLength);
        g2d.setComposite(AlphaComposite.SrcOver);

        if (explosives) {
            g2d.setColor(Color.lightGray);
            g2d.fillPolygon(
                    new int[]{0, sideLength, sideLength / 2},
                    new int[]{0, 0, sideLength / 2},
                    3
            );
            g2d.fillPolygon(
                    new int[]{0, sideLength, sideLength / 2},
                    new int[]{sideLength, sideLength, sideLength / 2},
                    3
            );
        }

        g2d.setColor(Color.darkGray);
        if (bullets) {
            g2d.drawLine(0, 0, sideLength - 1, sideLength - 1);
        }

        g2d.drawLine(0, sideLength - 1, sideLength - 1, 0);
        g2d.dispose();
        return bi;
    }

}
