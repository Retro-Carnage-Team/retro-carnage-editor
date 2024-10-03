package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Obstacle;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * Creates ObstacleNode for each Obstacle contained in the specified Layer.
 *
 * @author Thomas Werner
 */
public final class ObstacleChildren extends Children.Keys<Obstacle> {

    private final Layer layer;

    public ObstacleChildren(final Layer layer) {
        this.layer = layer;        
        layer.getObstacles().addPropertyChangeListener((PropertyChangeEvent e) -> addNotify());
    }

    @Override
    protected void addNotify() {
        setKeys(layer.getObstacles());
    }

    @Override
    protected Node[] createNodes(final Obstacle t) {
        return new Node[]{new ObstacleNode(t)};
    }

}
