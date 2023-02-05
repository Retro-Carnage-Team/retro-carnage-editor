package net.retrocarnage.editor.model;

/**
 * EnemyMovement is one of a series of movements of an Enemy.
 *
 * @author Thomas Werner
 */
public class EnemyMovement {

    private int distanceX;
    private int distanceY;    
    
    public int getDistanceX() {
        return distanceX;
    }

    public void setDistanceX(final int distanceX) {
        this.distanceX = distanceX;
    }

    public int getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(final int distanceY) {
        this.distanceY = distanceY;
    }

    public void add(final EnemyMovement other) {
        setDistanceX(getDistanceX() + other.getDistanceX());
        setDistanceY(getDistanceY() + other.getDistanceY());
    }
    
}
