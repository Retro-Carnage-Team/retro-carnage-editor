package net.retrocarnage.editor.nodes;

import java.awt.Image;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.nodes.impl.IconUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 * Node for a mission.
 *
 * @author Thomas Werner
 */
public class MissionNode extends AbstractNode {

    private static final String ICON_PATH = "net/retrocarnage/editor/nodes/icons/mission.png";
    private static final Image ICON = IconUtil.loadIcon(ICON_PATH);

    public MissionNode(final Mission mission) {
        super(Children.LEAF, Lookups.singleton(mission));
    }

    public Mission getMission() {
        return (Mission) getLookup().lookup(Mission.class);
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    @Override
    public String getDisplayName() {
        return getMission().getName();
    }

}