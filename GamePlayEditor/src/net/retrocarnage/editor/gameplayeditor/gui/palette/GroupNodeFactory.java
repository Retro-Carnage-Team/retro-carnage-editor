package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.retrocarnage.editor.assetmanager.AssetService;
import static net.retrocarnage.editor.assetmanager.AssetService.TAG_CLIENT;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates GroupNodes for each distinct tag assigned to all known sprite assets.
 *
 * @author Thomas Werner
 */
public class GroupNodeFactory extends ChildFactory<String> {

    private static final String OBSTACLE_GROUP = "obstacle group key";

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        final List<String> sortedEntries = new ArrayList<>(AssetService.getDefault().getSpriteTags());
        sortedEntries.remove(TAG_CLIENT);
        sortedEntries.add(OBSTACLE_GROUP);
        Collections.sort(sortedEntries);
        toPopulate.addAll(sortedEntries);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        if (OBSTACLE_GROUP.equals(key)) {
            return new Node[]{new ObstacleGroupNode()};
        }
        return new Node[]{new SpriteGroupNode(key)};
    }

}
