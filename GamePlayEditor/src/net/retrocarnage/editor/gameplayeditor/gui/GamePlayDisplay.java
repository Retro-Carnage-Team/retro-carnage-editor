package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
            final Graphics2D g2d = (Graphics2D) g;
            final EditorRenderer renderer = new EditorRenderer(gamePlay, selection);
            gamePlaySize = renderer.getSize();
            g2d.translate(BORDER_WIDTH, BORDER_WIDTH);
            renderer.render(g2d);
        }
    }

}
