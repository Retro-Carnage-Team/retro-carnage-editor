package net.retrocarnage.editor.renderer.export;

import java.awt.Dimension;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.geom.Area;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.renderer.common.ClipShapeFactory;
import net.retrocarnage.editor.renderer.common.Constants;
import net.retrocarnage.editor.renderer.common.GamePlayGraphics2D;
import net.retrocarnage.editor.renderer.common.SpritePainter;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Renders a mission for export.
 *
 * @author Thomas Werner
 */
public class ExportRenderer {

    private static final Logger logger = Logger.getLogger(ExportRenderer.class.getName());

    private final GamePlay gamePlay;
    private final SectionAnalysis sectionAnalysis;

    public ExportRenderer(final GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.sectionAnalysis = new SectionAnalyzer().analyzeMapStructure(gamePlay.getSections());
    }

    /**
     * Calculates the dimensions required to display the generated graphic.
     *
     * @return the actual dimensions of the generated graphic
     */
    public Dimension getSize() {
        return new Dimension(
                sectionAnalysis.getMapWidth() * Constants.SCREEN_WIDTH,
                sectionAnalysis.getMapHeight() * Constants.SCREEN_WIDTH
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

            paintContent(g2d);
        }

        stopWatch.stop();
        final long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
        logger.log(Level.FINE, String.format("Rendering the mission took %d ms", duration));
    }

    private void paintContent(final Graphics2D g2d) {
        final Area gameArea = new ClipShapeFactory(
                sectionAnalysis,
                gamePlay.getSections(),
                Constants.SCREEN_WIDTH
        ).build();
        try (GamePlayGraphics2D gpg2d = new GamePlayGraphics2D(g2d, gameArea)) {
            new SpritePainter(gamePlay.getLayers(), gpg2d, 1).paintSprites();
        }
    }

}
