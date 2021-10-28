package net.retrocarnage.editor.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Enemy is the serialized definition of an enemy.
 *
 * @author Thomas Werner
 */
public class Enemy {

    private double activationDistance;
    private List<EnemyMovement> movements;
    private String direction;
    private Rectangle position;
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

    public void setActivationDistance(double activationDistance) {
        this.activationDistance = activationDistance;
    }

    public List<EnemyMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<EnemyMovement> movements) {
        this.movements = movements;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<EnemyAction> getActions() {
        return actions;
    }

    public void setActions(List<EnemyAction> actions) {
        this.actions = actions;
    }

}
