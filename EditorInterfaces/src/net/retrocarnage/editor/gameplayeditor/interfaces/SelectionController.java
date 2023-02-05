package net.retrocarnage.editor.gameplayeditor.interfaces;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import net.retrocarnage.editor.model.Selectable;

/**
 * Manages the selection of the GamePlayEditor
 *
 * @author Thomas Werner
 */
public interface SelectionController {

    // not vetoable
    public static final String PROPERTY_POINT_SELECTED = "point-selected";
    
    // vetoable
    public static final String PROPERTY_SELECTION = "selection";

    /**
     * Adds a listener for non-vetoable events - like the selection of a point on the map.
     * @param listener The listener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes the listener for non-vetoable events.
     * @param listener The listener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener listener);
    
    /**
     * Adds a listener for vetoable events - like the selection of a Selectable.
     * @param listener The listener to be added
     */
    void addVetoableChangeListener(VetoableChangeListener listener);

    /**
     * Removes the listener for vetoable events.
     * @param listener The listener to be removed
     */
    void removeVetoableChangeListener(VetoableChangeListener listener);
    
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
     * To be called when a property of the selected element has been updated.
     */
    void selectionModified();

    /**
     * Moves the selected element on screen.
     *
     * @param deltaX number of pixels to the right
     * @param deltaY number of pixels down
     */
    void moveSelectedElement(int deltaX, int deltaY);

    /**
     * Resizes the selected element on screen.
     *
     * @param positionDeltaX moves the selectedElement the given number of pixels to the right
     * @param positionDeltaY moves the selectedElement the given number of pixels down
     * @param sizeDeltaX increases the width of the selected element the given number number of pixels
     * @param sizeDeltaY increases the height of the selected element the given number number of pixels
     */
    void resizeSelectedElement(
            int positionDeltaX,
            int positionDeltaY,
            int sizeDeltaX,
            int sizeDeltaY
    );

}
