package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 * Node used to group the VisualAssets of a Layer.
 *
 * @author Thomas Werner
 */
public class VisualAssetsNode extends AbstractNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/visualAssets.png";
    private static final Image ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_PATH));

    public VisualAssetsNode(final Layer layer) {
        super(new VisualAssetChildren(layer), Lookups.singleton(layer));
        setDisplayName("Visual assets");
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return ICON;
    }

}
