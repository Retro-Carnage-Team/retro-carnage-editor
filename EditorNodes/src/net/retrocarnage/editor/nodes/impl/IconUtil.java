package net.retrocarnage.editor.nodes.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Helper that can be used to load an icon for a node.
 *
 * @author Thomas Werner
 */
public class IconUtil {

    private static final Logger logger = Logger.getLogger(IconUtil.class.getName());
    private static final Image EMPTY_ICON = buildEmptyIcon();

    public static Image loadIcon(final String resourcePath) {
        try {
            return ImageIO.read(IconUtil.class.getResourceAsStream(resourcePath));
        } catch (final IOException ex) {
            logger.log(Level.WARNING, "Failed to load icon for MissionNode", ex);
            return EMPTY_ICON;
        }
    }

    private static Image buildEmptyIcon() {
        final BufferedImage result = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = result.createGraphics();
        g2d.setColor(new Color(255, 255, 255, 255));
        g2d.fillRect(0, 0, 1, 1);
        g2d.dispose();
        return result;
    }

}
