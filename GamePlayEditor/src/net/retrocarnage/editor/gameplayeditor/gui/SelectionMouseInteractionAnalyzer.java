package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.zoom.ZoomService;

/**
 * Analyzes the interaction between a Selectable and the mouse.
 *
 * @author Thomas Werner
 */
public class SelectionMouseInteractionAnalyzer {

    static final int RESIZE_PRECISION = 5;

    private final boolean mouseInSelection;
    private final boolean mouseInTopResizeArea;
    private final boolean mouseInRightResizeArea;
    private final boolean mouseInBottomResizeArea;
    private final boolean mouseInLeftResizeArea;

    /**
     * Creates a new instance of SelectionMouseInteractionAnalyzer.
     *
     * @param selection selected element
     * @param mousePosition mouse position relative to the GamePlay
     */
    public SelectionMouseInteractionAnalyzer(final Selectable selection, final Point mousePosition) {
        float zoomFactor = (float) (ZoomService.getDefault().getZoomLevel() / 100.0);
        final Rectangle selectionRect = selection.getScaledPosition(zoomFactor);

        mouseInSelection = selectionRect.contains(mousePosition);
        if (mouseInSelection) {
            final Dimension reducedHeight = new Dimension(selectionRect.width, RESIZE_PRECISION);
            final Dimension reducedWidth = new Dimension(RESIZE_PRECISION, selectionRect.height);
            mouseInTopResizeArea = new Rectangle(selectionRect.getLocation(), reducedHeight).contains(mousePosition);
            mouseInLeftResizeArea = new Rectangle(selectionRect.getLocation(), reducedWidth).contains(mousePosition);
            mouseInBottomResizeArea = new Rectangle(new Point(
                    selectionRect.x,
                    selectionRect.y + selectionRect.height - RESIZE_PRECISION
            ), reducedHeight).contains(mousePosition);
            mouseInRightResizeArea = new Rectangle(new Point(
                    selectionRect.x + selectionRect.width - RESIZE_PRECISION,
                    selectionRect.y
            ), reducedWidth).contains(mousePosition);
        } else {
            mouseInTopResizeArea = false;
            mouseInRightResizeArea = false;
            mouseInBottomResizeArea = false;
            mouseInLeftResizeArea = false;
        }
    }

    public boolean isMouseInSelection() {
        return mouseInSelection;
    }

    public boolean isMouseInTopResizeArea() {
        return mouseInTopResizeArea;
    }

    public boolean isMouseInRightResizeArea() {
        return mouseInRightResizeArea;
    }

    public boolean isMouseInBottomResizeArea() {
        return mouseInBottomResizeArea;
    }

    public boolean isMouseInLeftResizeArea() {
        return mouseInLeftResizeArea;
    }

    public Cursor getCursor() {
        if (isMouseInTopResizeArea() && isMouseInLeftResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
        } else if (isMouseInTopResizeArea() && isMouseInRightResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
        } else if (isMouseInBottomResizeArea() && isMouseInLeftResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
        } else if (isMouseInBottomResizeArea() && isMouseInRightResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
        } else if (isMouseInTopResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
        } else if (isMouseInBottomResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
        } else if (isMouseInLeftResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
        } else if (isMouseInRightResizeArea()) {
            return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
        }
        return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

}
