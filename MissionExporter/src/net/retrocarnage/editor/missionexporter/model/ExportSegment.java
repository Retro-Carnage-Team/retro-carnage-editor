package net.retrocarnage.editor.missionexporter.model;

import java.util.LinkedList;
import java.util.List;
import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.model.Section;

/**
 * A wrapper for the Section entity used to apply correct naming and formatting.
 *
 * @author Thomas Werner
 */
public class ExportSegment {

    private final ExportFolderStructure exportFolderStructure;
    private final Section section;
    private final int sectionNumber;
    private List<ExportObstacle> obstacles;

    public ExportSegment(
            final ExportFolderStructure exportFolderStructure,
            final Section section,
            final int sectionNumber
    ) {
        this.exportFolderStructure = exportFolderStructure;
        this.section = section;
        this.sectionNumber = sectionNumber;
    }

    public List<String> getBackgrounds() {
        final List<String> result = new LinkedList<>();
        for (int screen = 0; screen < section.getNumberOfScreens(); screen++) {
            result.add(exportFolderStructure.getBackgroundImageRelativePath(sectionNumber, screen));
        }
        return result;
    }

    public String getDirection() {
        return section.getDirection().getExportName();
    }

    // TODO: enemies
    // TODO: goal
    public List<ExportObstacle> getObstacles() {
        return obstacles;
    }

    void setObstacles(final List<ExportObstacle> obstacles) {
        this.obstacles = obstacles;
    }

}
