package net.retrocarnage.editor.gameplayeditor.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Position;
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

    /**
     * To be called when a property of the selected element has been updated.
     */
    @Override
    public void selectionModified() {
        fireChange();
    }

    @Override
    public void moveSelectedElement(final int deltaX, final int deltaY) {
        if (null == selection) {
            logger.log(Level.WARNING, "There's no selection to be moved...");
            return;
        }

        final Position oldPosition = selection.getPosition();
        selection.setPosition(new Position(
                oldPosition.getX() + deltaX,
                oldPosition.getY() + deltaY,
                oldPosition.getWidth(),
                oldPosition.getHeight()
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

        final Position oldPosition = selection.getPosition();
        selection.setPosition(new Position(
                oldPosition.getX() + positionDeltaX,
                oldPosition.getY() + positionDeltaY,
                oldPosition.getWidth() + sizeDeltaX,
                oldPosition.getHeight() + sizeDeltaY
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
