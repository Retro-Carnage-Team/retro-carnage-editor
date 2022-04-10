package net.retrocarnage.editor.missionexporter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.missionexporter.model.ExportMission;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Sprite;

/**
 * Exports the mission file.
 *
 * @author Thomas Werner
 */
public class MissionExporter {

    private static final Logger logger = Logger.getLogger(MissionExporter.class.getName());

    private final ExportFolderStructure exportFolderStructure;
    private final GamePlay gamePlay;
    private final Mission mission;

    public MissionExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.exportFolderStructure = exportFolderStructure;
        this.gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
        this.mission = mission;
    }

    public void run() {
        exportClientImage();
        exportMissionFile();
    }

    private void exportMissionFile() {
        final File missionFile = exportFolderStructure.getMissionFile();
        final ExportMission exportMission = new ExportMission(mission, exportFolderStructure);
        final ObjectMapper mapper = new ObjectMapper();
        try (final FileOutputStream fos = new FileOutputStream(missionFile); final BufferedOutputStream bos = new BufferedOutputStream(fos);) {
            mapper.writeValue(bos, exportMission);
        } catch (IOException ex) {
            Logger.getLogger(ExportWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportClientImage() {
        final Path imageFile = exportFolderStructure.getClientImageFile().toPath();
        final Sprite sprite = AssetService.getDefault().getSprite(mission.getClient());
        try {
            Files.deleteIfExists(imageFile);
            sprite.getData(Files.newOutputStream(imageFile));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to export image of mission's client", ex);
        }
    }

}
