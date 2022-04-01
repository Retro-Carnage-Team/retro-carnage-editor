package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Point;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.TransferHandler;
import static net.retrocarnage.editor.gameplayeditor.gui.GamePlayDisplay.BORDER_WIDTH;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.Obstacle;
import net.retrocarnage.editor.model.Sprite;

/**
 * Manages the drag and drop gestures for the GamePlayEditor.
 *
 * @author Thomas Werner
 */
public class DragAndDropTransferHandler extends TransferHandler {

    private static final Logger logger = Logger.getLogger(DragAndDropTransferHandler.class.getName());

    private final GamePlayEditorController controller;

    DragAndDropTransferHandler(final GamePlayEditorController controller) {
        this.controller = controller;
    }

    @Override
    public boolean canImport(final TransferSupport support) {
        return support.isDataFlavorSupported(Enemy.DATA_FLAVOR)
                || support.isDataFlavorSupported(Sprite.DATA_FLAVOR)
                || support.isDataFlavorSupported(Obstacle.DATA_FLAVOR);
    }

    @Override
    public boolean importData(final TransferSupport support) {
        try {
            final Point dropLocation = support.getDropLocation().getDropPoint();
            dropLocation.translate(-BORDER_WIDTH, -BORDER_WIDTH);

            if (support.isDataFlavorSupported(Enemy.DATA_FLAVOR)) {
                final Enemy enemy = (Enemy) support.getTransferable().getTransferData(Enemy.DATA_FLAVOR);
                controller.addEnemy(enemy, dropLocation);
            } else if (support.isDataFlavorSupported(Sprite.DATA_FLAVOR)) {
                final Sprite sprite = (Sprite) support.getTransferable().getTransferData(Sprite.DATA_FLAVOR);
                controller.addSprite(sprite, dropLocation);
            } else if (support.isDataFlavorSupported(Obstacle.DATA_FLAVOR)) {
                final Obstacle obstacle = (Obstacle) support.getTransferable().getTransferData(Obstacle.DATA_FLAVOR);
                controller.addObstacle(obstacle, dropLocation);
            }
            return true;
        } catch (UnsupportedFlavorException | IOException ex) {
            logger.log(Level.SEVERE, "Failed to handle D&D operation on GamePlayEditor", ex);
            return false;
        }
    }

}
