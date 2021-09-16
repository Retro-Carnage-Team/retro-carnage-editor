package net.retrocarnage.editor.missionmanager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.ApplicationFolderService;
import net.retrocarnage.editor.missionmanager.MissionService;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static final String MISSION_DATABASE_FILENAME = "missionDatabase.xml";
    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        final MissionServiceImpl missionService = ((MissionServiceImpl) MissionService.getDefault());
        missionService.initializeFolderStructure();

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path databaseFile = appFolderService.buildDatabaseFilePath(MISSION_DATABASE_FILENAME);
        if (databaseFile.toFile().exists()) {
            try (final InputStream database = Files.newInputStream(databaseFile, StandardOpenOption.READ)) {
                missionService.loadMissions(database);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read the mission database file", ex.getMessage());
            }
        }

    }

    @Override
    public void close() {
        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path databaseFile = appFolderService.buildDatabaseFilePath(MISSION_DATABASE_FILENAME);
        try (final OutputStream database = Files.newOutputStream(databaseFile)) {
            final MissionServiceImpl missionService = ((MissionServiceImpl) MissionService.getDefault());
            missionService.saveMissions(database);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to write the mission database file", ex.getMessage());
        }
    }

}
