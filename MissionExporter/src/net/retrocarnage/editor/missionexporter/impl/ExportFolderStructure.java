package net.retrocarnage.editor.missionexporter.impl;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.assetmanager.AssetServiceFactory;
import net.retrocarnage.editor.core.io.FileNameChecker;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;
import org.apache.commons.io.FilenameUtils;

/**
 * Holds information about the target file structure of a mission export.
 *
 * @author Thomas Werner
 */
public class ExportFolderStructure {

    private static final Logger logger = Logger.getLogger(ExportFolderStructure.class.getName());

    private final AssetService assetService;
    private final File exportFolder;
    private final Mission mission;

    /**
     * Creates a new instance of ExportFolderStructure.
     *
     * @param exportFolder the base folder for the export
     * @param mission the mission to be exported
     */
    public ExportFolderStructure(final File exportFolder, final Mission mission) {
        this.assetService = AssetServiceFactory.buildAssetService();
        this.exportFolder = exportFolder;
        this.mission = mission;
    }
    
    /**
     * Creates a new instance of ExportFolderStructure for usage in tests. Offers injection of AssetService instance.
     *
     * @param exportFolder the base folder for the export
     * @param mission the mission to be exported
     * @param assetService the AssetService to be used
     */
    public ExportFolderStructure(final File exportFolder, final Mission mission, final AssetService assetService) {
        this.assetService = assetService;
        this.exportFolder = exportFolder;
        this.mission = mission;
    }

    /**
     * @return a File object for the root folder of the export
     */
    public File getRootFolder() {
        return exportFolder;
    }

    /**
     * @return a File object for the JSON file containing the missing data
     */
    public File getMissionFile() {
        final File missionFolder = getMissionsFolder();
        return new File(missionFolder, FileNameChecker.buildSaveVersion(mission.getName() + ".json"));
    }

    /**
     * @return a File object for the attributions file containing license information about assets used in the mission
     */
    public File getMissionAttributionFile() {
        return new File(exportFolder, FileNameChecker.buildSaveVersion(mission.getName() + "-attributions.md"));
    }

    /**
     * Creates the full folder structure for the export
     */
    public void prepareFolderStructure() {
        createFolder(getClientsFolder(), "folder for mission clients");                                                 // images/clients/
        createFolder(getMissionBackgroundFolder(), "folder for mission backgrounds");                                   // images/levels/
        createFolder(getMissionsFolder(), "folder for missions");                                                       // missions
        createFolder(getMusicFolder(), "folder for music");                                                             // sounds/music
    }

    /**
     * @return a file for the folder to place background images for the gameplay in
     */
    public File getMissionBackgroundFolder() {
        final String missionBackgroundFolderPath = String.format(
                "%s/images/levels/%s",
                exportFolder.getAbsolutePath(),
                FileNameChecker.buildSaveVersion(mission.getName())
        );
        return new File(missionBackgroundFolderPath);
    }

    /**
     * @return a file for the /missions folder of the export folder structure
     */
    public File getMissionsFolder() {
        return new File(String.format("%s/missions", exportFolder.getAbsolutePath()));
    }

    /**
     * Returns the a file describing a background image for the specified gameplay section and screen number.
     *
     * @param sectionNumber number of the gameplay section
     * @param screenNumber number of the screen
     * @return the file
     */
    public File getBackgroundImageFile(final int sectionNumber, final int screenNumber) {
        final String fileName = String.format("%s/%d-%d.png",
                getMissionBackgroundFolder().getAbsolutePath(),
                sectionNumber,
                screenNumber
        );
        return new File(fileName);
    }

    /**
     * Returns the a file describing a background image for the specified gameplay section and screen number.
     *
     * @param sectionNumber number of the gameplay section
     * @param screenNumber number of the screen
     * @return the file
     */
    public String getBackgroundImageRelativePath(final int sectionNumber, final int screenNumber) {
        return String.format("images/levels/%s/%d-%d.png",
                FileNameChecker.buildSaveVersion(mission.getName()),
                sectionNumber,
                screenNumber
        );
    }

    /**
     * @return the relative path of the image of the missions client
     */
    public String getClientImageRelativePath() {
        return String.format("images/clients/%s.%s",
                FileNameChecker.buildSaveVersion(mission.getName()),
                "png"
        );
    }

    /**
     * @return a file for the image of the missions client
     */
    public File getClientImageFile() {
        final String fileName = String.format(
                "%s.%s",
                FileNameChecker.buildSaveVersion(mission.getName()),
                "png"
        );
        return new File(getClientsFolder(), fileName);
    }

    public File getClientsFolder() {
        return new File(String.format("%s/images/clients", getRootFolder().getAbsolutePath()));
    }

    /**
     * @return the relative path of the background music of the mission
     */
    public String getMusicRelativePath() {
        final Music music = assetService.getMusic(mission.getSong());
        return String.format("sounds/music/%s.mp3", FileNameChecker.buildSaveVersion(music.getName()));
    }

    /**
     * @return a file for the background music of the missions
     */
    public File getMusicFile() {
        final Music music = assetService.getMusic(mission.getSong());
        final String fileName = String.format("%s.mp3", FileNameChecker.buildSaveVersion(music.getName()));
        return new File(getMusicFolder(), fileName);
    }

    public File getMusicFolder() {
        return new File(String.format("%s/sounds/music", getRootFolder().getAbsolutePath()));
    }

    /**
     * Creates a specified folder and writes logs.
     *
     * @param folderToCreate the folder to be created
     * @param description some description for the folder (to be used on logs)
     */
    private void createFolder(final File folderToCreate, final String description) {
        if (!folderToCreate.exists()) {
            logger.log(
                    Level.FINE,
                    "Creating new {0} {1}",
                    new Object[]{description, folderToCreate}
            );
            if (!folderToCreate.mkdirs()) {
                logger.log(
                        Level.WARNING,
                        "Failed to create {0}: {1}",
                        new Object[]{description, folderToCreate.getAbsolutePath()}
                );
            }
        }
    }

}
