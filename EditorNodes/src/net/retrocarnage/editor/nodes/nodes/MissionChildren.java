package net.retrocarnage.editor.nodes.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.missionmanager.MissionServiceFactory;
import net.retrocarnage.editor.model.Mission;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.*;

/**
 * Provides child nodes for all missions.
 *
 * @author Thomas Werner
 */
public final class MissionChildren extends Children.Keys<Mission> {

    public MissionChildren() {
        final PropertyChangeListener listener = (PropertyChangeEvent pce) -> addNotify();
        MissionServiceFactory
                .INSTANCE
                .buildMissionService()
                .addPropertyChangeListener(
                        WeakListeners.propertyChange(
                                listener, 
                                MissionServiceFactory
                                        .INSTANCE
                                        .buildMissionService()
                        )
                );
    }

    @Override
    protected void addNotify() {
        setKeys(MissionServiceFactory
                .INSTANCE
                .buildMissionService()
                .getMissions());
    }

    @Override
    protected Node[] createNodes(final Mission key) {
        final MissionNode missionNode = new MissionNode(key);
        return new Node[]{missionNode};
    }

}
