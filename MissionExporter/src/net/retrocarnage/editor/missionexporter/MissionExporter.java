package net.retrocarnage.editor.missionexporter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import net.retrocarnage.editor.missionexporter.impl.ExportWorker;
import net.retrocarnage.editor.model.Mission;
import org.netbeans.api.progress.*;
import org.openide.windows.WindowManager;

/**
 * Exports missions to a user specified location.
 *
 * @author Thomas Werner
 */
public class MissionExporter {

    private static File lastExportFolder;

    public void exportMission(final Mission mission) {
        final File exportFolder = selectExportFolder();
        if (null != exportFolder) {
            lastExportFolder = exportFolder;
            runExport(mission, exportFolder);
        }
    }

    private File selectExportFolder() {
        final JFileChooser fileChooser = (null == lastExportFolder)
                ? new JFileChooser()
                : new JFileChooser(lastExportFolder);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        final int dialogResult = fileChooser.showOpenDialog(WindowManager.getDefault().getMainWindow());
        return (dialogResult == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile() : null;
    }

    private void runExport(final Mission mission, final File exportFolder) {
        final ProgressHandle handle = ProgressHandleFactory.createHandle("Mission export");
        handle.start();

        final ExportWorker worker = new ExportWorker(mission, exportFolder);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            private boolean infinite = true;

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                    handle.finish();
                } else if ("progress".equals(evt.getPropertyName())) {
                    if (infinite) {
                        infinite = false;
                        handle.switchToDeterminate(100);
                    }
                    handle.progress((Integer) evt.getNewValue());
                }
            }
        });
        worker.execute();
    }

}
