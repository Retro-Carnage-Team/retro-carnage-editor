package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.TransferHandler;
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
        return support.isDataFlavorSupported(Sprite.DATA_FLAVOR);
    }

    @Override
    public boolean importData(final TransferSupport support) {
        try {
            final Sprite sprite = (Sprite) support.getTransferable().getTransferData(Sprite.DATA_FLAVOR);
            controller.addSprite(sprite, support.getDropLocation().getDropPoint());
            return true;
        } catch (UnsupportedFlavorException | IOException ex) {
            logger.log(Level.WARNING, "Failed to handle D&D operation with Sprite", ex);
            return false;
        }
    }

}
