package net.retrocarnage.editor.gameplayeditor.gui;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Optional;
import net.retrocarnage.editor.gameplayeditor.gui.palette.DummyPaletteActions;
import net.retrocarnage.editor.gameplayeditor.gui.palette.GroupNodeFactory;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Goal;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Obstacle;
import net.retrocarnage.editor.model.Position;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;
import net.retrocarnage.editor.zoom.ZoomService;
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

    private Operation operation = Operation.NONE;
    private int offsetTop = 0;
    private int offsetLeft = 0;
    private int offsetBottom = 0;
    private int offsetRight = 0;

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
            propertyChangeSupport.firePropertyChange(PROPERTY_GAMEPLAY, null, gamePlay);
            lookupContent.add(savable);
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

    void addEnemy(final Enemy enemy, final Point position) {
        final Layer selectedLayer = layerControllerImpl.getSelectedLayer();
        if (selectedLayer.isLocked()) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("The selected layer is locked"));
        } else {
            final Point scaledPosition = scalePosition(position);
            final Position newPosition = new Position();
            newPosition.setX(scaledPosition.x - (enemy.getPosition().getWidth() / 2));
            newPosition.setY(scaledPosition.y - (enemy.getPosition().getHeight() / 2));
            newPosition.setWidth(enemy.getPosition().getWidth());
            newPosition.setHeight(enemy.getPosition().getHeight());
            enemy.setPosition(newPosition);

            selectedLayer.getEnemies().add(0, enemy);
            requestGamePlayRepaint();
        }
    }

    void addObstacle(final Obstacle obstacle, final Point position) {
        final Layer selectedLayer = layerControllerImpl.getSelectedLayer();
        if (selectedLayer.isLocked()) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("The selected layer is locked"));
        } else {
            final Point scaledPosition = scalePosition(position);
            final Position targetPosition = new Position();
            targetPosition.setX(scaledPosition.x - 50);
            targetPosition.setY(scaledPosition.y - 50);
            targetPosition.setWidth(100);
            targetPosition.setHeight(100);

            final Obstacle newObstacle = new Obstacle();
            newObstacle.setPosition(targetPosition);
            newObstacle.setBulletStopper(obstacle.isBulletStopper());
            newObstacle.setExplosiveStopper(obstacle.isExplosiveStopper());
            selectedLayer.getObstacles().add(0, newObstacle);

            requestGamePlayRepaint();
        }
    }

    void addSprite(final Sprite sprite, final Point position) {
        final Layer selectedLayer = layerControllerImpl.getSelectedLayer();
        if (selectedLayer.isLocked()) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("The selected layer is locked"));
        } else {
            final Point scaledPosition = scalePosition(position);
            final Position rectangle = new Position();
            rectangle.setX(scaledPosition.x - (sprite.getWidth() / 2));
            rectangle.setY(scaledPosition.y - (sprite.getHeight() / 2));
            rectangle.setWidth(sprite.getWidth());
            rectangle.setHeight(sprite.getHeight());

            final VisualAsset visualAsset = new VisualAsset();
            visualAsset.setAssetId(sprite.getId());
            visualAsset.setPosition(rectangle);
            selectedLayer.getVisualAssets().add(0, visualAsset);

            requestGamePlayRepaint();
        }
    }

    void setGoal(final Goal goal, final Point dropLocation) {
        final Layer selectedLayer = layerControllerImpl.getSelectedLayer();
        if (selectedLayer.isLocked()) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("The selected layer is locked"));
        } else {
            for (Layer layer : layerControllerImpl.getLayers()) {
                if (null != layer.getGoal()) {
                    if (layer.isLocked()) {
                        final String message = "Cannot replace existing goal from locked layer";
                        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(message));
                        return;
                    }
                    layer.setGoal(null);
                }
            }

            final Point scaledPosition = scalePosition(dropLocation);
            final Position rectangle = new Position();
            rectangle.setX(scaledPosition.x - 50);
            rectangle.setY(scaledPosition.y - 50);
            rectangle.setWidth(100);
            rectangle.setHeight(100);
            goal.setPosition(rectangle);
            selectedLayer.setGoal(goal);

            requestGamePlayRepaint();
        }
    }

    void removeSelectedElement() {
        final Selectable selection = selectionControllerImpl.getSelection();
        if (null != selection) {
            for (Layer layer : layerControllerImpl.getLayers()) {
                if (!layer.isLocked()) {
                    if ((selection instanceof VisualAsset) && layer.getVisualAssets().remove(selection)) {
                        selectionControllerImpl.setSelection(null);
                        break;
                    } else if ((selection instanceof Obstacle) && layer.getObstacles().remove(selection)) {
                        selectionControllerImpl.setSelection(null);
                        break;
                    } else if (selection instanceof Goal) {
                        layer.setGoal(null);
                        selectionControllerImpl.setSelection(null);
                        break;
                    }
                }
            }
        }
    }

    void handleMouseClick(final Point position) {
        final Point scaledPosition = scalePosition(position);
        final Selectable oldSelection = selectionControllerImpl.getSelection();        
        selectionControllerImpl.pointSelected(scaledPosition);        
        for (Layer layer : gamePlay.getLayers()) {
            final Optional<Selectable> possibleItem = layer
                    .streamSelectables()
                    .filter((b) -> b.getPosition().toRectangle().contains(scaledPosition))
                    .findFirst();

            if (possibleItem.isPresent() && oldSelection != possibleItem.get()) {
                selectionControllerImpl.setSelection(possibleItem.get());
                return;
            }
        }
        if (oldSelection != null) {
            selectionControllerImpl.setSelection(null);
        }
    }

    void handleMousePressed(final Point position) {
        final Selectable selection = selectionControllerImpl.getSelection();
        if (null == selection) {
            return;
        }

        final SelectionMousePositionAnalyzer smia = new SelectionMousePositionAnalyzer(selection, position);
        if (!smia.isMouseInSelection()) {
            return;
        }

        offsetTop = smia.getOffsetTop();
        offsetLeft = smia.getOffsetLeft();
        offsetBottom = smia.getOffsetBottom();
        offsetRight = smia.getOffsetRight();

        if (selection.isResizable()) {
            if (smia.isMouseInTopResizeArea() && smia.isMouseInLeftResizeArea()) {
                operation = Operation.RESIZE_NW;
            } else if (smia.isMouseInTopResizeArea() && smia.isMouseInRightResizeArea()) {
                operation = Operation.RESIZE_NE;
            } else if (smia.isMouseInBottomResizeArea() && smia.isMouseInLeftResizeArea()) {
                operation = Operation.RESIZE_SW;
            } else if (smia.isMouseInBottomResizeArea() && smia.isMouseInRightResizeArea()) {
                operation = Operation.RESIZE_SE;
            } else if (smia.isMouseInTopResizeArea()) {
                operation = Operation.RESIZE_N;
            } else if (smia.isMouseInLeftResizeArea()) {
                operation = Operation.RESIZE_W;
            } else if (smia.isMouseInRightResizeArea()) {
                operation = Operation.RESIZE_E;
            } else if (smia.isMouseInBottomResizeArea()) {
                operation = Operation.RESIZE_S;
            } else {
                operation = Operation.MOVE;
            }
        } else if (selection.isMovable()) {
            operation = Operation.MOVE;
        }
    }

    void handleMouseReleased(final Point position) {
        operation = Operation.NONE;
    }

    void handleMouseDragged(final Point position) {
        final Selectable selection = selectionControllerImpl.getSelection();
        if ((null == selection) || (Operation.NONE == operation)) {
            return;
        }

        final SelectionMousePositionAnalyzer smia = new SelectionMousePositionAnalyzer(selection, position);
        final int deltaLeft = smia.getOffsetLeft() - offsetLeft;
        final int deltaRight = smia.getOffsetRight() - offsetRight;
        final int deltaTop = smia.getOffsetTop() - offsetTop;
        final int deltaBottom = smia.getOffsetBottom() - offsetBottom;
        switch (operation) {
            case MOVE:
                if (smia.isMouseInSelection()) {
                    selectionControllerImpl.moveSelectedElement(deltaLeft, deltaTop);
                }
                break;
            case RESIZE_N:
                selectionControllerImpl.resizeSelectedElement(0, deltaTop, 0, -1 * deltaTop);
                break;
            case RESIZE_NW:
                selectionControllerImpl.resizeSelectedElement(deltaLeft, deltaTop, -1 * deltaLeft, -1 * deltaTop);
                break;
            case RESIZE_NE:
                selectionControllerImpl.resizeSelectedElement(0, deltaTop, -1 * deltaRight, -1 * deltaTop);
                break;
            case RESIZE_W:
                selectionControllerImpl.resizeSelectedElement(deltaLeft, 0, -1 * deltaLeft, 0);
                break;
            case RESIZE_E:
                selectionControllerImpl.resizeSelectedElement(0, 0, -1 * deltaRight, 0);
                break;
            case RESIZE_S:
                selectionControllerImpl.resizeSelectedElement(0, 0, 0, -1 * deltaBottom);
                break;
            case RESIZE_SW:
                selectionControllerImpl.resizeSelectedElement(deltaLeft, 0, -1 * deltaLeft, -1 * deltaBottom);
                break;
            case RESIZE_SE:
                selectionControllerImpl.resizeSelectedElement(0, 0, -1 * deltaRight, -1 * deltaBottom);
                break;
            default:
                break;
        }
        requestGamePlayRepaint();
    }

    void handleMouseExited() {
        operation = Operation.NONE;
    }

    /**
     * To be called whenever the GamePlay has to be rerendered.
     */
    void requestGamePlayRepaint() {
        propertyChangeSupport.firePropertyChange(PROPERTY_GAMEPLAY, null, gamePlay);
    }

    private Point scalePosition(final Point position) {
        final float factor = 1f / (ZoomService.getDefault().getZoomLevel() / 100f);
        return new Point((int) (position.x * factor), (int) (position.y * factor));
    }

}
