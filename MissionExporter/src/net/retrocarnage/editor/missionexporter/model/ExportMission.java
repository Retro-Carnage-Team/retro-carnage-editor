package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedList;
import java.util.List;
import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Location;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Section;

/**
 * A wrapper for the mission entity used to apply correct naming and formatting.
 *
 * @author Thomas Werner
 */
@JsonPropertyOrder({"briefing", "client", "location", "music"})
public class ExportMission {

    private final Mission mission;
    private final ExportFolderStructure exportFolderStructure;

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
        final List<ExportSegment> result = new LinkedList<>();
        final GamePlay gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
        for (int segmentNumber = 0; segmentNumber < gamePlay.getSections().size(); segmentNumber++) {
            final Section section = gamePlay.getSections().get(segmentNumber);
            result.add(new ExportSegment(exportFolderStructure, section, segmentNumber));
        }
        return result;
    }

}
