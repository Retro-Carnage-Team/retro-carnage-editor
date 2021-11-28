package net.retrocarnage.editor.gameplayeditor.gui.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for a group of Obstacle assets.
 *
 * @author Thomas Werner
 */
public class ObstacleGroupNode extends AbstractNode {

    public ObstacleGroupNode() {
        super(Children.create(new ObstacleNodeFactory(), false));
        setDisplayName("Obstacles");
    }
}
