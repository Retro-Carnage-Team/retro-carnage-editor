package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.renderer.editor.EditorRenderer;

/**
 * Renders displays the currently set GamePlay.
 *
 * @author Thomas Werner
 */
public class GamePlayDisplay extends JPanel {

    static final int BORDER_WIDTH = 200;

    private final JScrollPane container;
    private GamePlay gamePlay;
    private Selectable selection;
    private Dimension gamePlaySize = new Dimension(1, 1);

    public GamePlayDisplay(final JScrollPane container) {
        this.container = container;
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(final MouseEvent evt) {
                updateCursor(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                EventQueue.invokeLater(() -> updateCursor(evt));
            }
        });
    }

    /**
     * Updates the GamePlay to be drawn.
     *
     * Changes to the given object will not be monitored. To update the display you'll have to call this method again.
     *
     * @param gamePlay the gamePlay to be drawn.
     */
    public void updateDisplay(final GamePlay gamePlay, final Selectable selection) {
        if (null == gamePlay) {
            this.gamePlay = null;
            this.selection = null;
            gamePlaySize = new Dimension(1, 1);
        } else {
            this.gamePlay = gamePlay;
            this.selection = selection;
            final EditorRenderer renderer = new EditorRenderer(gamePlay);
            gamePlaySize = renderer.getSize();
            container.revalidate();
            repaint();                                                                                                  // TODO: Check: is this necessary?
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                (int) (gamePlaySize.getWidth() + 2 * BORDER_WIDTH),
                (int) (gamePlaySize.getHeight() + 2 * BORDER_WIDTH)
        );
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (null != gamePlay) {
            final Rectangle visibleRect = container.getViewport().getViewRect();
            final EditorRenderer renderer = new EditorRenderer(gamePlay, selection, visibleRect);
            gamePlaySize = renderer.getSize();

            final Graphics2D g2d = (Graphics2D) g;
            g2d.translate(BORDER_WIDTH, BORDER_WIDTH);
            renderer.render(g2d);
        }
    }

    private void updateCursor(final MouseEvent evt) {
        Cursor newCursor;
        if (null == selection) {
            newCursor = Cursor.getDefaultCursor();
        } else {
            final Point mouse = (Point) evt.getPoint().clone();
            mouse.translate(-BORDER_WIDTH, -BORDER_WIDTH);
            final SelectionMousePositionAnalyzer smia = new SelectionMousePositionAnalyzer(selection, mouse);
            newCursor = smia.getCursor(selection.isResizable(), selection.isMovable());
        }

        if (newCursor.getType() != getCursor().getType()) {
            setCursor(newCursor);
        }
    }

}
