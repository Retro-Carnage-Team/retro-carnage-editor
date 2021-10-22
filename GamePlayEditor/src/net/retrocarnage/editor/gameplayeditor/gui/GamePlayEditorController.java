package net.retrocarnage.editor.gameplayeditor.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import net.retrocarnage.editor.gameplayeditor.gui.palette.DummyPaletteActions;
import net.retrocarnage.editor.gameplayeditor.gui.palette.GroupNodeFactory;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Mission;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
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
    private final SaveGamePlayAction savable;
    private final PropertyChangeListener gamePlayChangeListener;
    private final PropertyChangeSupport propertyChangeSupport;

    GamePlayEditorController(final Mission mission) {
        this.mission = mission;
        gamePlay = MissionService.getDefault().loadGamePlay(mission.getId());

        lookupContent = new InstanceContent();
        savable = new SaveGamePlayAction(gamePlay, mission.getName()) {
            @Override
            protected void handleSave() throws IOException {
                super.handleSave();
                lookupContent.remove(this);
            }
        };

        propertyChangeSupport = new PropertyChangeSupport(this);
        gamePlayChangeListener = (final PropertyChangeEvent pce) -> {
            if (GamePlay.PROPERTY_UNKNOWN.equals(pce.getPropertyName())) {
                propertyChangeSupport.firePropertyChange(PROPERTY_GAMEPLAY, null, gamePlay);
                lookupContent.add(savable);
            }
        };
        gamePlay.addPropertyChangeListener(gamePlayChangeListener);
        lookupContent.add(gamePlay);

        final Node paletteRoot = new AbstractNode(Children.create(new GroupNodeFactory(), false));
        final PaletteActions paletteActions = new DummyPaletteActions();
        final PaletteController paletteController = PaletteFactory.createPalette(paletteRoot, paletteActions);

        lookupContent.add(paletteController);
    }

    void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    GamePlay getGamePlay() {
        return gamePlay;
    }

    Mission getMission() {
        return mission;
    }

    InstanceContent getLookupContent() {
        return lookupContent;
    }

    void close() {
        gamePlay.removePropertyChangeListener(gamePlayChangeListener);
        savable.close();
    }

}
