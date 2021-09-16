
package net.retrocarnage.editor.model;

/**
 * EnemyMovement is one of a series of movements of an Enemy.
 * 
 * @author Thomas Werner
 */
public class EnemyMovement {
    
    private long duration;
    private double offsetXPerMs;
    private double offsetYPerMs;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getOffsetXPerMs() {
        return offsetXPerMs;
    }

    public void setOffsetXPerMs(double offsetXPerMs) {
        this.offsetXPerMs = offsetXPerMs;
    }

    public double getOffsetYPerMs() {
        return offsetYPerMs;
    }

    public void setOffsetYPerMs(double offsetYPerMs) {
        this.offsetYPerMs = offsetYPerMs;
    }    
    
}
