package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author Thomas Werner
 */
@JsonPropertyOrder({"duration", "offsetXPerMs", "offsetYPerMs"})
public class ExportEnemyMovement {

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
