package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.nodes.icons.IconProvider;

/**
 * Clones the VisualAsset.
 *
 * @author Thomas Werner
 */
public class VisualAssetCloneAction extends AbstractAction {
    
    private static final Image ICON = IconUtil.loadIcon(IconProvider.COPY_ICON.getResourcePath());

    private final Layer layer;
    private final VisualAsset visualAsset;

    public VisualAssetCloneAction(final Layer layer, final VisualAsset visualAsset) {
        super("Clone", new ImageIcon(ICON));
        this.layer = layer;
        this.visualAsset = visualAsset;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final VisualAsset clone = visualAsset.clone();
        layer.getVisualAssets().add(0, clone);
        GamePlayEditorProxyFactory
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookup(SelectionController.class)
                .setSelection(clone);
    }

}
