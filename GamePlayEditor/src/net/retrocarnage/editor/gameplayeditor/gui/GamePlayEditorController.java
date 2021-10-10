package net.retrocarnage.editor.gameplayeditor.gui;

import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.gameplay.GamePlay;
import org.openide.cookies.SaveCookie;
import org.openide.util.lookup.InstanceContent;

/**
 * A controller class for the GamePlayEditorTopComponent.
 *
 * @author Thomas Werner
 */
class GamePlayEditorController {

    private final InstanceContent lookupContent;
    private final GamePlay gamePlay;
    private final Mission mission;
    private final SaveCookie saveCookie;

    GamePlayEditorController(final Mission mission) {
        this.mission = mission;
        this.gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());
        this.saveCookie = new SaveGamePlayAction(gamePlay);

        lookupContent = new InstanceContent();
        lookupContent.add(gamePlay);

        // TODO: Place save cookie in lookupContent whenever the gameplay data changed
        lookupContent.add(saveCookie);
    }

    public GamePlay getGamePlay() {
        return gamePlay;
    }

    public Mission getMission() {
        return mission;
    }

    public InstanceContent getLookupContent() {
        return lookupContent;
    }

}
