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
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.renderer.SectionPathRunner;
import net.retrocarnage.editor.renderer.export.ExportRenderer;

/**
 * This class can be used to export the backgrounds of a mission.
 *
 * @author Thomas Werner
 */
class BackgroundExporter extends SectionPathRunner {

    private static final Logger logger = Logger.getLogger(ExportWorker.class.getName());

    private final File exportFolder;
    private final GamePlay gamePlay;
    private final Mission mission;
    private int sectionNumber;

    /**
     * Builds a new BackgroundExporter.
     *
     * @param mission the mission to export backgrounds for
     * @param exportFolder the target folder (root folder of RETRO-CARNAGE installation)
     * @return a BackgroundExporter, configured and ready to use
     */
    public static BackgroundExporter build(final Mission mission, final File exportFolder) {
        final GamePlay gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
        final SectionAnalysis mapStructure = new SectionAnalyzer().analyzeMapStructure(gamePlay.getSections());
        return new BackgroundExporter(mapStructure, gamePlay.getSections(), exportFolder, gamePlay, mission);
    }

    private BackgroundExporter(
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
        } else {
            logger.log(
                    Level.FINE,
                    "Creating new folder for mission backgrounds {0}",
                    missionBackgroundFolder.toString()
            );
            if (!missionBackgroundFolder.mkdirs()) {
                logger.log(
                        Level.WARNING,
                        "Failed to create folder for mission backgrounds: {0}",
                        missionBackgroundFolder.getAbsolutePath()
                );
            }
        }
    }

    @Override
    protected void processSectionRect(final Section section, final int x, final int y, final int w, final int h) {
        final ExportRenderer renderer = new ExportRenderer(gamePlay);
        for (int screenNumber = 0; screenNumber < section.getNumberOfScreens(); screenNumber++) {
            final BufferedImage image = new BufferedImage(1_500, 1_500, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2d = image.createGraphics();
            g2d.translate(getScreenOffsetX(section, x, screenNumber), getScreenOffsetY(section, y, screenNumber));
            renderer.render(g2d);
            g2d.dispose();

            final String fileName = String.format("%s/%d-%d.png",
                    getMissionBackgroundFolder().getAbsolutePath(),
                    sectionNumber,
                    screenNumber
            );
            try {
                logger.log(Level.FINE, "Exporting mission background: {0}", fileName);
                ImageIO.write(image, "PNG", new File(fileName));
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to write section background", ex);
            }
        }
        sectionNumber++;
    }

    private int getScreenOffsetX(final Section section, final int sectionStartX, final int screenNumber) {
        switch (section.getDirection()) {
            case LEFT:
                return -sectionStartX - ((section.getNumberOfScreens() - screenNumber - 1) * 1_500);
            case RIGHT:
                return -sectionStartX + (1_500 * screenNumber);
            default:
                return -sectionStartX;
        }
    }

    private int getScreenOffsetY(final Section section, final int sectionStartY, final int screenNumber) {
        switch (section.getDirection()) {
            case UP:
                return -sectionStartY - ((section.getNumberOfScreens() - screenNumber - 1) * 1_500);
            default:
                return -sectionStartY;
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
                        logger.log(Level.FINE, "Deleting old mission background {0}", p.toString());
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
