package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates GroupNodes for kind of palette object.
 *
 * @author Thomas Werner
 */
public class GroupNodeFactory extends ChildFactory<String> {

    private static final String BACKGROUND_GROUP = "background group key";
    private static final String ENEMY_GROUP = "enemygroup key";
    private static final String AREA_MARKERS_GROUP = "area markers group key";

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        toPopulate.add(BACKGROUND_GROUP);
        toPopulate.add(ENEMY_GROUP);
        toPopulate.add(AREA_MARKERS_GROUP);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        if (BACKGROUND_GROUP.equals(key)) {
            return new Node[]{new BackgroundGroupNode()};
        }
        if (ENEMY_GROUP.equals(key)) {
            return new Node[]{new EnemyGroupNode()};
        }
        if (AREA_MARKERS_GROUP.equals(key)) {
            return new Node[]{new AreaMarkersGroupNode()};
        }
        return new Node[]{};
    }

}
