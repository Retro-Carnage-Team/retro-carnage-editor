package net.retrocarnage.editor.renderer.minimap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.util.List;
import javax.swing.UIManager;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionAnalyzer;

/**
 * Renders a minimap (showing the section structure only) for a mission.
 *
 * @author Thomas Werner
 */
public class MinimapRenderer {

    private static final int STROKE = 2;

    private final List<Section> sections;
    private final SectionAnalysis sectionAnalysis;

    public MinimapRenderer(final List<Section> sections) {
        this.sections = sections;
        this.sectionAnalysis = new SectionAnalyzer().analyzeMapStructure(sections);
    }

    /**
     * Calculates the actual dimensions of the generated graphic based on the given max width and height of the target
     * graphic.
     *
     * @param width max width of the target graphic
     * @param height max height of the target graphic
     * @return the actual dimensions of the generated graphic
     */
    public Dimension getSize(final int width, final int height) {
        final int gameScreenWidth = calculateGameScreenWidth(width, height);
        return new Dimension(
                sectionAnalysis.getMapWidth() * gameScreenWidth,
                sectionAnalysis.getMapHeight() * gameScreenWidth
        );
    }

    /**
     * Renders the minimap onto a given graphics context.
     *
     * @param g2d the graphics context
     * @param width max width of the target graphic
     * @param height max height of the target graphic
     */
    public void render(final Graphics2D g2d, final int width, final int height) {
        if (null != sections && !sections.isEmpty()) {
            g2d.setColor(UIManager.getColor("Panel.background"));
            g2d.fillRect(0, 0, width, height);
            g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(STROKE));

            final int gameScreenWidth = calculateGameScreenWidth(width, height);

            int posX = sectionAnalysis.getStartX();
            int posY = sectionAnalysis.getStartY();
            for (Section section : sections) {
                switch (section.getDirection()) {
                    case LEFT:
                        paintSectionToLeft(posX, section, gameScreenWidth, posY, g2d);
                        posX -= (section.getNumberOfScreens() - 1);
                        break;
                    case RIGHT:
                        paintSectionToRight(posX, gameScreenWidth, posY, section, g2d);
                        posX += (section.getNumberOfScreens() - 1);
                        break;
                    case UP:
                        paintSectionToTop(posX, gameScreenWidth, posY, section, g2d);
                        posY += (section.getNumberOfScreens() - 1);
                        break;
                    default:
                        break;
                }
            }

            paintStartScreenSymbol(g2d, gameScreenWidth);
            paintEndScreenSymbol(g2d, posX, gameScreenWidth);
        }
    }

    private void paintSectionToRight(
            final int posX, final int gameScreenWidth, final int posY, final Section section, final Graphics2D g2d
    ) {
        final int x = posX * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);

        g2d.drawLine(
                (posX + 1) * gameScreenWidth,
                (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth,
                (posX + 1) * gameScreenWidth,
                (sectionAnalysis.getMapHeight() - posY) * gameScreenWidth
        );
    }

    private void paintSectionToTop(
            final int posX, final int gameScreenWidth, final int posY, final Section section, final Graphics2D g2d
    ) {
        final int x = posX * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - section.getNumberOfScreens()) * gameScreenWidth;
        final int w = gameScreenWidth;
        final int h = section.getNumberOfScreens() * gameScreenWidth;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);

        if (posX != sectionAnalysis.getStartX() || posY != sectionAnalysis.getStartY()) {
            g2d.drawLine(
                    posX * gameScreenWidth,
                    (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth,
                    (posX + 1) * gameScreenWidth,
                    (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth
            );
        }
    }

    private void paintSectionToLeft(
            final int posX, final Section section, final int gameScreenWidth, final int posY, final Graphics2D g2d
    ) {
        final int x = (posX - section.getNumberOfScreens() + 1) * gameScreenWidth;
        final int y = (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);

        g2d.drawLine(
                posX * gameScreenWidth,
                (sectionAnalysis.getMapHeight() - posY - 1) * gameScreenWidth,
                posX * gameScreenWidth,
                (sectionAnalysis.getMapHeight() - posY) * gameScreenWidth
        );
    }

    private void paintEndScreenSymbol(final Graphics2D g2d, final int posX, final int gameScreenWidth) {
        g2d.drawOval(
                posX * gameScreenWidth + gameScreenWidth / 10,
                gameScreenWidth / 10,
                (int) (gameScreenWidth * 0.8),
                (int) (gameScreenWidth * 0.8)
        );
    }

    private void paintStartScreenSymbol(final Graphics2D g2d, final int gameScreenWidth) {
        g2d.drawLine(
                sectionAnalysis.getStartX() * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((sectionAnalysis.getMapHeight() - 1) * gameScreenWidth + gameScreenWidth * 0.1),
                sectionAnalysis.getStartX() * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((sectionAnalysis.getMapHeight() - 1) * gameScreenWidth + gameScreenWidth * 0.8)
        );
        g2d.drawLine(
                sectionAnalysis.getStartX() * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((sectionAnalysis.getMapHeight() - 1) * gameScreenWidth + gameScreenWidth * 0.1),
                (int) (sectionAnalysis.getStartX() * gameScreenWidth + gameScreenWidth * 0.25),
                (int) ((sectionAnalysis.getMapHeight() - 1) * gameScreenWidth + gameScreenWidth * 0.45)
        );
        g2d.drawLine(
                sectionAnalysis.getStartX() * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((sectionAnalysis.getMapHeight() - 1) * gameScreenWidth + gameScreenWidth * 0.1),
                (int) (sectionAnalysis.getStartX() * gameScreenWidth + gameScreenWidth * 0.75),
                (int) ((sectionAnalysis.getMapHeight() - 1) * gameScreenWidth + gameScreenWidth * 0.45)
        );
    }

    private int calculateGameScreenWidth(final int targetWidth, final int targetHeight) {
        final int minDimension = Math.min(targetWidth, targetHeight);
        return minDimension / Math.max(sectionAnalysis.getMapHeight(), sectionAnalysis.getMapWidth());
    }

}
