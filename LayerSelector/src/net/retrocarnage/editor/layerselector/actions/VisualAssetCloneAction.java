package net.retrocarnage.editor.layerselector.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;

/**
 * Clones the VisualAsset.
 *
 * @author Thomas Werner
 */
public class VisualAssetCloneAction extends AbstractAction {

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetCloneAction(final Layer layer, final VisualAsset visualAsset) {
        this.layer = layer;
        this.visualAsset = visualAsset;
        putValue(NAME, "Clone");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layer.getVisualAssets().add(visualAsset.clone());
    }

}
