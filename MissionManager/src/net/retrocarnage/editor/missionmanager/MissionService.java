package net.retrocarnage.editor.missionmanager;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.GamePlay;

/**
 * A service that manages the missions.
 *
 * @author Thomas Werner
 */
public interface MissionService {

    static final String PROPERTY_MISSIONS = "missions";

    void addPropertyChangeListener(final PropertyChangeListener listener);

    void removePropertyChangeListener(final PropertyChangeListener listener);

    Collection<Mission> getMissions();

    Mission getMission(final String id);

    void addMission(final Mission mission);

    void updateMission(final Mission mission);

    void removeMission(final String id);

    GamePlay loadGamePlay(final String missionId);

    void saveGamePlay(final GamePlay gameplay);

    void initializeFolderStructure();
    
     void loadMissions(final InputStream in) throws IOException;
     
     void saveMissions(final OutputStream out) throws IOException;
    
}
