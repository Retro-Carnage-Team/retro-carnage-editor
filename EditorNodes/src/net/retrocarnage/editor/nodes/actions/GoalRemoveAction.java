package net.retrocarnage.editor.nodes.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.nodes.icons.IconProvider;
import org.openide.util.Lookup;

/**
 * Removes the Goal of a given Layer.
 *
 * @author Thomas Werner
 */
public class GoalRemoveAction extends AbstractAction {
    
    private static final Image ICON = IconUtil.loadIcon(IconProvider.DELETE_ICON.getResourcePath());

    private final transient Layer layer;

    public GoalRemoveAction(final Layer layer) {
        super("Delete", new ImageIcon(ICON));
        this.layer = layer;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        layer.setGoal(null);

        final SelectionController selectionCtrl = Lookup.getDefault().lookup(SelectionController.class);
        if (null != selectionCtrl) {
            selectionCtrl.setSelection(null);
        }
    }

}
