package net.retrocarnage.editor.gameplayeditor;

import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.model.Selectable;

/**
 * Manages the selection of the GamePlayEditor
 *
 * @author Thomas Werner
 */
public interface SelectionController {

    public static final String PROPERTY_SELECTION = "selection";
    public static final String PROPERTY_SELECTED_ELEMENT = "selected element";

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * @return the selected element
     */
    Selectable getSelection();

    /**
     * Changes the selected element.
     *
     * @param selection the new selection
     */
    void setSelection(Selectable selection);

    /**
     * Moves the selected element on screen.
     *
     * @param selectedElement the element to be moved
     * @param deltaX number of pixels to the right
     * @param deltaY number of pixels down
     */
    void moveSelectedElement(Selectable selectedElement, int deltaX, int deltaY);

    /**
     * Resizes the selected element on screen.
     *
     * @param selectedElement the element to be moved
     * @param positionDeltaX moves the selectedElement the given number of pixels to the right
     * @param positionDeltaY moves the selectedElement the given number of pixels down
     * @param sizeDeltaX increases the width of the selected element the given number number of pixels
     * @param sizeDeltaY increases the height of the selected element the given number number of pixels
     */
    void resizeSelectedElement(
            Selectable selectedElement,
            int positionDeltaX,
            int positionDeltaY,
            int sizeDeltaX,
            int sizeDeltaY
    );

}
