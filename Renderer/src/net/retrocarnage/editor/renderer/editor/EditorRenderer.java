package net.retrocarnage.editor.renderer.editor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.geom.Area;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.playermodeloverlay.PlayerModelOverlayService;
import net.retrocarnage.editor.renderer.SectionAnalyzer;
import net.retrocarnage.editor.renderer.common.GamePlayGraphics2D;
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

    private final GamePlay gamePlay;
    private Selectable selection;
    private final SectionAnalyzer.SectionAnalysis sectionAnalysis;
    private final Rectangle viewRect;

    public EditorRenderer(final GamePlay gamePlay) {
        this(gamePlay, null, null);
    }

    public EditorRenderer(final GamePlay gamePlay, final Selectable selection, final Rectangle viewRect) {
        this.gamePlay = gamePlay;
        this.selection = selection;
        this.sectionAnalysis = new SectionAnalyzer().analyzeMapStructure(gamePlay.getSections());
        this.viewRect = viewRect;
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

            paintContent(g2d);
        }

        stopWatch.stop();
        final long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
        logger.log(Level.FINE, String.format("Rendering the mission took %d ms", duration));
    }

    private void paintContent(final Graphics2D g2d) {
        final int gameScreenWidth = calculateGameScreenWidth();
        final float scaling = (float) (ZoomService.getDefault().getZoomLevel() / 100.0);

        // Background
        new BackgroundPainter(sectionAnalysis, gamePlay.getSections(), gameScreenWidth, g2d).paintBackground();

        // Gameplay content
        final Area gameArea = new ClipShapeFactory(sectionAnalysis, gamePlay.getSections(), gameScreenWidth).build();
        try ( GamePlayGraphics2D gpg2d = new GamePlayGraphics2D(g2d, gameArea)) {
            new SpritePainter(gamePlay.getLayers(), gpg2d, scaling).paintSprites();
            new ObstaclePainter(gamePlay.getLayers(), gpg2d, scaling).paintObstacles();
            new EnemyPainter(gamePlay.getLayers(), gpg2d, scaling).paintEnemies();
        }

        // Selections
        new SelectionPainter(g2d, selection, scaling).paintSelectionBorder();

        // Player model overlay
        if (PlayerModelOverlayService.getDefault().isPlayerModelOverlayVisible()) {
            new PlayerModelPainter(g2d, scaling, viewRect).paintPlayerModel();
        }
    }

    private int calculateGameScreenWidth() {
        // TODO: Replace constant value
        return (int) (1_500 * ZoomService.getDefault().getZoomLevel() / 100.0);
    }

}
