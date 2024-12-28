package net.retrocarnage.editor.missionexporter.impl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import net.retrocarnage.editor.model.Mission;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * This SwingWorker is used as bridge between the asynchronous export and the GUI.
 *
 * @author Thomas Werner
 */
public class ExportWorker extends SwingWorker<IOException, Integer> {

    private static final Logger logger = Logger.getLogger(ExportWorker.class.getName());

    private final ExportFolderStructure exportFolderStructure;
    private Mission mission;

    public ExportWorker(final Mission mission, final File exportFolder) {
        exportFolderStructure = new ExportFolderStructure(exportFolder, mission);
        if (null != mission) {
            this.mission = new Mission(mission);
        }
    }

    @Override
    protected IOException doInBackground() throws Exception {
        if (null == mission) {
            logger.log(Level.WARNING, "Mission does not exist. Skipping export process.");
            return null;
        }

        logger.log(
                Level.INFO,
                "Exporting mission ''{0}'' to {1}",
                new Object[]{mission.getName(), exportFolderStructure.getRootFolder().getAbsolutePath()}
        );

        try {
            exportFolderStructure.prepareFolderStructure();

            BackgroundExporter.build(mission, exportFolderStructure).export();
            new MissionExporter(mission, exportFolderStructure).export();
            new MusicExporter(mission, exportFolderStructure).export();
            new ClientImageExporter(mission, exportFolderStructure).export();
            new AttributionExporter(mission, exportFolderStructure).export();    
            
            return null;
        } catch(IOException ex) {
            return ex;
        }
    }
    
    @Override
    protected void done() {
        try {
            final IOException iox = get();
            if(null != iox) {
                final String msg = String.format(
                        "Failed to export mission '%s': %s", 
                        mission.getName(), 
                        iox.getMessage()
                );
                logger.log(Level.WARNING, msg, iox);
                DialogDisplayer
                        .getDefault()
                        .notify(new NotifyDescriptor.Message(msg, NotifyDescriptor.WARNING_MESSAGE));
            }
        } catch (InterruptedException ie) {
            logger.log(Level.WARNING, "Interrupted!", ie);
            Thread.currentThread().interrupt();
        } catch(ExecutionException ignore) {
            logger.log(Level.WARNING, "Thread execution failed!", ignore);
            // this can be ignored
        }
    }

}
