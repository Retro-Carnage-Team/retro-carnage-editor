package net.retrocarnage.editor.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Helper that can be used to load an icon for a node.
 *
 * @author Thomas Werner
 */
public final class IconUtil {

    private static final Logger logger = Logger.getLogger(IconUtil.class.getName());
    private static final Image EMPTY_ICON = buildEmptyIcon();

    private IconUtil() {
        // You are not expected to create instances of this class.
    }

    /**
     * Returns a BufferedImage as the result of decoding a supplied InputStream with an ImageReader chosen automatically
     * from among those currently registered. Closes the given InputStream.
     * 
     * @param resource InputStream containing image data
     * @return Image containing the data from resource
     */
    public static Image loadIcon(final InputStream resource) {
        if(null == resource) {
            return EMPTY_ICON;
        }
                
        try {
            return ImageIO.read(resource);
        } catch (final Exception ex) {
            logger.log(Level.WARNING, "Failed to load icon", ex);
            return EMPTY_ICON;
        } finally {
            try {        
                resource.close();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to close input stream for icon", ex);                
            }
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
