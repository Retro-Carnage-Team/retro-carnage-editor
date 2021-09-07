
package net.retrocarnage.editor.model;

/**
 * Obstacle is something that blocks the movement of Players. Some obstacles block bullets, too.
 * 
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/obstacle.go
 */
public class Obstacle extends Rectangle {

    private boolean stopsBullets;
    private boolean stopsExplosives;

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
    
}
