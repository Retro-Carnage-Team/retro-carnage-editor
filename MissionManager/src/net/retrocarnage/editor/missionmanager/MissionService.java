package net.retrocarnage.editor.missionmanager;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import net.retrocarnage.editor.missionmanager.impl.MissionServiceImpl;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.gameplay.GamePlay;

/**
 * A service that manages the missions.
 *
 * @author Thomas Werner
 */
public abstract class MissionService {

    public static final String PROPERTY_MISSIONS = "missions";

    private static final MissionService missionServiceImpl = new MissionServiceImpl();

    public abstract void addPropertyChangeListener(final PropertyChangeListener listener);

    public abstract void removePropertyChangeListener(final PropertyChangeListener listener);

    public abstract Collection<Mission> getMissions();

    public abstract Mission getMission(final String id);

    public abstract void addMission(final Mission mission);

    public abstract void updateMission(final Mission mission);

    public abstract void removeMission(final String id);

    public abstract GamePlay loadGamePlay(final String missionId);

    public abstract void saveGamePlay(final GamePlay gameplay);

    /**
     * @return an instance of this service
     */
    public static MissionService getDefault() {
        return missionServiceImpl;
    }

}
