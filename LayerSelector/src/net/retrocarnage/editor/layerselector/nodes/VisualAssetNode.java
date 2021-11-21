package net.retrocarnage.editor.layerselector.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.layerselector.actions.VisualAssetCloneAction;
import net.retrocarnage.editor.layerselector.actions.VisualAssetRemoveAction;
import net.retrocarnage.editor.layerselector.actions.VisualAssetToBackAction;
import net.retrocarnage.editor.layerselector.actions.VisualAssetToFrontAction;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.VisualAsset;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for VisualAssets in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public class VisualAssetNode extends AbstractNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/layerselector/icons/visualAsset.png";
    private static final Image ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_PATH));

    private final VisualAsset visualAsset;
    private final String name;

    public VisualAssetNode(final VisualAsset visualAsset) {
        super(Children.LEAF);
        this.visualAsset = visualAsset;
        this.name = AssetService.getDefault().getSprite(visualAsset.getAssetId()).getName();
        setDisplayName(name);
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + name + "</b>";
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    public VisualAsset getVisualAsset() {
        return visualAsset;
    }

    @Override
    public Action[] getActions(final boolean popup) {
        final Layer layer = ((LayerNode) getParentNode()).getLayer();
        return new Action[]{
            new VisualAssetToFrontAction(layer, visualAsset),
            new VisualAssetToBackAction(layer, visualAsset),
            new VisualAssetCloneAction(layer, visualAsset),
            new VisualAssetRemoveAction(layer, visualAsset)
        };
    }

}
