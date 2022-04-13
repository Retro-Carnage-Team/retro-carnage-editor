package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import org.openide.nodes.AbstractNode;

/**
 * Node used to group the Layers of a Mission.
 *
 * @author Thomas Werner
 */
public class LayersNode extends AbstractNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/layers.png";
    private static final Image ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_PATH));

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
