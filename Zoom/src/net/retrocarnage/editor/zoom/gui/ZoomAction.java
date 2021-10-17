package net.retrocarnage.editor.zoom.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.actions.Presenter;

/**
 * This Action is used to integrate the ZoomPanel into the Zoom toolbar.
 *
 * @author Thomas Werner
 */
@ActionID(category = "Zoom", id = "net.retrocarnage.editor.zoom.gui.ZoomAction")
@ActionRegistration(lazy = false, displayName = "NOT-USED")
@ActionReference(path = "Toolbars/Zoom", position = 300)
public final class ZoomAction extends AbstractAction implements Presenter.Toolbar {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Delegated to toolbar
    }

    @Override
    public Component getToolbarPresenter() {
        return new ZoomPanel();
    }
}
