package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Enemy is the serialized definition of an enemy.
 *
 * @author Thomas Werner
 */
public final class Enemy implements Selectable, Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(Enemy.class, "enemy");
    public static final int LANDMINE_HEIGHT = 44;
    public static final int LANDMINE_WIDTH = 50;
    public static final int PERSON_HEIGHT = 150;
    public static final int PERSON_WIDTH = 90;
    public static final String PROPERTY_DIRECTION = "Direction";
    public static final String PROPERTY_SKIN = "Skin";
    public static final String PROPERTY_SPEED = "Speed";
    public static final String PROPERTY_TYPE = "Type";    

    private final PropertyChangeSupport propertyChangeSupport;
    private List<EnemyMovement> movements;
    private String direction;
    private Position position;
    private String skin;
    private int speed;                                                                                                  // In pixels per μs (1s / 1_000_000)
    private int type;
    private List<EnemyAction> actions;

    public Enemy() {
        this.movements = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Copy constructor. Creates a copy of the given Enemy.
     * ChangeListeners will not be registered with the new instance.
     * 
     * @param other Enemy object to be copied
     */
    public Enemy(final Enemy other) {
        this.movements = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
                
        this.direction = other.direction;
        this.position = new Position(other.position);
        this.skin = other.skin;
        this.speed = other.speed;
        this.type = other.type;

        for(EnemyAction a: other.actions) {
            actions.add(new EnemyAction(a));
        }
        
        for(EnemyMovement m: other.movements) {
            movements.add(new EnemyMovement(m));
        }
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
        final String old = this.direction;
        this.direction = direction;
        propertyChangeSupport.firePropertyChange(PROPERTY_DIRECTION, old, direction);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Position position) {
        final Position old = this.position;
        this.position = position;
        propertyChangeSupport.firePropertyChange(PROPERTY_POSITION, old, position);
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(final String skin) {
        final String old = this.skin;
        this.skin = skin;
        propertyChangeSupport.firePropertyChange(PROPERTY_SKIN, old, skin);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        final int old = this.speed;
        this.speed = speed;
        propertyChangeSupport.firePropertyChange(PROPERTY_SPEED, old, speed);
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        final int old = this.type;
        this.type = type;
        propertyChangeSupport.firePropertyChange(PROPERTY_TYPE, old, type);
    }

    public List<EnemyAction> getActions() {
        return actions;
    }

    public void setActions(final List<EnemyAction> actions) {
        this.actions = actions;
    }

    @JsonIgnore
    @Override
    public boolean isMovable() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isResizable() {
        return false;
    }

    @JsonIgnore
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(final DataFlavor df) {
        return DATA_FLAVOR.equals(df);
    }

    @Override
    public Object getTransferData(final DataFlavor df) throws UnsupportedFlavorException, IOException {
        if (DATA_FLAVOR.equals(df)) {
            return this;
        }
        throw new UnsupportedFlavorException(df);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
