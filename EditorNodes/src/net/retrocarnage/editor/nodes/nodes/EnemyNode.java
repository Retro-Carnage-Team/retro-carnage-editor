package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.nodes.icons.IconProvider;
import net.retrocarnage.editor.nodes.impl.EnemyPropsFactory;
import net.retrocarnage.editor.nodes.impl.SelectablePropsFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;

/**
 * A Node for Enemy in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public final class EnemyNode extends AbstractNode implements SelectableNode {
    
    private static final Image ICON = IconUtil.loadIcon(IconProvider.getIcon(IconProvider.IconPath.ENEMY_ICON));

    public EnemyNode(final Enemy enemy) {
        super(Children.LEAF, Lookups.singleton(enemy));
        enemy.addPropertyChangeListener(new PositionPropertyChangeAdapter(this::firePropertyChange));
        setDisplayName("Enemy");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>Enemy</b>";
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    public Enemy getEnemy() {
        return getLookup().lookup(Enemy.class);
    }

    @Override
    public Action[] getActions(final boolean popup) {
        return new Action[]{};
    }

    @Override
    protected Sheet createSheet() {
        final Layer layer = ((LayerNode) getParentNode().getParentNode()).getLayer();
        final Enemy enemy = getEnemy();

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
