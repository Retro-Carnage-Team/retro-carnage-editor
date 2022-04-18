package net.retrocarnage.editor.nodes.nodes;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

/**
 * Creates VisualAssetNode for each VisualAsset contained in the specified Layer.
 *
 * @author Thomas Werner
 */
public class VisualAssetChildren extends Children.Keys<VisualAsset> {

    private final Layer layer;

    public VisualAssetChildren(final Layer layer) {
        this.layer = layer;
        final ChangeListener listener = (ChangeEvent ce) -> addNotify();
        layer.getVisualAssets().addChangeListener(WeakListeners.change(listener, layer.getVisualAssets()));
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
