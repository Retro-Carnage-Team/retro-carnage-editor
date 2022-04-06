package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Rotation;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.nodes.actions.VisualAssetCloneAction;
import net.retrocarnage.editor.nodes.actions.VisualAssetRemoveAction;
import net.retrocarnage.editor.nodes.actions.VisualAssetToBackAction;
import net.retrocarnage.editor.nodes.actions.VisualAssetToFrontAction;
import net.retrocarnage.editor.nodes.impl.BlockerPropsFactory;
import net.retrocarnage.editor.nodes.impl.SelectablePropsFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;

/**
 * A Node for VisualAssets in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public class VisualAssetNode extends AbstractNode implements SelectableNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/visualAsset.png";
    private static final String TILE_ICON_PATH = "/net/retrocarnage/editor/nodes/icons/tile.png";
    private static final Image ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_PATH));
    private static final Image TILE_ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(TILE_ICON_PATH));

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
        final Layer layer = ((LayerNode) getParentNode().getParentNode()).getLayer();

        final Sheet sheet = Sheet.createDefault();
        sheet.put(buildVisualAssetSheet(layer.isLocked()));
        sheet.put(SelectablePropsFactory.buildFullSheet(visualAsset, layer.isLocked()));
        sheet.put(BlockerPropsFactory.buildBlockerSheet(visualAsset, layer.isLocked()));
        return sheet;
    }

    public Sheet.Set buildVisualAssetSheet(final boolean readonly) {
        final Sheet.Set positionSet = Sheet.createPropertiesSet();
        positionSet.setDisplayName("Properties");
        positionSet.setName("Properties");

        final Property rotation = new Node.Property<Rotation>(Rotation.class) {

            @Override
            public Rotation getValue() {
                return visualAsset.getRotation();
            }

            @Override
            public void setValue(final Rotation t) {
                if (!readonly) {
                    visualAsset.setRotation(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
        rotation.setName("Rotation");
        positionSet.put(rotation);

        return positionSet;
    }

    @Override
    public Selectable getSelectable() {
        return getVisualAsset();
    }

}
