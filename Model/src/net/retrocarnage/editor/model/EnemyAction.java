package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * EnemyAction is something that an Enemy does.
 *
 * Each Enemy has a list of these actions. Elements of this list of EnemyActions are repeated in an endless loop - until
 * the enemy leaves the screen or dies.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/enemy-action.go
 */
@JsonPropertyOrder({"delay", "action"})
public class EnemyAction {

    private String action;
    private long delay;

    public EnemyAction() {}
    
    /**
     * Copy constructor. Creates a copy of the given EnemyAction
     * 
     * @param other EnemyAction to be copied
     */
    public EnemyAction(final EnemyAction other) {
        action = other.action;
        delay = other.delay;
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

}
