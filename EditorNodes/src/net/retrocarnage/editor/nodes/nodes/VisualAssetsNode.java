package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.nodes.icons.IconProvider;
import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 * Node used to group the VisualAssets of a Layer.
 *
 * @author Thomas Werner
 */
public final class VisualAssetsNode extends AbstractNode {

    private static final Image ICON = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.VISUAL_ASSETS_ICON));

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
