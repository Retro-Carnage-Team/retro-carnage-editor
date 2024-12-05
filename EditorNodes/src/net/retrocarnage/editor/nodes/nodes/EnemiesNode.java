package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.nodes.icons.IconProvider;
import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 * Node used to group the Enemies of a Layer.
 *
 * @author Thomas Werner
 */
public final class EnemiesNode extends AbstractNode {
    
    private static final Image ICON = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.ENEMIES_ICON));

    public EnemiesNode(final Layer layer) {
        super(new EnemyChildren(layer), Lookups.singleton(layer));
        setDisplayName("Enemies");
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
