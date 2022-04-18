package net.retrocarnage.editor.model;

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

    private boolean locked;
    private String name;
    private boolean visible;
    private Goal goal;
    private final ObservableList<Enemy> enemies;
    private final ObservableList<Obstacle> obstacles;
    private final ObservableList<VisualAsset> visualAssets;

    public Layer() {
        enemies = new ObservableList<>();
        obstacles = new ObservableList<>();
        visualAssets = new ObservableList<>();
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(final Goal goal) {
        this.goal = goal;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
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

}
