package net.retrocarnage.editor.nodes.nodes;

import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;

/**
 * Creates children for each Layer of a given LayerController.
 *
 * @author Thomas Werner
 */
public class LayerChildrenController extends LayerChildren {

    private final LayerController controller;

    public LayerChildrenController(final LayerController controller) {
        this.controller = controller;
        this.controller.addPropertyChangeListener(this);
    }

    @Override
    protected LayerController getController() {
        return controller;
    }

}
