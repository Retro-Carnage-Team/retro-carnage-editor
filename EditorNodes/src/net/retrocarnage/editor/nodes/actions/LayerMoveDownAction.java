package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.nodes.icons.IconProvider;

/**
 * Moves the Layer to the background.
 *
 * This is done by moving it one step to the beginning of the list of the layer's visual assets.
 *
 * @author Thomas Werner
 */
public class LayerMoveDownAction extends AbstractAction {

    private static final Image ICON = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.DOWN_ICON));

    private final Layer layer;
    private final LayerController layerCtrl;

    public LayerMoveDownAction(final Layer layer, final LayerController layerCtrl) {
        super("Move down", new ImageIcon(ICON));
        this.layer = layer;
        this.layerCtrl = layerCtrl;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layerCtrl.moveDown(layer);
    }

}
