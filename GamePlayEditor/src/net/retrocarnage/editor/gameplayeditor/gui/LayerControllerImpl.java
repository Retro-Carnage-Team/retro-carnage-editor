package net.retrocarnage.editor.gameplayeditor.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * LayerController implementation that will be placed in the Lookup of the editor.
 *
 * @author Thomas Werner
 */
class LayerControllerImpl implements LayerController {

    private static final Logger logger = Logger.getLogger(LayerControllerImpl.class.getName());

    private final PropertyChangeSupport propertyChangeSupport;
    private final GamePlayEditorController controller;
    private Layer selectedLayer;

    public LayerControllerImpl(final GamePlayEditorController controller) {
        this.controller = controller;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public Layer getSelectedLayer() {
        if (null == selectedLayer) {
            selectedLayer = getDefaultLayer();
        }
        return selectedLayer;
    }

    @Override
    public void setSelectedLayer(final Layer layer) {
        if (null == layer) {
            selectedLayer = getDefaultLayer();
        } else {
            final Layer oldSelection = selectedLayer;
            selectedLayer = layer;
            fireLayerChange(oldSelection);
            fireLayerChange(layer);
        }
    }

    @Override
    public List<Layer> getLayers() {
        getDefaultLayer();                                                                                              // make sure that there's a default layer
        return Collections.unmodifiableList(controller.getGamePlay().getLayers());
    }

    @Override
    public void toggleVisibility(final Layer layer) {
        layer.setVisible(!layer.isVisible());
        controller.requestGamePlayRepaint();
        fireLayerChange(layer);
    }

    @Override
    public void toggleLock(final Layer layer) {
        layer.setLocked(!layer.isLocked());
        fireLayerChange(layer);
    }

    @Override
    public void addLayer(final Layer layer) {
        controller.getGamePlay().getLayers().add(layer);
        if (!layer.getVisualAssets().isEmpty()) {
            controller.requestGamePlayRepaint();
        }
        fireLayerChange();
    }

    @Override
    public void removeLayer(final Layer layer) {
        if (Layer.DEFAULT_LAYER_NAME.equals(layer.getName())) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Default layer cannot be deleted"));
        } else {
            controller.getGamePlay().getLayers().remove(layer);
            controller.requestGamePlayRepaint();
            fireLayerChange();
        }
    }

    @Override
    public void renameLayer(final Layer layer, final String name) {
        if (Layer.DEFAULT_LAYER_NAME.equals(layer.getName())) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Default layer cannot be renamed"));
        } else if (Layer.DEFAULT_LAYER_NAME.equals(name)) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("That name is not valid, sorry."));
        } else {
            layer.setName(name);
            fireLayerChange(layer);
        }
    }

    @Override
    public void moveUp(final Layer layer) {
        final int idxOfLayer = controller.getGamePlay().getLayers().indexOf(layer);
        if (0 < idxOfLayer && controller.getGamePlay().getLayers().size() > 1) {
            final Layer tmp = controller.getGamePlay().getLayers().get(idxOfLayer - 1);
            controller.getGamePlay().getLayers().set(idxOfLayer - 1, layer);
            controller.getGamePlay().getLayers().set(idxOfLayer, tmp);
            controller.requestGamePlayRepaint();
            fireLayerChange();
        }
    }

    @Override
    public void moveDown(final Layer layer) {
        final int idxOfLayer = controller.getGamePlay().getLayers().indexOf(layer);
        if (idxOfLayer < controller.getGamePlay().getLayers().size() - 1 && controller.getGamePlay().getLayers().size() > 1) {
            final Layer tmp = controller.getGamePlay().getLayers().get(idxOfLayer + 1);
            controller.getGamePlay().getLayers().set(idxOfLayer + 1, layer);
            controller.getGamePlay().getLayers().set(idxOfLayer, tmp);
            controller.requestGamePlayRepaint();
            fireLayerChange();
        }
    }

    /**
     * @return the default layer
     */
    private Layer getDefaultLayer() {
        final Optional<Layer> possibleDefaultLayer = controller.getGamePlay().getLayers().stream()
                .filter((l) -> Layer.DEFAULT_LAYER_NAME.equals(l.getName()))
                .findAny();
        if (possibleDefaultLayer.isEmpty()) {
            final Layer defaultLayer = new Layer();
            defaultLayer.setName(Layer.DEFAULT_LAYER_NAME);
            defaultLayer.setVisible(true);
            controller.getGamePlay().getLayers().add(defaultLayer);
            return defaultLayer;
        }
        return possibleDefaultLayer.get();
    }

    /**
     * Fires a change that indicates that the set of Layers changed.
     */
    private void fireLayerChange() {
        try {
            propertyChangeSupport.firePropertyChange(PROPERTY_LAYERS, null, getLayers());
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Failed to inform listeners about Layer change", ex);
        }
    }

    /**
     * Fires a change that indicates that a specific Layer changed.
     *
     * @param specificLayer the Layer that changed
     */
    private void fireLayerChange(final Layer specificLayer) {
        try {
            propertyChangeSupport.firePropertyChange(PROPERTY_LAYER, null, specificLayer);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Failed to inform listeners about Layer change", ex);
        }
    }

}
