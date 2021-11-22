package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;

/**
 * Changes the visibility of a layer.
 *
 * @author Thomas Werner
 */
public class LayerToggleVisibilityAction extends AbstractAction {

    private final Layer layer;
    private final LayerController layerCtrl;

    public LayerToggleVisibilityAction(final Layer layer, final LayerController layerCtrl) {
        this.layer = layer;
        this.layerCtrl = layerCtrl;
        putValue(NAME, layer.isVisible() ? "Hide" : "Show");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layerCtrl.toggleVisibility(layer);
    }

}
