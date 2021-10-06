package net.retrocarnage.editor.gameplayeditor;

import java.io.IOException;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.gameplay.GamePlay;
import org.openide.cookies.SaveCookie;

/**
 * Implementation of SaveCookie that stores a given GamePlay file to disk.
 *
 * @author Thomas Werner
 */
public class SaveGamePlayAction implements SaveCookie {

    private final GamePlay gamePlay;

    SaveGamePlayAction(final GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    @Override
    public void save() throws IOException {
        MissionService.getDefault().saveGamePlay(gamePlay);
    }

}
