package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;

/**
 * Moves the VisualAsset to the background.
 *
 * This is done by moving it one step to the end of the list of the layer's visual assets.
 *
 * @author Thomas Werner
 */
public class VisualAssetToBackAction extends AbstractAction {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/down.png";
    private static final Image ICON = IconUtil.loadIcon(VisualAssetToBackAction.class.getResourceAsStream(ICON_PATH));

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetToBackAction(final Layer layer, final VisualAsset visualAsset) {
        super("Move down", new ImageIcon(ICON));
        this.layer = layer;
        this.visualAsset = visualAsset;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final int currentIndex = layer.getVisualAssets().indexOf(visualAsset);
        if (currentIndex < layer.getVisualAssets().size() - 1) {
            final VisualAsset other = layer.getVisualAssets().get(currentIndex + 1);
            layer.getVisualAssets().set(currentIndex + 1, visualAsset);
            layer.getVisualAssets().set(currentIndex, other);
        }
    }

}
