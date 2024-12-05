package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.nodes.icons.IconProvider;
import org.openide.nodes.AbstractNode;

/**
 * Node used to group the Layers of a Mission.
 *
 * @author Thomas Werner
 */
public final class LayersNode extends AbstractNode {
    
    private static final Image ICON = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.LAYERS_ICON));

    public LayersNode(final LayerController controller) {
        super(new LayerChildrenController(controller));
        setDisplayName("Layers");
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
