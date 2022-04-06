package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.nodes.impl.EnemyPropsFactory;
import net.retrocarnage.editor.nodes.impl.SelectablePropsFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;

/**
 * A Node for Enemy in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public class EnemyNode extends AbstractNode implements SelectableNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/enemy.png";
    private static final Image ICON = IconUtil.loadIcon(EnemyNode.class.getResourceAsStream(ICON_PATH));

    private final Enemy enemy;
    private final String name;

    public EnemyNode(final Enemy enemy) {
        super(Children.LEAF);
        this.enemy = enemy;
        this.name = "Enemy";

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

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public Action[] getActions(final boolean popup) {
        return new Action[]{};
    }

    @Override
    protected Sheet createSheet() {
        final Layer layer = ((LayerNode) getParentNode().getParentNode()).getLayer();

        final Sheet sheet = Sheet.createDefault();
        sheet.put(SelectablePropsFactory.buildPositionSheet(enemy, layer.isLocked()));
        sheet.put(EnemyPropsFactory.buildEnemySheet(enemy, layer.isLocked()));
        return sheet;
    }

    @Override
    public Selectable getSelectable() {
        return getEnemy();
    }

}
