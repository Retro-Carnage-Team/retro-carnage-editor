package net.retrocarnage.editor.missionexporter.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.model.Mission;

/**
 * Creates the Attribution.md document for an exported mission. This file contains attributions for all assets used in
 * the mission.
 *
 * @author Thomas Werner
 */
public class AttributionExporter {

    private static final Logger logger = Logger.getLogger(AttributionExporter.class.getName());

    private final ExportFolderStructure exportFolderStructure;
    private final Mission mission;

    public AttributionExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
    }

    public void run() {
        // TODO: Read templates/attribution-template.md into buffer

        final File mdFile = exportFolderStructure.getMissionAttributionFile();
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(mdFile, Charset.forName("utf-8")))) {
            // Iterate over lines of attribution-template
            // Replace placeholders with actual values
            // write result to writer
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }
}
