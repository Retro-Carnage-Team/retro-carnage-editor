package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates ObstacleNodes for each type of Obstacle.
 *
 * @author Thomas Werner
 */
public class ObstacleNodeFactory extends ChildFactory<String> {

    private static final String OBSTACLE_KEY = "bullet stopper";

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        toPopulate.add(OBSTACLE_KEY);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        if (OBSTACLE_KEY.equals(key)) {
            return new Node[]{new ObstacleNode()};
        }
        return new Node[]{};
    }

}
