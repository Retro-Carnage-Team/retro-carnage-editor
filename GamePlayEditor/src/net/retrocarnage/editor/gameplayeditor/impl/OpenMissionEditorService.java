package net.retrocarnage.editor.gameplayeditor.impl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.gameplayeditor.gui.GamePlayEditorTopComponent;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.nodes.actions.MissionEditAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of EditGamePlayAction.OpenGamePlayEditorHandler.
 *
 * This implementation will open a new MissionEditorTopComponent - if the Mission is not opened, yet.
 *
 * @author Thomas Werner
 */
@ServiceProvider(service = MissionEditAction.OpenGamePlayEditorHandler.class)
public class OpenMissionEditorService implements MissionEditAction.OpenGamePlayEditorHandler {

    private static final Logger logger = Logger.getLogger(OpenMissionEditorService.class.getName());
    
    @Override
    public void openGamePlayEditor(Mission mission) {
        GamePlayEditorTopComponent window = GamePlayEditorRepository.INSTANCE.findEditorForMission(mission);
        if (null == window) {
            try {
                window = new GamePlayEditorTopComponent(mission);
                window.open();
            } catch(IOException iox) {
                final String msg = String.format(
                        "Failed to open mission '%s': %s", 
                        mission.getName(), 
                        iox.getMessage()
                );
                logger.log(Level.WARNING, msg, iox);
                DialogDisplayer
                        .getDefault()
                        .notify(new NotifyDescriptor.Message(msg, NotifyDescriptor.WARNING_MESSAGE));
            }
        }
        
        if(null != window) {
            window.requestActive();
        }
    }

}
