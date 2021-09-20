package net.retrocarnage.editor.missionmanager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
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

    private final MissionDatabase missions;
    private final Path missionFolder;

    public MissionServiceImpl() {
        missions = new MissionDatabase();

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        missionFolder = Paths.get(appFolderPath.toString(), MISSION_FOLDER_NAME);
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
        return Collections.unmodifiableCollection(missions.getMissions());
    }

    @Override
    public Mission getMission(final String id) {
        return missions.getMission(id);
    }

    @Override
    public void addMission(final Mission msn) {
        missions.putMission(msn);
    }

    @Override
    public void removeMission(final String id) {
        missions.remove(id);
    }

}
