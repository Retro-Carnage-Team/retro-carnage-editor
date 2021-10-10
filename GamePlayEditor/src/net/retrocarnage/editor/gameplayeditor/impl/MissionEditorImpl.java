package net.retrocarnage.editor.gameplayeditor.impl;

import net.retrocarnage.editor.gameplayeditor.gui.GamePlayEditorTopComponent;
import net.retrocarnage.editor.missionmanager.MissionEditor;
import net.retrocarnage.editor.model.Mission;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the MissionEditor service interface.
 *
 * This implementation will open a new MissionEditorTopComponent - if the Mission is not opened, yet.
 *
 * @author Thomas Werner
 */
@ServiceProvider(service = MissionEditor.class)
public class MissionEditorImpl implements MissionEditor {

    @Override
    public void open(final Mission mission) {
        GamePlayEditorTopComponent window = GamePlayEditorRepository.INSTANCE.findEditorForMission(mission);
        if (null == window) {
            window = new GamePlayEditorTopComponent(mission);
            window.open();
        }
        window.requestActive();
    }

}
