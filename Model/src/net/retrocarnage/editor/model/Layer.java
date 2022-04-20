package net.retrocarnage.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.stream.Stream;

/**
 * Layers are used to organize assets.
 *
 * This makes it easer to work on different aspects of the mission one at a time.
 *
 * @author Thomas Werner
 */
public class Layer {

    public static final String DEFAULT_LAYER_NAME = "Default";
    public static final String PROPERTY_GOAL = "goal";
    public static final String PROPERTY_LOCKED = "locked";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_VISIBLE = "visible";

    private final PropertyChangeSupport propertyChangeSupport;
    private boolean locked;
    private String name;
    private boolean visible;
    private Goal goal;
    private final ObservableList<Enemy> enemies;
    private final ObservableList<Obstacle> obstacles;
    private final ObservableList<VisualAsset> visualAssets;

    public Layer() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        enemies = new ObservableList<>();
        obstacles = new ObservableList<>();
        visualAssets = new ObservableList<>();
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(final Goal goal) {
        final Goal old = this.goal;
        this.goal = goal;
        propertyChangeSupport.firePropertyChange(PROPERTY_GOAL, old, goal);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(final boolean locked) {
        final boolean old = this.locked;
        this.locked = locked;
        propertyChangeSupport.firePropertyChange(PROPERTY_LOCKED, old, locked);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        final String old = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange(PROPERTY_NAME, old, name);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        final boolean old = this.visible;
        this.visible = visible;
        propertyChangeSupport.firePropertyChange(PROPERTY_VISIBLE, old, visible);
    }

    public ObservableList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(final List<Enemy> enemies) {
        this.enemies.clear();
        this.enemies.addAll(enemies);
    }

    public ObservableList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(final List<Obstacle> obstacles) {
        this.obstacles.clear();
        this.obstacles.addAll(obstacles);
    }

    public ObservableList<VisualAsset> getVisualAssets() {
        return visualAssets;
    }

    public void setVisualAssets(final List<VisualAsset> visualAssets) {
        this.visualAssets.clear();
        this.visualAssets.addAll(visualAssets);
    }

    public Stream<Selectable> streamSelectables() {
        Stream<Selectable> result = Stream.concat(getVisualAssets().stream(), getObstacles().stream());
        result = Stream.concat(getEnemies().stream(), result);
        if (null != goal) {
            result = Stream.concat(result, Stream.of(goal));
        }
        return result;
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
