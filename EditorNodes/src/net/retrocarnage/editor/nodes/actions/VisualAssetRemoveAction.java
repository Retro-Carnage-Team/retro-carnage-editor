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
 * Removes the VisualAsset from the gameplay.
 *
 * @author Thomas Werner
 */
public class VisualAssetRemoveAction extends AbstractAction {

    private static final Image ICON = IconUtil.loadIcon(IconProvider.DELETE_ICON.getResourcePath());

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetRemoveAction(final Layer layer, final VisualAsset visualAsset) {
        super("Delete", new ImageIcon(ICON));
        this.layer = layer;
        this.visualAsset = visualAsset;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layer.getVisualAssets().remove(visualAsset);
    }

}
