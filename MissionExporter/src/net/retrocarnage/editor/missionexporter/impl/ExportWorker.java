package net.retrocarnage.editor.missionexporter.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.renderer.SectionPathRunner;
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
        final GamePlay gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
        final SectionAnalysis mapStructure = new SectionAnalyzer().analyzeMapStructure(gamePlay.getSections());
        new SectionPathRunnerImpl(mapStructure, gamePlay.getSections(), exportFolder, gamePlay, mission).run();
    }

    private void exportMissionFile() {
        // TODO: Export the mission as JSON
    }

    private void exportAttributionFile() {
        // TODO: Export the attributions as MD
    }

    private static class SectionPathRunnerImpl extends SectionPathRunner {

        private final File exportFolder;
        private final GamePlay gamePlay;
        private final Mission mission;
        private int sectionNumber;

        public SectionPathRunnerImpl(
                final SectionAnalysis mapAnalysis,
                final List<Section> sections,
                final File exportFolder,
                final GamePlay gamePlay,
                final Mission mission) {
            super(mapAnalysis, sections, 1_500);
            this.exportFolder = exportFolder;
            this.gamePlay = gamePlay;
            this.mission = mission;

            final File missionBackgroundFolder = getMissionBackgroundFolder();
            if (missionBackgroundFolder.exists()) {
                deleteMissionBackgrounds();
            } else if (!missionBackgroundFolder.mkdirs()) {
                logger.log(
                        Level.WARNING,
                        "Failed to create folder for mission backgrounds: {0}",
                        missionBackgroundFolder.getAbsolutePath()
                );
            }
        }

        @Override
        protected void processSectionRect(final Section section, final int x, final int y, final int w, final int h) {
            final ExportRenderer renderer = new ExportRenderer(gamePlay);
            final BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2d = image.createGraphics();
            g2d.translate(-x, -y);
            renderer.render(g2d);
            g2d.dispose();

            final String fileName = String.format("%s/%d.png",
                    getMissionBackgroundFolder().getAbsolutePath(),
                    sectionNumber++
            );
            try {
                ImageIO.write(image, "PNG", new File(fileName));
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to write section background", ex);
            }
        }

        private File getMissionBackgroundFolder() {
            final String missionBackgroundFolderPath = String.format(
                    "%s/images/levels/%s",
                    exportFolder.getAbsolutePath(),
                    mission.getName()
            );
            return new File(missionBackgroundFolderPath);
        }

        private void deleteMissionBackgrounds() {
            final File missionBackgroundFolder = getMissionBackgroundFolder();
            if (missionBackgroundFolder.exists()) {
                try {
                    Files.list(missionBackgroundFolder.toPath()).forEach((p) -> {
                        try {
                            Files.delete(p);
                        } catch (IOException ex) {
                            logger.log(
                                    Level.WARNING,
                                    String.format("Failed to delete existing mission background (%s)", p.toString()),
                                    ex
                            );
                        }
                    });
                } catch (IOException ex) {
                    logger.log(Level.WARNING,
                            String.format(
                                    "Failed to list backgrounds of mission folder (%s)",
                                    missionBackgroundFolder.toString()
                            ),
                            ex
                    );
                }
            }
        }
    }

}
