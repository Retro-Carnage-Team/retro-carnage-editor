package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;

/**
 * Removes the VisualAsset from the gameplay.
 *
 * @author Thomas Werner
 */
public class VisualAssetRemoveAction extends AbstractAction {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/delete.png";
    private static final Image ICON = IconUtil.loadIcon(VisualAssetRemoveAction.class.getResourceAsStream(ICON_PATH));

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
