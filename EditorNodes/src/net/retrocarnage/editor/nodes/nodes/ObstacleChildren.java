package net.retrocarnage.editor.nodes.nodes;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Obstacle;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

/**
 * Creates ObstacleNode for each Obstacle contained in the specified Layer.
 *
 * @author Thomas Werner
 */
public class ObstacleChildren extends Children.Keys<Obstacle> {

    private final Layer layer;
    private final ChangeListener listener;

    public ObstacleChildren(final Layer layer) {
        this.layer = layer;
        listener = (ChangeEvent ce) -> addNotify();
        layer.getObstacles().addChangeListener(WeakListeners.change(listener, layer.getObstacles()));
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
