package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;

/**
 * Changes the lock status of a layer (to allow or disallow changes to the layer).
 *
 * @author Thomas Werner
 */
public final class LayerToggleLockAction extends AbstractAction {

    private final Layer layer;
    private final LayerController layerCtrl;

    public LayerToggleLockAction(final Layer layer, final LayerController layerCtrl) {
        this.layer = layer;
        this.layerCtrl = layerCtrl;
        putValue(NAME, layer.isLocked() ? "Unlock" : "Lock");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layerCtrl.toggleLock(layer);
    }

}
