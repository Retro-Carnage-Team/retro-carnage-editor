package net.retrocarnage.editor.missionmanager;

import net.retrocarnage.editor.missionmanager.impl.MissionServiceImpl;
import net.retrocarnage.editor.model.Mission;

/**
 * A service that manages the missions.
 *
 * @author Thomas Werner
 */
public abstract class MissionService {

    private static final MissionService missionServiceImpl = new MissionServiceImpl();

    public abstract Iterable<Mission> getMissions();

    public abstract Mission getMission(final String id);

    public abstract void addMission(final Mission mission);

    public abstract void removeMission(final String id);

    /**
     * @return an instance of this service
     */
    public static MissionService getDefault() {
        return missionServiceImpl;
    }

}
