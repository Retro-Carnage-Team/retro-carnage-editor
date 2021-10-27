package net.retrocarnage.editor.renderer.editor;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.zoom.ZoomService;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Renders a mission inside the editor screen.
 *
 * This is the most sophisticated renderer - as it supports all kinds of visual items, selections, and more.
 *
 * @author Thomas Werner
 */
public class EditorRenderer {

    private static final Logger logger = Logger.getLogger(EditorRenderer.class.getName());
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
        final StopWatch stopWatch = StopWatch.createStarted();

        if (null != gamePlay && null != gamePlay.getSections() && !gamePlay.getSections().isEmpty()) {
            final Dimension dimension = getSize();
            g2d.setColor(UIManager.getColor("Panel.background"));
            g2d.fillRect(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());
            g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(STROKE));

            final int gameScreenWidth = calculateGameScreenWidth();
            new BackgroundPainter(sectionAnalysis, gamePlay.getSections(), gameScreenWidth, g2d).paintBackground();
            new SpritePainter(gamePlay.getLayers(), g2d).paintSprites();
        }

        stopWatch.stop();
        final long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, String.format("Rendering the mission took %d ms", duration));
    }

    private int calculateGameScreenWidth() {
        return (int) (1_500 * ZoomService.getDefault().getZoomLevel() / 100.0);
    }

}
