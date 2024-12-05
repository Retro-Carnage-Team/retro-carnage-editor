package net.retrocarnage.editor.nodes.nodes;

import java.awt.Image;
import javax.swing.Action;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.nodes.actions.MissionEditAction;
import net.retrocarnage.editor.nodes.actions.MissionExportAction;
import net.retrocarnage.editor.nodes.icons.IconPathProvider;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 * Node for a mission.
 *
 * @author Thomas Werner
 */
public class MissionNode extends AbstractNode {
    
    private static final Image ICON = IconUtil.loadIcon(
            MissionNode.class.getResourceAsStream(IconPathProvider.MISSION_ICON_PATH)
    );

    public MissionNode(final Mission mission) {
        super(Children.LEAF, Lookups.singleton(mission));
    }

    public Mission getMission() {
        return getLookup().lookup(Mission.class);
    }

    @Override
    public Image getIcon(final int type) {
        return ICON;
    }

    @Override
    public String getDisplayName() {
        return getMission().getName();
    }

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{
            new MissionEditAction(getMission()),
            new MissionExportAction(getMission())
        };
    }

}
