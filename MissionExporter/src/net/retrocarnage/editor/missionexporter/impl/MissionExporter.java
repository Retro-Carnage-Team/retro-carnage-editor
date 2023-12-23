package net.retrocarnage.editor.missionexporter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.missionexporter.model.ExportMission;
import net.retrocarnage.editor.model.Mission;

/**
 * Exports the mission as JSON file.
 *
 * @author Thomas Werner
 */
public class MissionExporter {

    private static final Logger logger = Logger.getLogger(MissionExporter.class.getName());

    private final ExportFolderStructure exportFolderStructure;
    private final Mission mission;

    /**
     * Creates a new instance of MissionExporter.
     *
     * @param mission the Mission to be exported
     * @param exportFolderStructure definition of the export folder structure
     */
    public MissionExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
    }

    /**
     * Exports the mission as JSON file to the specified folder structure.
     */
    public void export() {
        final File missionFile = exportFolderStructure.getMissionFile();
        final ExportMission exportMission = new ExportMission(mission, exportFolderStructure);
        final ObjectMapper mapper = new ObjectMapper();
        try (final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(missionFile))) {
            mapper.writeValue(bos, exportMission);
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }

}
