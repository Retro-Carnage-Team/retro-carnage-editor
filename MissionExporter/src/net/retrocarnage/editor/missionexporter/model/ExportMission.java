package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.retrocarnage.editor.core.GameConstants;
import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.missionmanager.MissionServiceFactory;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Goal;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Location;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Position;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.renderer.SectionPathRunner;

/**
 * A wrapper for the mission entity used to apply correct naming and formatting.
 *
 * @author Thomas Werner
 */
@JsonPropertyOrder({"briefing", "client", "location", "music", "name", "reward", "segments"})
public class ExportMission {

    private final Mission mission;
    private final ExportFolderStructure exportFolderStructure;
    private List<ExportSegment> segments = null;

    public ExportMission(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.mission = mission;
        this.exportFolderStructure = exportFolderStructure;
    }

    public String getBriefing() {
        return mission.getBriefing();
    }

    public String getClient() {
        return exportFolderStructure.getClientImageRelativePath();
    }

    public Location getLocation() {
        return mission.getLocation();
    }

    public String getMusic() {
        return exportFolderStructure.getMusicFile().getName();
    }

    public String getName() {
        return mission.getName();
    }

    public int getReward() {
        return mission.getReward();
    }

    public List<ExportSegment> getSegments() {
        if (null == segments) {
            initializeSegments();
        }
        return segments;
    }

    private void initializeSegments() {
        final GamePlay gamePlay = MissionServiceFactory
                .buildMissionService()
                .loadGamePlay(mission.getId());
        final SectionAnalysis mapStructure = new SectionAnalyzer().analyzeMapStructure(gamePlay.getSections());
        final SectionProcessor sectionProcessor = new SectionProcessor(
                mapStructure,
                gamePlay.getSections(),
                gamePlay,
                exportFolderStructure
        );
        sectionProcessor.run();
        this.segments = sectionProcessor.getSegments();
    }

    private static final class SectionProcessor extends SectionPathRunner {

        private final ExportFolderStructure exportFolderStructure;
        private final GamePlay gamePlay;
        private final List<ExportSegment> segments = new LinkedList<>();
        private int segmentNumber = 0;

        SectionProcessor(
                final SectionAnalysis mapStructure,
                final List<Section> sections,
                final GamePlay gamePlay,
                final ExportFolderStructure exportFolderStructure
        ) {
            super(mapStructure, sections, GameConstants.SCREEN_WIDTH);
            this.gamePlay = gamePlay;
            this.exportFolderStructure = exportFolderStructure;
        }

        public List<ExportSegment> getSegments() {
            return segments;
        }

        @Override
        protected void processSectionRect(final Section section, final int x, final int y, final int w, final int h) {
            final ExportSegment exportSegment = new ExportSegment(exportFolderStructure, section, segmentNumber++);
            final Rectangle sectionRect = new Rectangle(x, y, w, h);
            exportSegment.setEnemies(getEnemiesInSection(sectionRect));
            exportSegment.setGoal(getGoalInSection(sectionRect));
            exportSegment.setObstacles(getObstaclesInSection(sectionRect));
            segments.add(exportSegment);
        }

        private List<ExportEnemy> getEnemiesInSection(final Rectangle sectionRect) {
            final List<ExportEnemy> result = new ArrayList<>();
            for (Layer layer : gamePlay.getLayers()) {
                result.addAll(layer
                        .getEnemies()
                        .stream()
                        .filter((e) -> e.getPosition().toRectangle().intersects(sectionRect))
                        .map(e -> new ExportEnemy(e, sectionRect))
                        .collect(Collectors.toList())
                );
            }
            return result;
        }

        private List<ExportObstacle> getObstaclesInSection(final Rectangle sectionRect) {
            final List<ExportObstacle> result = new ArrayList<>();
            for (Layer layer : gamePlay.getLayers()) {
                result.addAll(layer
                        .getObstacles()
                        .stream()
                        .filter((o) -> o.getPosition().toRectangle().intersects(sectionRect))
                        .map(o -> new ExportObstacle(o, sectionRect))
                        .collect(Collectors.toList())
                );
                result.addAll(layer
                        .getVisualAssets()
                        .stream()
                        .filter((va) -> va.isObstacle())
                        .filter((va) -> va.getPosition().toRectangle().intersects(sectionRect))
                        .map(va  -> new ExportObstacle(va, sectionRect))
                        .collect(Collectors.toList())
                );
            }
            return result;
        }

        private Position getGoalInSection(Rectangle sectionRect) {
            final Optional<Goal> possibleGoal = gamePlay.getLayers()
                    .stream()
                    .filter(l -> null != l.getGoal())
                    .map(l -> l.getGoal())
                    .filter((g) -> g.getPosition().toRectangle().intersects(sectionRect))
                    .findFirst();

            if (possibleGoal.isPresent()) {
                final Position goalPosition = possibleGoal.get().getPosition();
                return new Position(
                        goalPosition.getX() - sectionRect.x,
                        goalPosition.getY() - sectionRect.y,
                        goalPosition.getWidth(),
                        goalPosition.getHeight()
                );
            }
            return null;
        }

    }

}
