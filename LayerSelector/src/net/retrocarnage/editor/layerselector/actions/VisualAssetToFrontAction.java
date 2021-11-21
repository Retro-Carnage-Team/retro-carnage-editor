package net.retrocarnage.editor.layerselector.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;

/**
 * Moves the VisualAsset to the foreground.
 *
 * This is done by moving it one step to the beginning of the list of the layer's visual assets.
 *
 * @author Thomas Werner
 */
public class VisualAssetToFrontAction extends AbstractAction {

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetToFrontAction(final Layer layer, final VisualAsset visualAsset) {
        this.layer = layer;
        this.visualAsset = visualAsset;
        putValue(NAME, "Move up");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final int currentIndex = layer.getVisualAssets().indexOf(visualAsset);
        if (0 < currentIndex) {
            final VisualAsset other = layer.getVisualAssets().get(currentIndex - 1);
            layer.getVisualAssets().set(currentIndex - 1, visualAsset);
            layer.getVisualAssets().set(currentIndex, other);
        }
    }

}
