package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;

/**
 * Removes the VisualAsset from the mission.
 *
 * @author Thomas Werner
 */
public class VisualAssetRemoveAction extends AbstractAction {

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetRemoveAction(final Layer layer, final VisualAsset visualAsset) {
        this.layer = layer;
        this.visualAsset = visualAsset;
        putValue(NAME, "Delete");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layer.getVisualAssets().remove(visualAsset);
    }

}
