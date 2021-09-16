package net.retrocarnage.editor.missionmanager.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final MissionDatabase missions;
    private final Path missionFolder;

    public MissionServiceImpl() {
        missions = new MissionDatabase();

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
    public Iterable<Mission> getMissions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mission getMission(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addMission(Mission msn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeMission(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mission getSelectedMission() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSelectedMission(Mission mission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
