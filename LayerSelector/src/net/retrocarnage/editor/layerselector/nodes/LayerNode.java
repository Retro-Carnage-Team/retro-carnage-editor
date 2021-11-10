package net.retrocarnage.editor.layerselector.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.LayerController;
import net.retrocarnage.editor.layerselector.actions.RenameAction;
import net.retrocarnage.editor.layerselector.actions.ToggleLockAction;
import net.retrocarnage.editor.layerselector.actions.ToggleVisibilityAction;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 * Node for a Layer.
 *
 * @author Thomas Werner
 */
public class LayerNode extends AbstractNode {

    private static final String ICON_LOCKED_PATH = "/net/retrocarnage/editor/layerselector/icons/locked.png";
    private static final String ICON_UNLOCKED_PATH = "/net/retrocarnage/editor/layerselector/icons/unlocked.png";
    private static final Image ICON_LOCKED = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_LOCKED_PATH));
    private static final Image ICON_UNLOCKED = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_UNLOCKED_PATH));

    public LayerNode(final Layer layer) {
        super(new VisualAssetChildren(layer), Lookups.singleton(layer));
        setDisplayName(getLabel(layer));
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
    public String getHtmlDisplayName() {
        return getLabel(getLayer());
    }

    @Override
    public Action[] getActions(boolean popup) {
        final LayerController layerCtrl = GamePlayEditorProxy.getDefault().getLookup().lookup(LayerController.class);
        final Layer layer = getLayer();
        // TODO: Add actions to move layer up / down
        return new Action[]{
            new ToggleLockAction(layer, layerCtrl),
            new ToggleVisibilityAction(layer, layerCtrl),
            new RenameAction(layer, layerCtrl)
        };
    }

    private String getLabel(final Layer layer) {
        final LayerController layerCtrl = GamePlayEditorProxy.getDefault().getLookup().lookup(LayerController.class);
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
