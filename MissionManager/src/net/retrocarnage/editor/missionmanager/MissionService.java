package net.retrocarnage.editor.missionmanager;

import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.missionmanager.impl.MissionServiceImpl;
import net.retrocarnage.editor.model.Mission;

/**
 * A service that manages the missions.
 *
 * @author Thomas Werner
 */
public abstract class MissionService {

    public static final String PROPERTY_SELECTED_MISSION = "selectedMission";

    private static final MissionService missionServiceImpl = new MissionServiceImpl();

    public abstract void addPropertyChangeListener(final PropertyChangeListener listener);

    public abstract void removePropertyChangeListener(final PropertyChangeListener listener);

    public abstract Iterable<Mission> getMissions();

    public abstract Mission getMission(final String id);

    public abstract void addMission(final Mission mission);

    public abstract void removeMission(final String id);

    public abstract Mission getSelectedMission();

    public abstract void setSelectedMission(Mission mission);

    /**
     * @return an instance of this service
     */
    public static MissionService getDefault() {
        return missionServiceImpl;
    }

}
