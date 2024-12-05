package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.nodes.icons.IconPathProvider;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Removes the Layer from the gameplay.
 *
 * @author Thomas Werner
 */
public class LayerRemoveAction extends AbstractAction {
    
    private static final Image ICON = IconUtil.loadIcon(
            LayerRemoveAction.class.getResourceAsStream(IconPathProvider.DELETE_ICON_PATH)
    );

    private final Layer layer;
    private final LayerController layerCtrl;

    public LayerRemoveAction(final Layer layer, final LayerController layerCtrl) {
        super("Delete", new ImageIcon(ICON));
        this.layer = layer;
        this.layerCtrl = layerCtrl;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final String question = String.format(
                "Do you really want to remove %s with %d sprites and %d obstacles?",
                layer.getName(),
                layer.getVisualAssets().size(),
                layer.getObstacles().size()
        );

        final NotifyDescriptor nd = new NotifyDescriptor.Confirmation(question, NotifyDescriptor.YES_NO_OPTION);
        final Object result = DialogDisplayer.getDefault().notify(nd);
        if (result == NotifyDescriptor.YES_OPTION) {
            layerCtrl.removeLayer(layer);
        }
    }

}
