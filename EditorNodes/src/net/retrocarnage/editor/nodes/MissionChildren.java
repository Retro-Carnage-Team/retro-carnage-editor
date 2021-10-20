package net.retrocarnage.editor.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.Mission;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.*;

/**
 * Provides child nodes for all missions.
 *
 * @author Thomas Werner
 */
public class MissionChildren extends Children.Keys {

    private final PropertyChangeListener listener;

    public MissionChildren() {
        listener = (PropertyChangeEvent pce) -> {
            addNotify();
        };
        MissionService
                .getDefault()
                .addPropertyChangeListener(WeakListeners.propertyChange(listener, MissionService.getDefault()));
    }

    @Override
    protected void addNotify() {
        setKeys(MissionService.getDefault().getMissions());
    }

    @Override
    protected Node[] createNodes(final Object key) {
        final Mission mission = (Mission) key;
        final MissionNode missionNode = new MissionNode(mission);
        return new Node[]{missionNode};
    }

}
