package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import net.retrocarnage.editor.gameplayeditor.gui.palette.DummyPaletteActions;
import net.retrocarnage.editor.gameplayeditor.gui.palette.GroupNodeFactory;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.*;
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
    private final LayerControllerImpl layerControllerImpl;
    private final Mission mission;
    private final SaveGamePlayAction savable;
    private final SelectionControllerImpl selectionControllerImpl;
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

        layerControllerImpl = new LayerControllerImpl(this);
        lookupContent.add(layerControllerImpl);

        selectionControllerImpl = new SelectionControllerImpl(this);
        lookupContent.add(selectionControllerImpl);
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

    void addSprite(final Sprite sprite, final Point position) {
        final Layer selectedLayer = layerControllerImpl.getSelectedLayer();
        if (selectedLayer.isLocked()) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("The selected layer is locked"));
        } else {
            final Rectangle rectangle = new Rectangle();
            rectangle.setLocation(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
            rectangle.setSize(sprite.getWidth(), sprite.getHeight());

            final VisualAsset visualAsset = new VisualAsset();
            visualAsset.setAssetId(sprite.getId());
            visualAsset.setPosition(rectangle);
            selectedLayer.getVisualAssets().add(0, visualAsset);

            requestGamePlayRepaint();
        }
    }

    void handleSelectionByClick(Point position) {
        final Selectable oldSelection = selectionControllerImpl.getSelection();
        for (Layer layer : gamePlay.getLayers()) {
            for (VisualAsset asset : layer.getVisualAssets()) {
                if (asset.getPosition().contains(position)) {
                    if (oldSelection != asset) {
                        selectionControllerImpl.setSelection(asset);
                    }
                    return;
                }
            }
        }
        if (oldSelection != null) {
            selectionControllerImpl.setSelection(null);
        }
    }

    /**
     * To be called whenever the GamePlay has to be rerendered.
     */
    void requestGamePlayRepaint() {
        propertyChangeSupport.firePropertyChange(PROPERTY_GAMEPLAY, null, gamePlay);
    }

}
