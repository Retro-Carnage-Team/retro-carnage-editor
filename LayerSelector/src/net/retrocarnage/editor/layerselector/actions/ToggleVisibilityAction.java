package net.retrocarnage.editor.layerselector.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.gameplayeditor.LayerController;
import net.retrocarnage.editor.model.Layer;

/**
 * Changes the visibility of a layer.
 *
 * @author Thomas Werner
 */
public class ToggleVisibilityAction extends AbstractAction {

    private final Layer layer;
    private final LayerController layerCtrl;

    public ToggleVisibilityAction(final Layer layer, final LayerController layerCtrl) {
        this.layer = layer;
        this.layerCtrl = layerCtrl;
        putValue(NAME, layer.isVisible() ? "Hide" : "Show");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layerCtrl.toggleVisibility(layer);
    }

}
