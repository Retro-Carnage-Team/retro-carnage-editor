package net.retrocarnage.editor.missionexporter.impl.mock;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;

/**
 * Used in unit tests.
 *
 * @author Thomas Werner
 */
public class MissionServiceMock implements MissionService {

    private static final String NOT_SUPPORTED = "Not supported yet.";
    
    private final GamePlay gamePlay;
    private final Mission mission;

    public MissionServiceMock(final Mission mission, final GamePlay gamePlay) {
        this.mission = mission;
        this.gamePlay = gamePlay;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public Collection<Mission> getMissions() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public Mission getMission(final String id) {
        return mission;
    }

    @Override
    public void addMission(Mission mission) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void updateMission(Mission mission) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void removeMission(String id) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public GamePlay loadGamePlay(final String missionId) {
        return gamePlay;
    }

    @Override
    public void saveGamePlay(GamePlay gameplay) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void initializeFolderStructure() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void loadMissions(InputStream in) throws IOException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void saveMissions(OutputStream out) throws IOException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

}
