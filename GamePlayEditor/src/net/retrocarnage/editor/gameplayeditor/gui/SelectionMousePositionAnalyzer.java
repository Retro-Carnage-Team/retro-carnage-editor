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
public class SelectionMousePositionAnalyzer {

    static final int RESIZE_PRECISION = 5;

    private final boolean mouseInSelection;
    private final boolean mouseInTopResizeArea;
    private final boolean mouseInRightResizeArea;
    private final boolean mouseInBottomResizeArea;
    private final boolean mouseInLeftResizeArea;
    private final int offsetTop;
    private final int offsetLeft;
    private final int offsetBottom;
    private final int offsetRight;

    /**
     * Creates a new instance of SelectionMouseInteractionAnalyzer.
     *
     * @param selection selected element
     * @param mousePosition mouse position relative to the GamePlay
     */
    public SelectionMousePositionAnalyzer(final Selectable selection, final Point mousePosition) {
        float zoomFactor = (float) (ZoomService.getDefault().getZoomLevel() / 100.0);
        final Rectangle selectionRect = selection.getPosition().scale(zoomFactor).toRectangle();

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

            offsetTop = mousePosition.y - selectionRect.y;
            offsetLeft = mousePosition.x - selectionRect.x;
            offsetBottom = selectionRect.y + selectionRect.height - mousePosition.y;
            offsetRight = selectionRect.x + selectionRect.width - mousePosition.x;
        } else {
            mouseInTopResizeArea = false;
            mouseInRightResizeArea = false;
            mouseInBottomResizeArea = false;
            mouseInLeftResizeArea = false;

            offsetTop = -1;
            offsetLeft = -1;
            offsetBottom = -1;
            offsetRight = -1;
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

    public int getOffsetTop() {
        return offsetTop;
    }

    public int getOffsetLeft() {
        return offsetLeft;
    }

    public int getOffsetBottom() {
        return offsetBottom;
    }

    public int getOffsetRight() {
        return offsetRight;
    }

    public Cursor getCursor(boolean isResizable, boolean isMovable) {
        if (!isMouseInSelection()) {
            return Cursor.getDefaultCursor();
        }

        if (isResizable) {
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
        }

        if (isMovable) {
            return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        }

        return Cursor.getDefaultCursor();
    }
    
    public Operation getOperation() {
        Operation result;
        if (isMouseInTopResizeArea() && isMouseInLeftResizeArea()) {
            result = Operation.RESIZE_NW;
        } else if (isMouseInTopResizeArea() && isMouseInRightResizeArea()) {
            result = Operation.RESIZE_NE;
        } else if (isMouseInBottomResizeArea() && isMouseInLeftResizeArea()) {
            result = Operation.RESIZE_SW;
        } else if (isMouseInBottomResizeArea() && isMouseInRightResizeArea()) {
            result = Operation.RESIZE_SE;
        } else if (isMouseInTopResizeArea()) {
            result = Operation.RESIZE_N;
        } else if (isMouseInLeftResizeArea()) {
            result = Operation.RESIZE_W;
        } else if (isMouseInRightResizeArea()) {
            result = Operation.RESIZE_E;
        } else if (isMouseInBottomResizeArea()) {
            result = Operation.RESIZE_S;
        } else {
            result = Operation.MOVE;
        }
        return result;
    }

}
