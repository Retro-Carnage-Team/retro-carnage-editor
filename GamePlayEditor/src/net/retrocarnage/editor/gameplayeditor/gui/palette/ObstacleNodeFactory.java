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

    // private static final String NONE_STOPPER = "none stopper";
    private static final String BULLET_STOPPER = "bullet stopper";
    // private static final String FULL_STOPPER = "full stopper";

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        toPopulate.add(BULLET_STOPPER);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        switch (key) {
            case BULLET_STOPPER:
                return new Node[]{new ObstacleNode()};
        }
        return new Node[]{};

    }

}
