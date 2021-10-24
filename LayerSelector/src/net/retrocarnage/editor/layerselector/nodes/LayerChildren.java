package net.retrocarnage.editor.layerselector.nodes;

import java.util.Collection;
import java.util.Collections;
import net.retrocarnage.editor.gameplayeditor.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.LayerController;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Provides child nodes for all Layers of a GamePlay.
 *
 * @author Thomas Werner
 */
public class LayerChildren extends Children.Keys {

    private final LookupListener lookupListener;
    private final Lookup.Result<LayerController> lookupResult;
    private LayerController controller;

    public LayerChildren() {
        lookupListener = (final LookupEvent le) -> handleLookupResultChanged();
        lookupResult = GamePlayEditorProxy.getDefault().getLookup().lookupResult(LayerController.class);
        lookupResult.addLookupListener(lookupListener);
    }

    @Override
    protected void addNotify() {
        if (null == controller) {
            setKeys(Collections.EMPTY_LIST);
        } else {
            setKeys(controller.getLayers());
        }
    }

    @Override
    protected Node[] createNodes(final Object key) {
        return new Node[]{new LayerNode((Layer) key)};
    }

    private void handleLookupResultChanged() {
        final Collection<? extends LayerController> items = lookupResult.allInstances();
        controller = items.isEmpty() ? null : items.iterator().next();
        // TODO: Add listener to controller. Use events to update list of Layer nodes
        addNotify();
    }

}
