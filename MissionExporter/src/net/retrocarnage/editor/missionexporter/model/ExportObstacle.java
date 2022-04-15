package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.awt.Rectangle;
import net.retrocarnage.editor.model.Blocker;

/**
 * Generates JSON output for an obstacle, applies naming and formatting.
 *
 * @author Thomas Werner
 */
@JsonPropertyOrder({"x", "y", "width", "height", "stopsBullets", "stopsExplosives"})
public class ExportObstacle {

    private final Blocker blocker;
    private final Rectangle sectionRect;

    public ExportObstacle(final Blocker blocker, final Rectangle sectionRect) {
        this.blocker = blocker;
        this.sectionRect = sectionRect;
    }

    public int getX() {
        return blocker.getPosition().getX() - sectionRect.x;
    }

    public int getY() {
        return blocker.getPosition().getY() - sectionRect.y;
    }

    public int getWidth() {
        return blocker.getPosition().getWidth();
    }

    public int getHeight() {
        return blocker.getPosition().getHeight();
    }

    public boolean isStopsBullets() {
        return blocker.isBulletStopper();
    }

    public boolean isStopsExplosives() {
        return blocker.isExplosiveStopper();
    }

}
