package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.retrocarnage.editor.model.Direction;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemySkin;
import net.retrocarnage.editor.model.EnemyType;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates Nodes for each EnemyType
 *
 * @author Thomas Werner
 */
public class EnemyNodeFactory extends ChildFactory<EnemyType> {

    @Override
    protected boolean createKeys(final List<EnemyType> toPopulate) {
        toPopulate.addAll(Arrays.asList(EnemyType.values()));
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final EnemyType key) {
        switch (key) {
            case Person:
                return new EnemyNode[]{new EnemyNode(buildPersonTemplate())};
            case Landmine:
                return new EnemyNode[]{new EnemyNode(buildLandmineTemplate())};
            default:
                return new EnemyNode[]{};
        }
    }

    private static Enemy buildPersonTemplate() {
        final Enemy result = new Enemy();
        result.setActions(Collections.emptyList());
        result.setDirection(Direction.Down.getValue());
        result.setMovements(Collections.emptyList());
        result.setSkin(EnemySkin.GreyJumperWithRifle.getName());
        result.setType(EnemyType.Person.getValue());
        return result;
    }

    private static Enemy buildLandmineTemplate() {
        final Enemy result = new Enemy();
        result.setActions(Collections.emptyList());
        result.setDirection(Direction.Down.getValue());
        result.setMovements(Collections.emptyList());
        result.setSkin("");
        result.setType(EnemyType.Landmine.getValue());
        return result;
    }

}
