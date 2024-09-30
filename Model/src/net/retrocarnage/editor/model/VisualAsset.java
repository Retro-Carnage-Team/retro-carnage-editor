package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A visual asset.
 *
 * @author Thomas Werner
 */
public final class VisualAsset implements Blocker {

    public static final String PROPERTY_ASSETID = "assetId";
    public static final String PROPERTY_ROTATION = "rotation";

    private final PropertyChangeSupport propertyChangeSupport;

    private String assetId;
    private boolean obstacle;
    private Position position;
    private Rotation rotation = Rotation.None;
    private boolean stopsBullets;
    private boolean stopsExplosives;

    public VisualAsset() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(final String assetId) {
        final String old = assetId;
        this.assetId = assetId;
        propertyChangeSupport.firePropertyChange(PROPERTY_ASSETID, old, assetId);
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
        return obstacle;
    }

    @Override
    public void setObstacle(final boolean obstacle) {
        final boolean old = this.obstacle;
        this.obstacle = obstacle;
        propertyChangeSupport.firePropertyChange(PROPERTY_OBSTACLE, old, obstacle);
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(final Rotation rotation) {
        final Rotation old = this.rotation;
        this.rotation = rotation;
        propertyChangeSupport.firePropertyChange(PROPERTY_ROTATION, old, rotation);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public VisualAsset clone() {
        final VisualAsset clone = new VisualAsset();
        clone.setAssetId(assetId);
        clone.setPosition(position.clone());
        clone.setRotation(rotation);
        return clone;
    }

}
