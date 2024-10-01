package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
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
    private final VetoableChangeSupport vetoableChangeSupport;    
    private final GamePlayEditorController controller;
    private Selectable selection;

    SelectionControllerImpl(final GamePlayEditorController controller) {
        this.controller = controller;
        propertyChangeSupport = new PropertyChangeSupport(this);
        vetoableChangeSupport = new VetoableChangeSupport(this);
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
    public void addVetoableChangeListener(final VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    @Override
    public void removeVetoableChangeListener(final VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    @Override
    public Selectable getSelection() {
        return selection;
    }

    @Override
    public void setSelection(final Selectable selection) {        
        processSelectionChanged(selection);
    }

    @Override
    public void invalidateSelection() {        
        this.selection = null;
        controller.requestGamePlayRepaint();
    }
    
    /**
     * To be called when a property of the selected element has been updated.
     */
    @Override
    public void selectionModified() {
        processSelectionChanged(this.selection);
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

    public void pointSelected(final Point point) {
        try {
            propertyChangeSupport.firePropertyChange(PROPERTY_POINT_SELECTED, null, point);
        } catch(Exception ex) {
            logger.log(Level.WARNING, "Failed to inform listeners about point clicked", ex);
        }
    }
    
    /**
     * Fires a change that indicates that the selection changed.
     */
    private void processSelectionChanged(final Selectable newSelection) {
        try {            
            vetoableChangeSupport.fireVetoableChange(PROPERTY_SELECTION, this.selection, newSelection);
            this.selection = newSelection;
            controller.requestGamePlayRepaint();
        } catch(PropertyVetoException pve) {
            try {
                vetoableChangeSupport.fireVetoableChange(PROPERTY_SELECTION, this.selection, this.selection);
            } catch (PropertyVetoException ex) { }
            logger.log(Level.FINE, "Selection listener vetoed against selection change", pve);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Failed to inform listeners about selection change", ex);
        }
    }

}
