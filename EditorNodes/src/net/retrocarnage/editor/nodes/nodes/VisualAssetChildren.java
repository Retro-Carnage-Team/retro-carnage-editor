package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * Creates VisualAssetNode for each VisualAsset contained in the specified Layer.
 *
 * @author Thomas Werner
 */
public final class VisualAssetChildren extends Children.Keys<VisualAsset> {

    private final Layer layer;

    public VisualAssetChildren(final Layer layer) {
        this.layer = layer;        
        layer.getVisualAssets().addPropertyChangeListener((PropertyChangeEvent ce) -> addNotify());
    }

    @Override
    protected void addNotify() {
        setKeys(layer.getVisualAssets());
    }

    @Override
    protected Node[] createNodes(final VisualAsset t) {
        return new Node[]{new VisualAssetNode(t)};
    }

}
