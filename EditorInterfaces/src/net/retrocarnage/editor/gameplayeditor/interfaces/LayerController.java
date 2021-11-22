package net.retrocarnage.editor.gameplayeditor.interfaces;

import java.beans.PropertyChangeListener;
import java.util.List;
import net.retrocarnage.editor.model.Layer;

/**
 * Instances of this class can be found in the Lookup of GamePlay editor windows. They provide access to the Layers of
 * the selected GamePlay.
 *
 * @author Thomas Werner
 */
public interface LayerController {

    public static final String PROPERTY_LAYERS = "layers";
    public static final String PROPERTY_LAYER = "layer";

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    Layer getSelectedLayer();

    void setSelectedLayer(Layer layer);

    List<Layer> getLayers();

    void toggleVisibility(Layer layer);

    void toggleLock(Layer layer);

    void addLayer(Layer layer);

    void removeLayer(Layer layer);

    void renameLayer(Layer layer, String name);

    void moveUp(Layer layer);

    void moveDown(Layer layer);

}
