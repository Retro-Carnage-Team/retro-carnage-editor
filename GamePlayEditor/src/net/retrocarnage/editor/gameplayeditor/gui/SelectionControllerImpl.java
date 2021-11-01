package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.gameplayeditor.SelectionController;
import net.retrocarnage.editor.model.Selectable;

/**
 * SelectionController implementation that will be placed in the Lookup of the editor.
 *
 * @author Thomas Werner
 */
public class SelectionControllerImpl implements SelectionController {

    private static final Logger logger = Logger.getLogger(SelectionControllerImpl.class.getName());

    private final PropertyChangeSupport propertyChangeSupport;
    private final GamePlayEditorController controller;
    private Selectable selection;

    SelectionControllerImpl(final GamePlayEditorController controller) {
        this.controller = controller;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public Selectable getSelection() {
        return selection;
    }

    @Override
    public void setSelection(final Selectable selection) {
        this.selection = selection;
        fireChange();
    }

    @Override
    public void moveSelectedElement(final int deltaX, final int deltaY) {
        if (null == selection) {
            logger.log(Level.WARNING, "There's no selection to be moved...");
            return;
        }

        final Rectangle oldPosition = selection.getPosition();
        selection.setPosition(new Rectangle(
                oldPosition.x + deltaX,
                oldPosition.y + deltaY,
                oldPosition.width,
                oldPosition.height
        ));
        controller.requestGamePlayRepaint();
    }

    @Override
    public void resizeSelectedElement(
            final int positionDeltaX,
            final int positionDeltaY,
            final int sizeDeltaX,
            final int sizeDeltaY) {
        if (null == selection) {
            logger.log(Level.WARNING, "There's no selection to be resized...");
            return;
        }

        final Rectangle oldPosition = selection.getPosition();
        selection.setPosition(new Rectangle(
                oldPosition.x + positionDeltaX,
                oldPosition.y + positionDeltaY,
                oldPosition.width + sizeDeltaX,
                oldPosition.height + sizeDeltaY
        ));
        controller.requestGamePlayRepaint();
    }

    /**
     * Fires a change that indicates that the selection changed.
     */
    private void fireChange() {
        try {
            propertyChangeSupport.firePropertyChange(PROPERTY_SELECTION, null, selection);
            controller.requestGamePlayRepaint();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Failed to inform listeners about selection change", ex);
        }
    }

}
