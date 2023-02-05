package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.retrocarnage.editor.model.EnemyMovement;

/**
 * A wrapper for the EnemyMovement entity.
 * 
 * Enemy movements in the editor are specified as total distance on X and Y axis. The game instead uses duration and 
 * movement distance (X and Y) per MS. The export class converts betweens these two models.
 * 
 * @author Thomas Werner
 */
@JsonPropertyOrder({"duration", "offsetXPerMs", "offsetYPerMs"})
public class ExportEnemyMovement {

    private long duration;
    private double offsetXPerMs;
    private double offsetYPerMs;

    ExportEnemyMovement(final EnemyMovement em, final int speed) {
        double emDistance = Math.sqrt(em.getDistanceX() * em.getDistanceX() + em.getDistanceY() * em.getDistanceY());
        double exactDuration = emDistance / ((double)speed) * 1_000.0;
        offsetXPerMs = em.getDistanceX() / exactDuration;
        offsetYPerMs = em.getDistanceY() / exactDuration;
        duration = (long) exactDuration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }

    public double getOffsetXPerMs() {
        return offsetXPerMs;
    }

    public void setOffsetXPerMs(final double offsetXPerMs) {
        this.offsetXPerMs = offsetXPerMs;
    }

    public double getOffsetYPerMs() {
        return offsetYPerMs;
    }

    public void setOffsetYPerMs(final double offsetYPerMs) {
        this.offsetYPerMs = offsetYPerMs;
    }

}
