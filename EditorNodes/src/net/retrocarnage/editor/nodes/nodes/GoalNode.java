package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Goal;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.nodes.actions.GoalRemoveAction;
import net.retrocarnage.editor.nodes.impl.SelectablePropsFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;

/**
 * Node for a Goal.
 *
 * @author Thomas Werner
 */
public class GoalNode extends AbstractNode implements SelectableNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/goal.png";
    private static final Image ICON = IconUtil.loadIcon(GoalNode.class.getResourceAsStream(ICON_PATH));

    public GoalNode(final Goal goal) {
        super(Children.LEAF, Lookups.singleton(goal));
        setDisplayName("Goal");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>Goal</b>";
    }

    public Goal getGoal() {
        return (Goal) getLookup().lookup(Goal.class);
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    @Override
    public Action[] getActions(boolean popup) {
        final Layer layer = ((LayerNode) getParentNode()).getLayer();
        return new Action[]{
            new GoalRemoveAction(layer)
        };
    }

    @Override
    public Selectable getSelectable() {
        return getGoal();
    }

    @Override
    protected Sheet createSheet() {
        final Layer layer = ((LayerNode) getParentNode()).getLayer();

        final Sheet sheet = Sheet.createDefault();
        sheet.put(SelectablePropsFactory.buildFullSheet(getGoal(), layer.isLocked()));
        return sheet;
    }

}
