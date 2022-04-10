package net.retrocarnage.editor.missionexporter.impl;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import net.retrocarnage.editor.model.Mission;

/**
 * This SwingWorker is used as bridge between the asynchronous export and the GUI.
 *
 * @author Thomas Werner
 */
public class ExportWorker extends SwingWorker<Void, Integer> {

    private static final Logger logger = Logger.getLogger(ExportWorker.class.getName());

    private final File exportFolder;
    private Mission mission;

    public ExportWorker(final Mission mission, final File exportFolder) {
        this.exportFolder = exportFolder;
        try {
            if (null != mission) {
                this.mission = mission.clone();
            }
        } catch (CloneNotSupportedException ex) {
            logger.log(Level.SEVERE, "Failed to create working copy of mission", ex);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        if (null == mission) {
            logger.log(Level.WARNING, "Mission does not exist. Skipping export process.");
            return null;
        }

        logger.log(
                Level.INFO,
                "Exporting mission ''{0}'' to {1}",
                new Object[]{mission.getName(), exportFolder.getAbsolutePath()}
        );

        exportMissionBackgrounds();
        exportMissionFile();
        exportAttributionFile();

        return null;
    }

    private void exportMissionBackgrounds() {
        BackgroundExporter.build(mission, exportFolder).run();
    }

    private void exportMissionFile() {
        // TODO: Export the mission as JSON
    }

    private void exportAttributionFile() {
        // TODO: Export the attributions as MD
    }

}
