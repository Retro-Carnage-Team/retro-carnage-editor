package net.retrocarnage.editor.missionmanager.impl;

import java.util.ArrayList;
import java.util.List;
import net.retrocarnage.editor.model.Mission;

/**
 * POJO used to serialize / deserialize the mission database.
 *
 * @author Thomas Werner
 */
class MissionDatabaseFile {

    private List<Mission> missions;

    public MissionDatabaseFile() {
        missions = new ArrayList<>();
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(final List<Mission> missions) {
        this.missions = missions;
    }

}
