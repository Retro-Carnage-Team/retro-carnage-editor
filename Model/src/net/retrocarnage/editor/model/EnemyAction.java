package net.retrocarnage.editor.model;

/**
 * EnemyAction is something that an Enemy does.
 *
 * Each Enemy has a list of these actions. Elements of this list of EnemyActions are repeated in an endless loop - until
 * the enemy leaves the screen or dies.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/enemy-action.go
 */
public class EnemyAction {

    private String action;
    private long delay;

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
