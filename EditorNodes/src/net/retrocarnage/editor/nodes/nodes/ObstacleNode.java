package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Obstacle;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for Obstacles in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public class ObstacleNode extends AbstractNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/obstacle.png";
    private static final Image ICON = IconUtil.loadIcon(LayerNode.class.getResourceAsStream(ICON_PATH));

    private final Obstacle obstacle;
    private final String name;

    public ObstacleNode(final Obstacle obstacle) {
        super(Children.LEAF);
        this.obstacle = obstacle;
        this.name = "Obstacle";

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

    public Obstacle getObstacle() {
        return obstacle;
    }

    @Override
    public Action[] getActions(final boolean popup) {
        return new Action[]{};
    }

}
