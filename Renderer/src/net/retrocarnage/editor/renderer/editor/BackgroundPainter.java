package net.retrocarnage.editor.renderer.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalyzer;

/**
 * Paints the white background of a virgin mission section.
 *
 * @author Thomas Werner
 */
class BackgroundPainter {

    private static final Color BACKGROUND = Color.WHITE;

    private final SectionAnalyzer.SectionAnalysis sectionAnalysis;
    private final List<Section> sections;
    private final int gameScreenWidth;
    private final Graphics2D g2d;

    public BackgroundPainter(
            final SectionAnalyzer.SectionAnalysis sectionAnalysis,
            final List<Section> sections,
            final int gameScreenWidth,
            final Graphics2D g2d) {
        this.sectionAnalysis = sectionAnalysis;
        this.sections = sections;
        this.gameScreenWidth = gameScreenWidth;
        this.g2d = g2d;
    }

    public void paintBackground() {
        int posX = sectionAnalysis.getStartX();
        int posY = sectionAnalysis.getStartY();
        for (Section section : sections) {
            switch (section.getDirection()) {
                case LEFT:
                    paintSectionToLeft(posX, section, gameScreenWidth, posY);
                    posX -= (section.getNumberOfScreens() - 1);
                    break;
                case RIGHT:
                    paintSectionToRight(posX, gameScreenWidth, posY, section);
                    posX += (section.getNumberOfScreens() - 1);
                    break;
                case UP:
                    paintSectionToTop(posX, gameScreenWidth, posY, section);
                    posY += (section.getNumberOfScreens() - 1);
                    break;
                default:
                    break;
            }
        }
    }

    private void paintSectionToRight(final int posX, final int gameScreenWidth, final int posY, final Section section) {
        final int x = posX * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        g2d.setColor(BACKGROUND);
        g2d.fillRect(x, y, w, h);
    }

    private void paintSectionToTop(final int posX, final int gameScreenWidth, final int posY, final Section section) {
        final int x = posX * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - section.getNumberOfScreens()) * gameScreenWidth;
        final int w = gameScreenWidth;
        final int h = section.getNumberOfScreens() * gameScreenWidth;
        g2d.setColor(BACKGROUND);
        g2d.fillRect(x, y, w, h);
    }

    private void paintSectionToLeft(final int posX, final Section section, final int gameScreenWidth, final int posY) {
        final int x = (posX - section.getNumberOfScreens() + 1) * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        g2d.setColor(BACKGROUND);
        g2d.fillRect(x, y, w, h);
    }

}
