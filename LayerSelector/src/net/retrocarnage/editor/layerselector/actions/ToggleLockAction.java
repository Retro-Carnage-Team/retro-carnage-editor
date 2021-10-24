package net.retrocarnage.editor.layerselector.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.retrocarnage.editor.gameplayeditor.LayerController;
import net.retrocarnage.editor.model.Layer;

/**
 * Changes the lock status of a layer (to allow or disallow changes to the layer).
 *
 * @author Thomas Werner
 */
public class ToggleLockAction extends AbstractAction {

    private final Layer layer;
    private final LayerController layerCtrl;

    public ToggleLockAction(final Layer layer, final LayerController layerCtrl) {
        this.layer = layer;
        this.layerCtrl = layerCtrl;
        putValue(NAME, layer.isLocked() ? "Unlock" : "Lock");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layerCtrl.toggleLock(layer);
    }

}
