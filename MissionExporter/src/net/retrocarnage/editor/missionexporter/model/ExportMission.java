package net.retrocarnage.editor.missionexporter.model;

import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.model.Mission;

/**
 * A wrapper that adds some export
 *
 *
 * @author Thomas Werner
 */
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

}
