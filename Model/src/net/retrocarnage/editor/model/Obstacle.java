package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * An Obstacle is something that blocks the movement of Players. Some obstacles block bullets, too.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/obstacle.go
 */
public final class Obstacle implements Blocker, Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(Obstacle.class, "obstacle");

    private final PropertyChangeSupport propertyChangeSupport;

    private boolean stopsBullets;
    private boolean stopsExplosives;
    private Position position;

    public Obstacle() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Copy constructor. The new instance will be initialized with the values of the given Obstacle. 
     * ChangeListeners will not be registered with the new instance.
     * 
     * @param other Obstacle to be copied
     */
    public Obstacle(final Obstacle other) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        
        stopsBullets = other.stopsBullets;
        stopsExplosives = other.stopsExplosives;
        position = new Position(other.position);
    }

    @Override
    public boolean isBulletStopper() {
        return stopsBullets;
    }

    @Override
    public void setBulletStopper(final boolean stopsBullets) {
        final boolean old = this.stopsBullets;
        this.stopsBullets = stopsBullets;
        propertyChangeSupport.firePropertyChange(PROPERTY_BULLETSTOPPER, old, stopsBullets);
    }

    @Override
    public boolean isExplosiveStopper() {
        return stopsExplosives;
    }

    @Override
    public void setExplosiveStopper(final boolean stopsExplosives) {
        final boolean old = this.stopsExplosives;
        this.stopsExplosives = stopsExplosives;
        propertyChangeSupport.firePropertyChange(PROPERTY_EXPLOSIVESTOPPER, old, stopsExplosives);
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public void setObstacle(boolean obstacle) {
        // Obstacles are obstacles, stupid!
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

    @JsonIgnore
    @Override
    public boolean isMovable() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isResizable() {
        return true;
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
