package net.retrocarnage.editor.missionmanager.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.ApplicationFolderService;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.Mission;

/**
 * Implementation of the MissionService.
 *
 * @author Thomas Werner
 */
public class MissionServiceImpl extends MissionService {

    private static final String MISSION_FOLDER_NAME = "missions";
    private static final Logger logger = Logger.getLogger(MissionServiceImpl.class.getName());

    private final PropertyChangeSupport propertyChangeSupport;
    private final MissionDatabase missions;
    private final Path missionFolder;

    public MissionServiceImpl() {
        missions = new MissionDatabase();
        propertyChangeSupport = new PropertyChangeSupport(this);

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        missionFolder = Paths.get(appFolderPath.toString(), MISSION_FOLDER_NAME);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    void loadMissions(final InputStream in) throws IOException {
        missions.load(in);
    }

    void saveMissions(final OutputStream out) throws IOException {
        missions.save(out);
    }

    void initializeFolderStructure() {
        if (!missionFolder.toFile().exists() && !missionFolder.toFile().mkdir()) {
            logger.log(Level.WARNING, "Failed to create folder for missions: {0}", missionFolder.toString());
        }
    }

    @Override
    public Collection<Mission> getMissions() {
        final List<Mission> tempMissions = new ArrayList<>();
        tempMissions.addAll(missions.getMissions());
        Collections.sort(tempMissions, (m1, m2) -> m1.getName().compareTo(m2.getName()));
        return Collections.unmodifiableCollection(tempMissions);
    }

    @Override
    public Mission getMission(final String id) {
        return missions.getMission(id);
    }

    @Override
    public void addMission(final Mission msn) {
        missions.putMission(msn);
        propertyChangeSupport.firePropertyChange(PROPERTY_MISSIONS, null, getMissions());
    }

    @Override
    public void updateMission(final Mission changedMission) {
        final Mission original = getMission(changedMission.getId());
        original.applyPartialChangesOfMetaData(changedMission);
        propertyChangeSupport.firePropertyChange(PROPERTY_MISSIONS, null, getMissions());
    }

    @Override
    public void removeMission(final String id) {
        missions.remove(id);
        propertyChangeSupport.firePropertyChange(PROPERTY_MISSIONS, null, getMissions());
    }

}
