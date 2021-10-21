package net.retrocarnage.editor.renderer.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import javax.swing.UIManager;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.zoom.ZoomService;

/**
 * Renders a mission.
 *
 * @author Thomas Werner
 */
public class EditorRenderer {

    private static final int STROKE = 2;

    private final GamePlay gamePlay;
    private final SectionAnalyzer.SectionAnalysis sectionAnalysis;

    public EditorRenderer(final GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.sectionAnalysis = new SectionAnalyzer().analyzeMapStructure(gamePlay.getSections());
    }

    /**
     * Calculates the dimensions required to display the generated graphic.
     *
     * @return the actual dimensions of the generated graphic
     */
    public Dimension getSize() {
        final int gameScreenWidth = calculateGameScreenWidth();
        return new Dimension(
                sectionAnalysis.getMapWidth() * gameScreenWidth,
                sectionAnalysis.getMapHeight() * gameScreenWidth
        );
    }

    /**
     * Renders the minimap onto a given graphics context.
     *
     * @param g2d the graphics context
     */
    public void render(final Graphics2D g2d) {
        if (null != gamePlay && null != gamePlay.getSections() && !gamePlay.getSections().isEmpty()) {
            final Dimension dimension = getSize();
            g2d.setColor(UIManager.getColor("Panel.background"));
            g2d.fillRect(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());
            g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(STROKE));

            final int gameScreenWidth = calculateGameScreenWidth();

            int posX = sectionAnalysis.getStartX();
            int posY = sectionAnalysis.getStartY();
            for (Section section : gamePlay.getSections()) {
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

    private int calculateGameScreenWidth() {
        return (int) (1_500 * ZoomService.getDefault().getZoomLevel() / 100.0);
    }

}
