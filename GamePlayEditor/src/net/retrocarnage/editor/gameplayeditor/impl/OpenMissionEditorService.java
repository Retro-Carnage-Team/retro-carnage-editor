package net.retrocarnage.editor.gameplayeditor.impl;

import net.retrocarnage.editor.gameplayeditor.gui.GamePlayEditorTopComponent;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.nodes.actions.EditGamePlayAction;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of EditGamePlayAction.OpenGamePlayEditorHandler.
 *
 * This implementation will open a new MissionEditorTopComponent - if the Mission is not opened, yet.
 *
 * @author Thomas Werner
 */
@ServiceProvider(service = EditGamePlayAction.OpenGamePlayEditorHandler.class)
public class OpenMissionEditorService implements EditGamePlayAction.OpenGamePlayEditorHandler {

    @Override
    public void openGamePlayEditor(Mission mission) {
        GamePlayEditorTopComponent window = GamePlayEditorRepository.INSTANCE.findEditorForMission(mission);
        if (null == window) {
            window = new GamePlayEditorTopComponent(mission);
            window.open();
        }
        window.requestActive();
    }

}
