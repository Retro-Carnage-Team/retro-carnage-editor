package net.retrocarnage.editor.model;

import java.awt.Rectangle;

/**
 * Obstacle is something that blocks the movement of Players. Some obstacles block bullets, too.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/obstacle.go
 */
public class Obstacle implements Selectable {

    private boolean stopsBullets;
    private boolean stopsExplosives;
    private Rectangle position;

    public boolean isStopsBullets() {
        return stopsBullets;
    }

    public void setStopsBullets(boolean stopsBullets) {
        this.stopsBullets = stopsBullets;
    }

    public boolean isStopsExplosives() {
        return stopsExplosives;
    }

    public void setStopsExplosives(boolean stopsExplosives) {
        this.stopsExplosives = stopsExplosives;
    }

    @Override
    public Rectangle getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Rectangle position) {
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
