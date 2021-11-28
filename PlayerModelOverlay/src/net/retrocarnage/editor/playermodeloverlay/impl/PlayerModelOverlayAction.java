package net.retrocarnage.editor.playermodeloverlay.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.retrocarnage.editor.playermodeloverlay.PlayerModelOverlayService;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "View",
        id = "net.retrocarnage.editor.playermodeloverlay.impl.PlayerModelOverlayAction"
)
@ActionRegistration(
        iconBase = "net/retrocarnage/editor/playermodeloverlay/impl/icon.png",
        displayName = "#CTL_PlayerModelOverlayAction"
)
@ActionReference(path = "Menu/View", position = 0)
@Messages("CTL_PlayerModelOverlayAction=Toggle player model overlay")
public final class PlayerModelOverlayAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        PlayerModelOverlayServiceImpl service = (PlayerModelOverlayServiceImpl) PlayerModelOverlayService.getDefault();
        service.setPlayerModelOverlayVisible(!service.isPlayerModelOverlayVisible());
    }
}
