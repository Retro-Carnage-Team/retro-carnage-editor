package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import java.util.logging.Logger;
import javax.swing.Action;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.nodes.actions.VisualAssetCloneAction;
import net.retrocarnage.editor.nodes.actions.VisualAssetRemoveAction;
import net.retrocarnage.editor.nodes.actions.VisualAssetToBackAction;
import net.retrocarnage.editor.nodes.actions.VisualAssetToFrontAction;
import net.retrocarnage.editor.nodes.impl.SelectablePropsFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;

/**
 * A Node for VisualAssets in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public class VisualAssetNode extends AbstractNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/visualAsset.png";
    private static final String TILE_ICON_PATH = "/net/retrocarnage/editor/nodes/icons/tile.png";
    private static final Image ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_PATH));
    private static final Image TILE_ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(TILE_ICON_PATH));
    private static final Logger logger = Logger.getLogger(VisualAssetNode.class.getName());

    private final VisualAsset visualAsset;
    private final String name;
    private final boolean tile;

    public VisualAssetNode(final VisualAsset visualAsset) {
        super(Children.LEAF);
        this.visualAsset = visualAsset;

        final Sprite sprite = AssetService.getDefault().getSprite(visualAsset.getAssetId());
        this.name = sprite.getName();
        this.tile = sprite.isTile();

        setDisplayName(name);
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + name + "</b>";
    }

    @Override
    public Image getIcon(final int type) {
        return tile ? TILE_ICON : ICON;
    }

    public VisualAsset getVisualAsset() {
        return visualAsset;
    }

    @Override
    public Action[] getActions(final boolean popup) {
        final Layer layer = ((LayerNode) getParentNode().getParentNode()).getLayer();
        return new Action[]{
            new VisualAssetToFrontAction(layer, visualAsset),
            new VisualAssetToBackAction(layer, visualAsset),
            new VisualAssetCloneAction(layer, visualAsset),
            new VisualAssetRemoveAction(layer, visualAsset)
        };
    }

    @Override
    protected Sheet createSheet() {
        final Sheet sheet = Sheet.createDefault();

        final Layer layer = ((LayerNode) getParentNode().getParentNode()).getLayer();

        // Position
        final Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("Position");

        final Property posXProp = SelectablePropsFactory.buildXProperty(visualAsset, layer.isLocked());
        posXProp.setName("X");
        set.put(posXProp);

        final Property posYProp = SelectablePropsFactory.buildYProperty(visualAsset, layer.isLocked());
        posYProp.setName("Y");
        set.put(posYProp);

        final Property posWidthProp = SelectablePropsFactory.buildWidthProperty(visualAsset, layer.isLocked());
        posWidthProp.setName("Width");
        set.put(posWidthProp);

        final Property posHeightProp = SelectablePropsFactory.buildHeightProperty(visualAsset, layer.isLocked());
        posHeightProp.setName("Height");
        set.put(posHeightProp);

        sheet.put(set);

        return sheet;
    }

}
