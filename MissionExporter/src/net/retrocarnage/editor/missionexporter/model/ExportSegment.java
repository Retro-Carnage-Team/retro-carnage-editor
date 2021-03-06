package net.retrocarnage.editor.missionexporter.model;

import java.util.LinkedList;
import java.util.List;
import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.model.Position;
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

    private Position goal;
    private List<ExportEnemy> enemies;
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

    public List<ExportEnemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(final List<ExportEnemy> enemies) {
        this.enemies = enemies;
    }

    public void setGoal(final Position goal) {
        this.goal = goal;
    }

    public Position getGoal() {
        return goal;
    }

    public List<ExportObstacle> getObstacles() {
        return obstacles;
    }

    void setObstacles(final List<ExportObstacle> obstacles) {
        this.obstacles = obstacles;
    }

}
