package net.retrocarnage.editor.missionmanager.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.model.SectionDirection;

/**
 * Implementation of the MissionService.
 *
 * @author Thomas Werner
 */
public final class MissionServiceImpl implements MissionService {

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

    @Override
    public void loadMissions(final InputStream in) throws IOException {
        missions.load(in);
    }

    @Override
    public void saveMissions(final OutputStream out) throws IOException {
        missions.save(out);
    }

    @Override
    public void initializeFolderStructure() {
        if (!missionFolder.toFile().exists() && !missionFolder.toFile().mkdir()) {
            logger.log(Level.WARNING, "Failed to create folder for missions: {0}", missionFolder);
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

    @Override
    public GamePlay loadGamePlay(final String missionId) throws IOException {
        final File gamePlayFile = missionFolder.resolve(missionId + ".xml").toFile();
        if (gamePlayFile.exists()) {
            try (final InputStream in = new BufferedInputStream(new FileInputStream(gamePlayFile))) {
                return new XmlMapper().readValue(in, GamePlay.class);
            }
        }

        return initializeGamePlay(missionId);
    }

    /**
     * Initializes a GamePlay object with default values.
     *
     * @param missionId ID of the mission that the new GamePlay belongs to
     * @return the initialized GamePlay object
     */
    private GamePlay initializeGamePlay(final String missionId) {
        final GamePlay result = new GamePlay(missionId);

        final Section firstSection = new Section();
        firstSection.setDirection(SectionDirection.UP);
        firstSection.setNumberOfScreens(1);
        result.getSections().add(firstSection);

        final Layer defaultLayer = new Layer();
        defaultLayer.setName(Layer.DEFAULT_LAYER_NAME);
        defaultLayer.setVisible(true);
        result.getLayers().add(defaultLayer);

        return result;
    }

    @Override
    public void saveGamePlay(final GamePlay gameplay) throws IOException {
        final File gamePlayFile = missionFolder.resolve(gameplay.getMissionId() + ".xml").toFile();
        try (final OutputStream out = new BufferedOutputStream(new FileOutputStream(gamePlayFile))) {
            new XmlMapper().writeValue(out, gameplay);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to store mission file", ex);
        }
    }

}
