package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.model.Position;
import static net.retrocarnage.editor.model.Selectable.PROPERTY_POSITION;
import static net.retrocarnage.editor.nodes.impl.SelectablePropsFactory.*;

/**
 * Acts as a PropertyChange adapter for AbstractNodes and their wrapped (Selectable) entities.
 *
 * Some Nodes, like the ObstacleNode, have properties like X, Y, Width and Height - while their wrapped entity has a
 * property of type Position. This PropertyChangeListener implementation can be registered at that wrapped entity to
 * listen for changes to the entity's Position. Once that changes it will analyze the change and fire new
 * PropertyChangeEvents for each of the Node's properties that needs to be updated.
 *
 * @author Thomas Werner
 */
class PositionPropertyChangeAdapter implements PropertyChangeListener {

    private final Target target;

    public PositionPropertyChangeAdapter(Target target) {
        this.target = target;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent pce) {
        if (PROPERTY_POSITION.equals(pce.getPropertyName())) {
            final Position o = (Position) pce.getOldValue();
            final Position n = (Position) pce.getNewValue();

            if (o.getX() != n.getX()) {
                target.firePropertyChange(PROPERTY_POSITION_X, o.getX(), n.getX());
            }
            if (o.getY() != n.getY()) {
                target.firePropertyChange(PROPERTY_POSITION_Y, o.getY(), n.getY());
            }
            if (o.getWidth() != n.getWidth()) {
                target.firePropertyChange(PROPERTY_POSITION_WIDTH, o.getWidth(), n.getWidth());
            }
            if (o.getHeight() != n.getHeight()) {
                target.firePropertyChange(PROPERTY_POSITION_HEIGHT, o.getHeight(), n.getHeight());
            }
        } else {
            target.firePropertyChange(pce.getPropertyName(), pce.getOldValue(), pce.getNewValue());
        }
    }

    /**
     * The firePropertyChange method of AbstractNode is protected. So we use this interface to pass a reference to that
     * method.
     */
    interface Target {

        void firePropertyChange(String name, Object o, Object n);

    }

}
