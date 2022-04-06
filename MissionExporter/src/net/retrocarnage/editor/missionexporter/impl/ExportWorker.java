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

    private final Mission mission;
    private final File exportFolder;

    public ExportWorker(final Mission mission, final File exportFolder) {
        this.mission = mission;
        this.exportFolder = exportFolder;
    }

    @Override
    protected Void doInBackground() throws Exception {
        logger.log(
                Level.INFO,
                "Exporting mission ''{0}'' to {1}",
                new Object[]{mission.getName(), exportFolder.getAbsolutePath()}
        );
        return null;
    }

}
