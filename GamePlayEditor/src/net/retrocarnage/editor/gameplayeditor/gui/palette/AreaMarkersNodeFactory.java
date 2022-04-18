package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates Nodes for each type of area markers.
 *
 * @author Thomas Werner
 */
public class AreaMarkersNodeFactory extends ChildFactory<String> {

    private static final String GOAL_KEY = "goal";
    private static final String OBSTACLE_KEY = "obstacle";

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        toPopulate.add(GOAL_KEY);
        toPopulate.add(OBSTACLE_KEY);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        if (GOAL_KEY.equals(key)) {
            return new Node[]{new GoalNode()};
        }
        if (OBSTACLE_KEY.equals(key)) {
            return new Node[]{new ObstacleNode()};
        }
        return new Node[]{};
    }

}
