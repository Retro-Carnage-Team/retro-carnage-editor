package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Location;
import net.retrocarnage.editor.model.Mission;
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
        return exportFolderStructure.getMusicRelativePath();
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
        final GamePlay gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
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
            super(mapStructure, sections, 1_500);
            this.gamePlay = gamePlay;
            this.exportFolderStructure = exportFolderStructure;
        }

        public List<ExportSegment> getSegments() {
            return segments;
        }

        @Override
        protected void processSectionRect(final Section section, final int x, final int y, final int w, final int h) {
            final ExportSegment exportSegment = new ExportSegment(exportFolderStructure, section, segmentNumber++);
            exportSegment.setObstacles(getObstaclesForSection(section, new Rectangle(x, y, w, h)));
            segments.add(exportSegment);
        }

        private List<ExportObstacle> getObstaclesForSection(final Section section, final Rectangle sectionRect) {
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

    }

}
