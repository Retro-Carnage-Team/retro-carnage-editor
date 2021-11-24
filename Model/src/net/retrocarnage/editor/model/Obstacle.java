package net.retrocarnage.editor.model;

/**
 * Obstacle is something that blocks the movement of Players. Some obstacles block bullets, too.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/obstacle.go
 */
public class Obstacle implements Blocker, Selectable {

    private boolean stopsBullets;
    private boolean stopsExplosives;
    private Position position;

    @Override
    public boolean isBulletStopper() {
        return stopsBullets;
    }

    @Override
    public void setBulletStopper(final boolean stopsBullets) {
        this.stopsBullets = stopsBullets;
    }

    @Override
    public boolean isExplosiveStopper() {
        return stopsExplosives;
    }

    @Override
    public void setExplosiveStopper(final boolean stopsExplosives) {
        this.stopsExplosives = stopsExplosives;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public void setObstacle(boolean obstacle) {
        // Obstacles are obstacles, stupid!
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Position position) {
        this.position = position;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

}
