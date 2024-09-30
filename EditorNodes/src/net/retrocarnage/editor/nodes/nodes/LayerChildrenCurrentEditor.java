package net.retrocarnage.editor.nodes.nodes;

import java.util.Collection;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Provides Nodes for all Layers of the currently selected GamePlayEditor.
 *
 * @author Thomas Werner
 */
public final class LayerChildrenCurrentEditor extends LayerChildren {

    private final Lookup.Result<LayerController> lookupResult;
    private LayerController controller;

    public LayerChildrenCurrentEditor() {
        final LookupListener lookupListener = (final LookupEvent le) -> handleLookupResultChanged();
        lookupResult = GamePlayEditorProxy.getDefault().getLookup().lookupResult(LayerController.class);
        lookupResult.addLookupListener(lookupListener);
    }

    private void handleLookupResultChanged() {
        if (null != controller) {
            controller.removePropertyChangeListener(this);
        }

        final Collection<? extends LayerController> items = lookupResult.allInstances();
        controller = items.isEmpty() ? null : items.iterator().next();
        if (null != controller) {
            controller.addPropertyChangeListener(this);
        }

        addNotify();
    }

    @Override
    protected LayerController getController() {
        return controller;
    }

}
