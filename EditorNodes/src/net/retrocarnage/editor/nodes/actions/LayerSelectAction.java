package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;

/**
 * Action that selects the specified layer.
 *
 * @author Thomas Werner
 */
public class LayerSelectAction extends AbstractAction {

    private final Layer layer;
    private final LayerController layerCtrl;

    public LayerSelectAction(final Layer layer, final LayerController layerCtrl) {
        this.layer = layer;
        this.layerCtrl = layerCtrl;
        putValue(NAME, "Select");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layerCtrl.setSelectedLayer(layer);
    }

}
