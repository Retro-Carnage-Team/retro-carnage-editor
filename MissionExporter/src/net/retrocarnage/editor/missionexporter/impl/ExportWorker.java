package net.retrocarnage.editor.missionexporter.impl;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Segment;
import net.retrocarnage.editor.renderer.export.ExportRenderer;

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

        exportSegmentScreens();
        exportMissionFile();
        exportAttributionFile();

        return null;
    }

    private void exportSegmentScreens() {
        final BufferedImage missionImage = renderMission();
        for (Segment segment : mission.getSegments()) {
            // TODO: Export each screen as JPG
            // TODO: Update the backgrounds of the mission copy accordingly
        }
    }

    /**
     * Creates a BufferedImage with the full size of the mission area. Renders the mission onto that image and returns
     * it. This will use a huge amount of memory - depending on the size of the mission.
     *
     * @return
     */
    private BufferedImage renderMission() {
        final GamePlay gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
        final ExportRenderer renderer = new ExportRenderer(gamePlay);
        final Dimension imageSize = renderer.getSize();
        final BufferedImage image = new BufferedImage(
                imageSize.width,
                imageSize.height,
                BufferedImage.TYPE_INT_ARGB
        );

        final Graphics2D g2d = image.createGraphics();
        renderer.render(g2d);
        g2d.dispose();

        return image;
    }

    private void exportMissionFile() {
        // TODO: Export the mission as JSON
    }

    private void exportAttributionFile() {
        // TODO: Export the attributions as MD
    }

}
