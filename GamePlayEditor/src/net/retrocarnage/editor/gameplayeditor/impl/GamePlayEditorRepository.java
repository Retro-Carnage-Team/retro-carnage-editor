package net.retrocarnage.editor.gameplayeditor.impl;

import java.util.HashMap;
import java.util.Map;
import net.retrocarnage.editor.gameplayeditor.gui.GamePlayEditorTopComponent;
import net.retrocarnage.editor.model.Mission;

/**
 * This repository contains all open GamePlayEditors.
 *
 * @author Thomas Werner
 */
public enum GamePlayEditorRepository {

    INSTANCE;

    private final Map<String, GamePlayEditorTopComponent> missionIdToEditorWindow;

    private GamePlayEditorRepository() {
        missionIdToEditorWindow = new HashMap<>();
    }

    /**
     * Used by GamePlayEditorTopComponents to register themselfs for a specific mission.
     *
     * @param missionId the ID of the Mission
     * @param editor the GamePlayEditorTopComponents used to work on the Mission
     */
    public void register(final String missionId, final GamePlayEditorTopComponent editor) {
        missionIdToEditorWindow.put(missionId, editor);
    }

    /**
     * Used by GamePlayEditorTopComponents to unregister themselfs for a specific mission.
     *
     * @param missionId the ID of the Mission
     * @param editor the GamePlayEditorTopComponents used to work on the Mission
     */
    public void unregister(final String missionId, final GamePlayEditorTopComponent editor) {
        missionIdToEditorWindow.remove(missionId, editor);
    }

    /**
     * Can be used to check of there is a GamePlayEditorTopComponents opened for a specific Mission.
     *
     * @param mission the Mission
     * @return the GamePlayEditorTopComponents used to work on the Mission - if any
     */
    public GamePlayEditorTopComponent findEditorForMission(final Mission mission) {
        if (null == mission) {
            return null;
        }

        return missionIdToEditorWindow.get(mission.getId());
    }

}
