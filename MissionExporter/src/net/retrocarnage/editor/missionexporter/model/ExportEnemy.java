package net.retrocarnage.editor.missionexporter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemyAction;

/**
 * A wrapper for the Enemy entity used to apply correct naming and formatting.
 *
 * @author Thomas Werner
 */
@JsonPropertyOrder({"actions", "movements", "direction", "position", "skin", "type"})
public class ExportEnemy {

    private final Enemy enemy;
    private final Rectangle sectionRect;

    public ExportEnemy(final Enemy enemy, final Rectangle sectionRect) {
        this.enemy = enemy;
        this.sectionRect = sectionRect;
    }

    public List<EnemyAction> getActions() {
        return enemy.getActions();
    }

    public String getDirection() {
        return enemy.getDirection();
    }

    public List<ExportEnemyMovement> getMovements() {
        return enemy.getMovements()
                .stream()
                // TODO: This is not done, yet. ExportEnemyMovement has to convert EnemyMovement data.
                .map(em -> new ExportEnemyMovement())
                .collect(Collectors.toList());
    }

    public ExportEnemyPosition getPosition() {
        return new ExportEnemyPosition();
    }

    public String getSkin() {
        return enemy.getSkin();
    }

    public int getType() {
        return enemy.getType();
    }

    @JsonPropertyOrder({"x", "y", "width", "height"})
    public class ExportEnemyPosition {

        public int getX() {
            return enemy.getPosition().getX() - sectionRect.x;
        }

        public int getY() {
            return enemy.getPosition().getY() - sectionRect.y;
        }

        public int getWidth() {
            return enemy.getPosition().getWidth();
        }

        public int getHeight() {
            return enemy.getPosition().getHeight();
        }
    }

}
