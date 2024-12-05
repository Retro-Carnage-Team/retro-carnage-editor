package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.nodes.actions.*;
import net.retrocarnage.editor.nodes.icons.IconProvider;
import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 * Node for a Layer.
 *
 * @author Thomas Werner
 */
public final class LayerNode extends AbstractNode {
    
    private static final Image ICON_LOCKED = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.LAYER_LOCKED_ICON));
    private static final Image ICON_UNLOCKED = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.LAYER_UNLOCKED_ICON));

    public LayerNode(final Layer layer) {
        super(new LayerGroupsChildren(layer), Lookups.singleton(layer));        
    }

    public Layer getLayer() {
        return getLookup().lookup(Layer.class);
    }

    @Override
    public Image getIcon(final int type) {
        return getLayer().isLocked() ? ICON_LOCKED : ICON_UNLOCKED;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        return getLayer().getName();
    }
    
    @Override
    public String getHtmlDisplayName() {
        return getLabel(getLayer());
    }

    @Override
    public Action[] getActions(boolean popup) {
        final LayerController layerCtrl = GamePlayEditorProxyFactory
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookup(LayerController.class);
        final Layer layer = getLayer();
        return new Action[]{
            new LayerMoveUpAction(layer, layerCtrl),
            new LayerMoveDownAction(layer, layerCtrl),
            new LayerSelectAction(layer, layerCtrl),
            new LayerToggleLockAction(layer, layerCtrl),
            new LayerToggleVisibilityAction(layer, layerCtrl),
            new LayerRenameAction(layer, layerCtrl),
            new LayerRemoveAction(layer, layerCtrl)
        };
    }

    private String getLabel(final Layer layer) {
        final LayerController layerCtrl = GamePlayEditorProxyFactory
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookup(LayerController.class);
        final boolean isSelected = (null != layerCtrl) && (layerCtrl.getSelectedLayer() == layer);
        final StringBuilder result = new StringBuilder();
        if (!layer.isVisible()) {
            result.append("<s>");
        }
        if (isSelected) {
            result.append("<b>");
        }
        result.append(layer.getName());
        if (isSelected) {
            result.append("</b>");
        }
        if (!layer.isVisible()) {
            result.append("</s>");
        }
        return result.toString();
    }

}
