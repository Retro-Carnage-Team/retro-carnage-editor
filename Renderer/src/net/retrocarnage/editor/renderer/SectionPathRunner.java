package net.retrocarnage.editor.renderer;

import java.util.List;
import net.retrocarnage.editor.model.Section;

/**
 * Calculates the areas of a sequence of sections.
 *
 * @author Thomas Werner
 */
public abstract class SectionPathRunner {

    private final SectionAnalysis sectionAnalysis;
    private final List<Section> sections;
    private final int gameScreenWidth;

    public SectionPathRunner(
            final SectionAnalysis sectionAnalysis,
            final List<Section> sections,
            final int gameScreenWidth) {
        this.sectionAnalysis = sectionAnalysis;
        this.sections = sections;
        this.gameScreenWidth = gameScreenWidth;
    }

    public void run() {
        int posX = sectionAnalysis.getStartX();
        int posY = sectionAnalysis.getStartY();
        for (Section section : sections) {
            switch (section.getDirection()) {
                case LEFT:
                    processSectionRectToLeft(posX, section, gameScreenWidth, posY);
                    posX -= (section.getNumberOfScreens() - 1);
                    break;
                case RIGHT:
                    processSectionRectToRight(posX, gameScreenWidth, posY, section);
                    posX += (section.getNumberOfScreens() - 1);
                    break;
                case UP:
                    processSectionRectToTop(posX, gameScreenWidth, posY, section);
                    posY += (section.getNumberOfScreens() - 1);
                    break;
                default:
                    break;
            }
        }
    }

    private void processSectionRectToRight(
            final int posX,
            final int gameScreenWidth,
            final int posY,
            final Section section
    ) {
        final int x = posX * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        processSectionRect(section, x, y, w, h);
    }

    private void processSectionRectToTop(
            final int posX,
            final int gameScreenWidth,
            final int posY,
            final Section section
    ) {
        final int x = posX * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - section.getNumberOfScreens()) * gameScreenWidth;
        final int w = gameScreenWidth;
        final int h = section.getNumberOfScreens() * gameScreenWidth;
        processSectionRect(section, x, y, w, h);
    }

    private void processSectionRectToLeft(
            final int posX,
            final Section section,
            final int gameScreenWidth,
            final int posY
    ) {
        final int x = (posX - section.getNumberOfScreens() + 1) * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        processSectionRect(section, x, y, w, h);
    }

    protected abstract void processSectionRect(Section section, int x, int y, int w, int h);
}
