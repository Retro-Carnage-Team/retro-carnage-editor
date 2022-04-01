package net.retrocarnage.editor.nodes.nodes;

import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * A Layer contains two kinds of elements. This class builds these groups as children of the LayerNode.
 *
 * @author Thomas Werner
 */
public class LayerGroupsChildren extends Children.Keys<String> {

    private static final String KEY = "key";
    private final Layer layer;

    public LayerGroupsChildren(final Layer layer) {
        this.layer = layer;
    }

    @Override
    protected void addNotify() {
        setKeys(new String[]{KEY});
    }

    @Override
    protected Node[] createNodes(final String key) {
        return new Node[]{new VisualAssetsNode(layer), new ObstaclesNode(layer), new EnemiesNode(layer)};
    }
}
