package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates GroupNodes for each distinct tag assigned to all known sprite assets.
 *
 * @author Thomas Werner
 */
public class GroupNodeFactory extends ChildFactory<String> {

    private static final String OBSTACLE_GROUP = "obstacle group key";
    private static final String BACKGROUND_GROUP = "background group key";

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        final List<String> sortedEntries = new ArrayList<>();
        sortedEntries.add(BACKGROUND_GROUP);
        sortedEntries.add(OBSTACLE_GROUP);
        toPopulate.addAll(sortedEntries);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        if (OBSTACLE_GROUP.equals(key)) {
            return new Node[]{new ObstacleGroupNode()};
        }
        if (BACKGROUND_GROUP.equals(key)) {
            return new Node[]{new BackgroundGroupNode()};
        }
        return new Node[]{};
    }

}
