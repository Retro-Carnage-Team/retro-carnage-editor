package net.retrocarnage.editor.renderer.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionPathRunner;

/**
 * Paints the white background of a virgin mission section.
 *
 * @author Thomas Werner
 */
class BackgroundPainter {

    private static final Color BACKGROUND = Color.WHITE;

    private final SectionAnalysis sectionAnalysis;
    private final List<Section> sections;
    private final int gameScreenWidth;
    private final Graphics2D g2d;

    public BackgroundPainter(
            final SectionAnalysis sectionAnalysis,
            final List<Section> sections,
            final int gameScreenWidth,
            final Graphics2D g2d) {
        this.sectionAnalysis = sectionAnalysis;
        this.sections = sections;
        this.gameScreenWidth = gameScreenWidth;
        this.g2d = g2d;
    }

    public void paintBackground() {
        new SectionPathRunner(sectionAnalysis, sections, gameScreenWidth) {
            @Override
            protected void processSectionRect(final Section section, final int x, final int y, final int w, final int h) {
                g2d.setColor(BACKGROUND);
                g2d.fillRect(x, y, w, h);
            }
        }.run();
    }

}
