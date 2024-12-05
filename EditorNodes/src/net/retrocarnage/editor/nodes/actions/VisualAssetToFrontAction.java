package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.nodes.icons.IconProvider;

/**
 * Moves the VisualAsset to the foreground.
 *
 * This is done by moving it one step to the beginning of the list of the layer's visual assets.
 *
 * @author Thomas Werner
 */
public class VisualAssetToFrontAction extends AbstractAction {

    private static final Image ICON = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.UP_ICON));

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetToFrontAction(final Layer layer, final VisualAsset visualAsset) {
        super("Move up", new ImageIcon(ICON));
        this.layer = layer;
        this.visualAsset = visualAsset;
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
