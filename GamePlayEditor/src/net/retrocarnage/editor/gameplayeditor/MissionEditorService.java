package net.retrocarnage.editor.gameplayeditor;

import net.retrocarnage.editor.missionmanager.MissionEditor;
import net.retrocarnage.editor.model.Mission;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the MissionEditor service interface. This implementation will always open a new
 * MissionEditorTopComponent.
 *
 * TODO: Keep list of opened editors. If an editor for the given mission exists, pull it to foreground.
 *
 * @author Thomas Werner
 */
@ServiceProvider(service = MissionEditor.class)
public class MissionEditorService implements MissionEditor {

    @Override
    public void open(final Mission mission) {
        GamePlayEditorTopComponent window = new GamePlayEditorTopComponent();
        window.setMission(mission);
        window.open();
        window.requestActive();
    }

}
