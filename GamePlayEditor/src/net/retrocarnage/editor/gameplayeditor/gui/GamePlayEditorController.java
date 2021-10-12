package net.retrocarnage.editor.gameplayeditor.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

    static final String PROPERTY_GAMEPLAY = "gamePlay";

    private final InstanceContent lookupContent;
    private final GamePlay gamePlay;
    private final Mission mission;
    private final SaveCookie saveCookie;
    private final PropertyChangeListener gamePlayChangeListener;
    private final PropertyChangeSupport propertyChangeSupport;

    GamePlayEditorController(final Mission mission) {
        this.mission = mission;
        gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());

        propertyChangeSupport = new PropertyChangeSupport(this);
        gamePlayChangeListener = (final PropertyChangeEvent pce) -> {
            if (GamePlay.PROPERTY_UNKNOWN.equals(pce.getPropertyName())) {
                propertyChangeSupport.firePropertyChange(PROPERTY_GAMEPLAY, null, gamePlay);
            }
        };
        gamePlay.addPropertyChangeListener(gamePlayChangeListener);

        saveCookie = new SaveGamePlayAction(gamePlay);

        lookupContent = new InstanceContent();
        lookupContent.add(gamePlay);

        // TODO: Place save cookie in lookupContent whenever the gameplay data changed
        lookupContent.add(saveCookie);
    }

    void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
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

    void close() {
        gamePlay.removePropertyChangeListener(gamePlayChangeListener);
    }

}
