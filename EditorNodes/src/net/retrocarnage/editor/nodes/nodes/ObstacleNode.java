package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Obstacle;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.nodes.impl.BlockerPropsFactory;
import net.retrocarnage.editor.nodes.impl.SelectablePropsFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;

/**
 * A Node for Obstacles in context of the LayerSelector.
 *
 * @author Thomas Werner
 */
public final class ObstacleNode extends AbstractNode implements SelectableNode {

    private static final String ICON_PATH = "/net/retrocarnage/editor/nodes/icons/obstacle.png";
    private static final Image ICON = IconUtil.loadIcon(ObstacleNode.class.getResourceAsStream(ICON_PATH));

    public ObstacleNode(final Obstacle obstacle) {
        super(Children.LEAF, Lookups.singleton(obstacle));
        obstacle.addPropertyChangeListener(new PositionPropertyChangeAdapter(this::firePropertyChange));
        setDisplayName("Obstacle");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>Obstacle</b>";
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    public Obstacle getObstacle() {
        return getLookup().lookup(Obstacle.class);
    }

    @Override
    public Action[] getActions(final boolean popup) {
        return new Action[]{};
    }

    @Override
    protected Sheet createSheet() {
        final Layer layer = ((LayerNode) getParentNode().getParentNode()).getLayer();

        final Sheet sheet = Sheet.createDefault();
        sheet.put(SelectablePropsFactory.buildFullSheet(getObstacle(), layer.isLocked()));
        sheet.put(BlockerPropsFactory.buildBlockerSheet(getObstacle(), layer.isLocked()));
        return sheet;
    }

    @Override
    public Selectable getSelectable() {
        return getObstacle();
    }

}
