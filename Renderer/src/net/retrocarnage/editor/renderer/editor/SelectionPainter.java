package net.retrocarnage.editor.renderer.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import net.retrocarnage.editor.model.Selectable;

/**
 * Paints a border arount the current selection (if there is one).
 *
 * @author Thomas Werner
 */
class SelectionPainter {

    private final Graphics2D g2d;
    private final Selectable selection;
    private final float scalingFactor;

    public SelectionPainter(final Graphics2D g2d, final Selectable selection, final float scalingFactor) {
        this.g2d = g2d;
        this.selection = selection;
        this.scalingFactor = scalingFactor;
    }

    public void paintSelectionBorder() {
        if (null != selection) {
            final Rectangle selectionRect = selection.getScaledPosition(scalingFactor);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(selectionRect.x, selectionRect.y, selectionRect.width, selectionRect.height);
        }
    }

}
