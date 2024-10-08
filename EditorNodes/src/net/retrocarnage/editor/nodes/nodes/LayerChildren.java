package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * Base class for various providers of LayerNodes.
 *
 * @author Thomas Werner
 */
public abstract class LayerChildren extends Children.Keys<Layer> implements PropertyChangeListener {

    protected LayerChildren() {
        // intentionally empty
    }

    @Override
    protected void addNotify() {
        if (null == getController()) {
            setKeys(Collections.emptyList());
        } else {
            setKeys(getController().getLayers());
        }
    }

    @Override
    protected Node[] createNodes(final Layer key) {
        return new Node[]{new LayerNode(key)};
    }

    @Override
    public void propertyChange(final PropertyChangeEvent pce) {
        if (LayerController.PROPERTY_LAYERS.equals(pce.getPropertyName())) {
            setKeys((List<Layer>) pce.getNewValue());
        } else if (LayerController.PROPERTY_LAYER.equals(pce.getPropertyName())) {
            refreshKey((Layer)pce.getNewValue());
        }
    }

    protected abstract LayerController getController();

}
