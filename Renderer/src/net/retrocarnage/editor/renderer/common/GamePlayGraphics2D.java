package net.retrocarnage.editor.renderer.common;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.Closeable;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class wrapps a Graphics2D context and restricts it's operations to the area defined by the gameplay sections.
 * This is useful if you want to paint stuff of the gameplay area that should not overlap onto the background.
 *
 * @author Thomas Werner
 */
public class GamePlayGraphics2D extends Graphics2D implements Closeable {

    private static final Logger logger = Logger.getLogger(GamePlayGraphics2D.class.getName());

    private final Rectangle originalClip;
    private final Graphics2D wrappedContext;

    public GamePlayGraphics2D(final Graphics2D wrappedContext, final Area gameArea) {
        this.wrappedContext = wrappedContext;
        if (wrappedContext.getClip() instanceof java.awt.geom.Rectangle2D) {
            originalClip = ((java.awt.geom.Rectangle2D) wrappedContext.getClip()).getBounds();
        } else if (wrappedContext.getClip() instanceof java.awt.Rectangle) {
            originalClip = (Rectangle) wrappedContext.getClip();
        } else {
            originalClip = null;
            logger.log(Level.WARNING, "Unknown clipping shape!");
        }

        if (null != originalClip) {
            final Area componentArea = new Area(originalClip);
            gameArea.intersect(componentArea);
            wrappedContext.setClip(gameArea);
        }
    }

    @Override
    public void draw(Shape shape) {
        wrappedContext.draw(shape);
    }

    @Override
    public boolean drawImage(Image image, AffineTransform at, ImageObserver io) {
        return wrappedContext.drawImage(image, at, io);
    }

    @Override
    public void drawImage(BufferedImage bi, BufferedImageOp bio, int i, int i1) {
        wrappedContext.drawImage(bi, bio, i, i1);
    }

    @Override
    public void drawRenderedImage(RenderedImage ri, AffineTransform at) {
        wrappedContext.drawRenderedImage(ri, at);
    }

    @Override
    public void drawRenderableImage(RenderableImage ri, AffineTransform at) {
        wrappedContext.drawRenderableImage(ri, at);
    }

    @Override
    public void drawString(String string, int i, int i1) {
        wrappedContext.drawString(string, i, i1);
    }

    @Override
    public void drawString(String string, float f, float f1) {
        wrappedContext.drawString(string, f, f1);
    }

    @Override
    public void drawString(AttributedCharacterIterator aci, int i, int i1) {
        wrappedContext.drawString(aci, i, i1);
    }

    @Override
    public void drawString(AttributedCharacterIterator aci, float f, float f1) {
        wrappedContext.drawString(aci, f, f1);
    }

    @Override
    public void drawGlyphVector(GlyphVector gv, float f, float f1) {
        wrappedContext.drawGlyphVector(gv, f, f1);
    }

    @Override
    public void fill(Shape shape) {
        wrappedContext.fill(shape);
    }

    @Override
    public boolean hit(Rectangle rctngl, Shape shape, boolean bln) {
        return wrappedContext.hit(rctngl, shape, bln);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return wrappedContext.getDeviceConfiguration();
    }

    @Override
    public void setComposite(Composite cmpst) {
        wrappedContext.setComposite(cmpst);
    }

    @Override
    public void setPaint(Paint paint) {
        wrappedContext.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke stroke) {
        wrappedContext.setStroke(stroke);
    }

    @Override
    public void setRenderingHint(RenderingHints.Key key, Object o) {
        wrappedContext.setRenderingHint(key, o);
    }

    @Override
    public Object getRenderingHint(RenderingHints.Key key) {
        return wrappedContext.getRenderingHint(key);
    }

    @Override
    public void setRenderingHints(Map<?, ?> map) {
        wrappedContext.setRenderingHints(map);
    }

    @Override
    public void addRenderingHints(Map<?, ?> map) {
        wrappedContext.addRenderingHints(map);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return wrappedContext.getRenderingHints();
    }

    @Override
    public void translate(int i, int i1) {
        wrappedContext.translate(i, i1);
    }

    @Override
    public void translate(double d, double d1) {
        wrappedContext.translate(d, d1);
    }

    @Override
    public void rotate(double d) {
        wrappedContext.rotate(d);
    }

    @Override
    public void rotate(double d, double d1, double d2) {
        wrappedContext.rotate(d, d1, d2);
    }

    @Override
    public void scale(double d, double d1) {
        wrappedContext.scale(d, d1);
    }

    @Override
    public void shear(double d, double d1) {
        wrappedContext.shear(d, d1);
    }

    @Override
    public void transform(AffineTransform at) {
        wrappedContext.transform(at);
    }

    @Override
    public void setTransform(AffineTransform at) {
        wrappedContext.setTransform(at);
    }

    @Override
    public AffineTransform getTransform() {
        return wrappedContext.getTransform();
    }

    @Override
    public Paint getPaint() {
        return wrappedContext.getPaint();
    }

    @Override
    public Composite getComposite() {
        return wrappedContext.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        wrappedContext.setBackground(color);
    }

    @Override
    public Color getBackground() {
        return wrappedContext.getBackground();
    }

    @Override
    public Stroke getStroke() {
        return wrappedContext.getStroke();
    }

    @Override
    public void clip(Shape shape) {
        wrappedContext.clip(shape);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return wrappedContext.getFontRenderContext();
    }

    @Override
    public Graphics create() {
        return wrappedContext.create();
    }

    @Override
    public Color getColor() {
        return wrappedContext.getColor();
    }

    @Override
    public void setColor(Color color) {
        wrappedContext.setColor(color);
    }

    @Override
    public void setPaintMode() {
        wrappedContext.setPaintMode();
    }

    @Override
    public void setXORMode(Color color) {
        wrappedContext.setXORMode(color);
    }

    @Override
    public Font getFont() {
        return wrappedContext.getFont();
    }

    @Override
    public void setFont(Font font) {
        wrappedContext.setFont(font);
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return wrappedContext.getFontMetrics(font);
    }

    @Override
    public Rectangle getClipBounds() {
        return wrappedContext.getClipBounds();
    }

    @Override
    public void clipRect(int i, int i1, int i2, int i3) {
        wrappedContext.clearRect(i, i1, i2, i3);
    }

    @Override
    public void setClip(int i, int i1, int i2, int i3) {
        wrappedContext.setClip(i, i1, i2, i3);
    }

    @Override
    public Shape getClip() {
        return wrappedContext.getClip();
    }

    @Override
    public void setClip(Shape shape) {
        wrappedContext.setClip(shape);
    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5) {
        wrappedContext.copyArea(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3) {
        wrappedContext.drawLine(i, i1, i2, i3);
    }

    @Override
    public void fillRect(int i, int i1, int i2, int i3) {
        wrappedContext.fillRect(i, i1, i2, i3);
    }

    @Override
    public void clearRect(int i, int i1, int i2, int i3) {
        wrappedContext.clearRect(i, i1, i2, i3);
    }

    @Override
    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        wrappedContext.drawRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        wrappedContext.fillRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawOval(int i, int i1, int i2, int i3) {
        wrappedContext.drawOval(i, i1, i2, i3);
    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3) {
        wrappedContext.fillOval(i, i1, i2, i3);
    }

    @Override
    public void drawArc(int i, int i1, int i2, int i3, int i4, int i5) {
        wrappedContext.drawArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillArc(int i, int i1, int i2, int i3, int i4, int i5) {
        wrappedContext.fillArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawPolyline(int[] ints, int[] ints1, int i) {
        wrappedContext.drawPolyline(ints, ints1, i);
    }

    @Override
    public void drawPolygon(int[] ints, int[] ints1, int i) {
        wrappedContext.drawPolygon(ints, ints1, i);
    }

    @Override
    public void fillPolygon(int[] ints, int[] ints1, int i) {
        wrappedContext.fillPolygon(ints, ints1, i);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, ImageObserver io) {
        return wrappedContext.drawImage(image, i, i1, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, ImageObserver io) {
        return wrappedContext.drawImage(image, i, i1, i2, i3, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, Color color, ImageObserver io) {
        return wrappedContext.drawImage(image, i, i1, color, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, Color color, ImageObserver io) {
        return wrappedContext.drawImage(image, i, i1, i2, i3, color, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, ImageObserver io) {
        return wrappedContext.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, Color color, ImageObserver io) {
        return wrappedContext.drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, color, io);
    }

    @Override
    public void dispose() {
        wrappedContext.dispose();
    }

    @Override
    public void close() {
        if (null != originalClip) {
            wrappedContext.setClip(originalClip);
        }
    }

}
