package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * Creates EnemyNode for each Enemy contained in the specified Layer.
 *
 * @author Thomas Werner
 */
public final class EnemyChildren extends Children.Keys<Enemy> {
    
    private final Layer layer;

    public EnemyChildren(final Layer layer) {
        this.layer = layer;
        layer.getEnemies().addPropertyChangeListener((PropertyChangeEvent e) -> addNotify());
    }

    @Override
    protected void addNotify() {
        setKeys(layer.getEnemies());
    }

    @Override
    protected Node[] createNodes(final Enemy t) {
        return new Node[]{new EnemyNode(t)};
    }

}
