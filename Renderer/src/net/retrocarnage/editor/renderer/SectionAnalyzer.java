package net.retrocarnage.editor.renderer;

import java.util.List;
import net.retrocarnage.editor.model.Section;

/**
 * Analyses the structure of a map (defined by it's sections).
 *
 * @author Thomas Werner
 */
public class SectionAnalyzer {

    public SectionAnalysis analyzeMapStructure(final List<Section> sections) {
        int mapHeight = 1;
        int x = 0;
        int minX = 0;
        int maxX = 0;
        for (final Section s : sections) {
            if (null != s.getDirection()) {
                switch (s.getDirection()) {
                    case LEFT:
                        x -= s.getNumberOfScreens() - 1;
                        minX = Math.min(x, minX);
                        break;
                    case RIGHT:
                        x += s.getNumberOfScreens() - 1;
                        maxX = Math.max(x, maxX);
                        break;
                    case UP:
                        mapHeight += s.getNumberOfScreens() - 1;
                        break;
                    default:
                        break;
                }
            }
        }
        return new SectionAnalysis(maxX - minX + 1, mapHeight, 0 - minX, 0);
    }


}
