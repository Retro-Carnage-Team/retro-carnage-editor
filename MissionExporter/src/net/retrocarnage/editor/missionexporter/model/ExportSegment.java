package net.retrocarnage.editor.missionexporter.model;

import java.util.LinkedList;
import java.util.List;
import net.retrocarnage.editor.missionexporter.impl.ExportFolderStructure;
import net.retrocarnage.editor.model.Section;

/**
 * A wrapper for the Segment entity used to apply correct naming and formatting.
 *
 * @author Thomas Werner
 */
public class ExportSegment {

    private final ExportFolderStructure exportFolderStructure;
    private final Section section;
    private final int sectionNumber;

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
            result.add(exportFolderStructure.getBackgroundImageRelativePath(sectionNumber, sectionNumber));
        }
        return result;
    }

    public String getDirection() {
        return section.getDirection().getExportName();
    }

}
