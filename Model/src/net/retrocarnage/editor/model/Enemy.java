package net.retrocarnage.editor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enemy is the serialized definition of an enemy.
 *
 * @author Thomas Werner
 */
public class Enemy implements Selectable {

    private double activationDistance;
    private List<EnemyMovement> movements;
    private String direction;
    private Position position;
    private String skin;
    private int type;
    private List<EnemyAction> actions;

    public Enemy() {
        this.movements = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public double getActivationDistance() {
        return activationDistance;
    }

    public void setActivationDistance(final double activationDistance) {
        this.activationDistance = activationDistance;
    }

    public List<EnemyMovement> getMovements() {
        return movements;
    }

    public void setMovements(final List<EnemyMovement> movements) {
        this.movements = movements;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Position position) {
        this.position = position;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(final String skin) {
        this.skin = skin;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public List<EnemyAction> getActions() {
        return actions;
    }

    public void setActions(final List<EnemyAction> actions) {
        this.actions = actions;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isResizable() {
        return false;
    }

}
