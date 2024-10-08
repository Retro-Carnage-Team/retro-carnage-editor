package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.model.Layer;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Changes the name of a layer.
 *
 * @author Thomas Werner
 */
public final class LayerRenameAction extends AbstractAction {

    private static final String DIALOG_TEXT = "Name";
    private static final String DIALOG_TITLE = "Please specify the new name for the Layer";

    private final Layer layer;
    private final LayerController layerCtrl;

    public LayerRenameAction(final Layer layer, final LayerController layerCtrl) {
        this.layer = layer;
        this.layerCtrl = layerCtrl;
        putValue(NAME, "Rename");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final NotifyDescriptor.InputLine inputLine = new NotifyDescriptor.InputLine(DIALOG_TEXT, DIALOG_TITLE);
        final Object result = DialogDisplayer.getDefault().notify(inputLine);
        if (NotifyDescriptor.OK_OPTION.equals(result)) {
            layerCtrl.renameLayer(layer, inputLine.getInputText());
        }
    }

}
