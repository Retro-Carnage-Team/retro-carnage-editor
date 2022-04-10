package net.retrocarnage.editor.renderer.common;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class wrapps a Graphics2D context and restricts it's operations to the area defined by the gameplay sections.
 * This is useful if you want to paint stuff of the gameplay area that should not overlap onto the background.
 *
 * @author Thomas Werner
 */
public class GamePlayGraphics2D extends DelegatingGraphics2D implements Closeable {

    private static final Logger logger = Logger.getLogger(GamePlayGraphics2D.class.getName());

    private final Rectangle originalClip;

    public GamePlayGraphics2D(final Graphics2D wrappedContext, final Area gameArea) {
        super(wrappedContext);
        if (wrappedContext.getClip() instanceof java.awt.geom.Rectangle2D) {
            originalClip = ((java.awt.geom.Rectangle2D) wrappedContext.getClip()).getBounds();
        } else if (wrappedContext.getClip() instanceof java.awt.Rectangle) {
            originalClip = (Rectangle) wrappedContext.getClip();
        } else if (null != wrappedContext.getClip()) {
            logger.log(Level.WARNING, "Unknown clipping shape!");
            originalClip = null;
        } else {
            originalClip = null;
        }

        if (null != originalClip) {
            final Area componentArea = new Area(originalClip);
            gameArea.intersect(componentArea);
            super.setClip(gameArea);
        }
    }

    @Override
    public void close() {
        if (null != originalClip) {
            super.setClip(originalClip);
        }
    }

}
