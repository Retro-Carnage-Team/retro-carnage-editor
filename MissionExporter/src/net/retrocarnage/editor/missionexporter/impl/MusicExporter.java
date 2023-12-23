package net.retrocarnage.editor.missionexporter.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;

/**
 * Exports the music file.
 *
 * @author Thomas Werner
 */
public class MusicExporter {

    private static final Logger logger = Logger.getLogger(MusicExporter.class.getName());

    private final ExportFolderStructure exportFolderStructure;
    private final Mission mission;

    /**
     * Creates a new instance of MusicExporter.
     *
     * @param mission the Mission to export the music for
     * @param exportFolderStructure definition of the export folder structure
     */
    public MusicExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
    }

    /**
     * Copies the music asset to the export folder.
     */
    public void export() {
        final Path musicFile = exportFolderStructure.getMusicFile().toPath();
        final Music music = AssetService.getDefault().getMusic(mission.getSong());
        try {
            Files.deleteIfExists(musicFile);
            music.getData(Files.newOutputStream(musicFile));
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to export background music of mission", ex);
        }
    }

}
