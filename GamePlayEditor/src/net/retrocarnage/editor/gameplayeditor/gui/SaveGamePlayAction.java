package net.retrocarnage.editor.gameplayeditor.gui;

import java.io.IOException;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.gameplay.GamePlay;
import org.netbeans.spi.actions.AbstractSavable;

/**
 * Implementation of Savable that stores a given GamePlay file to disk.
 *
 * @author Thomas Werner
 */
public class SaveGamePlayAction extends AbstractSavable {

    private final String name;
    private final GamePlay gamePlay;

    SaveGamePlayAction(final GamePlay gamePlay, final String name) {
        this.gamePlay = gamePlay;
        this.name = name;
        register();
    }

    @Override
    protected String findDisplayName() {
        return name;
    }

    @Override
    protected void handleSave() throws IOException {
        MissionService.getDefault().saveGamePlay(gamePlay);
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof SaveGamePlayAction) && ((SaveGamePlayAction) other).gamePlay.equals(gamePlay);
    }

    @Override
    public int hashCode() {
        return gamePlay.hashCode();
    }

    void close() {
        super.unregister();
    }

}
