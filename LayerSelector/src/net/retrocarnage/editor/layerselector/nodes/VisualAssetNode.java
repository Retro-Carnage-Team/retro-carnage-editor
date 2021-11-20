package net.retrocarnage.editor.layerselector.nodes;

import java.awt.Image;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.core.IconUtil;
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
    private static final Logger logger = Logger.getLogger(VisualAssetNode.class.getName());

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

}
