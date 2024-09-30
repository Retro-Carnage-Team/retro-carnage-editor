package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

/**
 * A Layer contains two kinds of elements. This class builds these groups as children of the LayerNode.
 *
 * @author Thomas Werner
 */
public final class LayerGroupsChildren extends Children.Keys<String> {

    private static final String KEY_ENEMIES = "enemies";
    private static final String KEY_GOAL = "goal";
    private static final String KEY_OBSTACLES = "obstacles";
    private static final String KEY_VISUAL_ASSETS = "visual assets";

    private final Layer layer;

    public LayerGroupsChildren(final Layer layer) {
        this.layer = layer;
        final PropertyChangeListener listener = (PropertyChangeEvent pce) -> addNotify();
        layer.addPropertyChangeListener(WeakListeners.propertyChange(listener, layer));
    }

    @Override
    protected void addNotify() {
        if (null == layer.getGoal()) {
            setKeys(new String[]{KEY_VISUAL_ASSETS, KEY_OBSTACLES, KEY_ENEMIES});
        } else {
            setKeys(new String[]{KEY_VISUAL_ASSETS, KEY_OBSTACLES, KEY_ENEMIES, KEY_GOAL});
        }
    }

    @Override
    protected Node[] createNodes(final String key) {
        if (KEY_VISUAL_ASSETS.equals(key)) {
            return new Node[]{new VisualAssetsNode(layer)};
        }
        if (KEY_OBSTACLES.equals(key)) {
            return new Node[]{new ObstaclesNode(layer)};
        }
        if (KEY_ENEMIES.equals(key)) {
            return new Node[]{new EnemiesNode(layer)};
        }
        if (KEY_GOAL.equals(key)) {
            return new Node[]{new GoalNode(layer.getGoal())};
        }
        return new Node[]{};
    }
}
