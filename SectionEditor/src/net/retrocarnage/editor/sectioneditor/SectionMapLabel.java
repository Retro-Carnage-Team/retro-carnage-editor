package net.retrocarnage.editor.sectioneditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.UIManager;
import net.retrocarnage.editor.model.gameplay.Section;

/**
 * A JLabel that displays a mini-map of the sections that the user configured.
 *
 * @author Thomas Werner
 */
class SectionMapLabel extends JLabel {

    private static final int STROKE = 3;

    private List<Section> sections;
    private int mapHeight, mapWidth;
    private int startX, startY;

    /**
     * Updates the sections to be drawn.
     *
     * Changes to the given list will not be monitored. To update the map you'll have to call this method again.
     *
     * @param sections the sections to be drawn.
     */
    public void setSections(final List<Section> sections) {
        if (null == sections) {
            this.sections = null;
        } else {
            this.sections = Collections.unmodifiableList(sections);
            analyzeMapStructure();
            repaint();
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (null != sections && !sections.isEmpty()) {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(UIManager.getColor("Panel.background"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(STROKE));

            final int gameScreenWidth = getWidth() / Math.max(mapHeight, mapWidth);

            int posX = startX;
            int posY = startY;
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
        final int y = (mapHeight - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);

        g2d.drawLine(
                (posX + 1) * gameScreenWidth,
                (mapHeight - posY - 1) * gameScreenWidth,
                (posX + 1) * gameScreenWidth,
                (mapHeight - posY) * gameScreenWidth
        );
    }

    private void paintSectionToTop(
            final int posX, final int gameScreenWidth, final int posY, final Section section, final Graphics2D g2d
    ) {
        final int x = posX * gameScreenWidth;
        final int y = (mapHeight - posY - section.getNumberOfScreens()) * gameScreenWidth;
        final int w = gameScreenWidth;
        final int h = section.getNumberOfScreens() * gameScreenWidth;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);

        if (posX != startX || posY != startY) {
            g2d.drawLine(
                    posX * gameScreenWidth,
                    (mapHeight - posY - 1) * gameScreenWidth,
                    (posX + 1) * gameScreenWidth,
                    (mapHeight - posY - 1) * gameScreenWidth
            );
        }
    }

    private void paintSectionToLeft(
            final int posX, final Section section, final int gameScreenWidth, final int posY, final Graphics2D g2d
    ) {
        final int x = (posX - section.getNumberOfScreens() + 1) * gameScreenWidth;
        final int y = (mapHeight - posY - 1) * gameScreenWidth;
        final int w = section.getNumberOfScreens() * gameScreenWidth;
        final int h = gameScreenWidth;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);

        g2d.drawLine(
                posX * gameScreenWidth,
                (mapHeight - posY - 1) * gameScreenWidth,
                posX * gameScreenWidth,
                (mapHeight - posY) * gameScreenWidth
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
                startX * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((mapHeight - 1) * gameScreenWidth + gameScreenWidth * 0.1),
                startX * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((mapHeight - 1) * gameScreenWidth + gameScreenWidth * 0.8)
        );
        g2d.drawLine(
                startX * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((mapHeight - 1) * gameScreenWidth + gameScreenWidth * 0.1),
                (int) (startX * gameScreenWidth + gameScreenWidth * 0.25),
                (int) ((mapHeight - 1) * gameScreenWidth + gameScreenWidth * 0.45)
        );
        g2d.drawLine(
                startX * gameScreenWidth + (gameScreenWidth + 3) / 2,
                (int) ((mapHeight - 1) * gameScreenWidth + gameScreenWidth * 0.1),
                (int) (startX * gameScreenWidth + gameScreenWidth * 0.75),
                (int) ((mapHeight - 1) * gameScreenWidth + gameScreenWidth * 0.45)
        );
    }

    private void analyzeMapStructure() {
        mapHeight = 1;
        int x = 0, minX = 0, maxX = 0;
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
        mapWidth = maxX - minX + 1;
        startX = 0 - minX;
        startY = 0;
    }

}
