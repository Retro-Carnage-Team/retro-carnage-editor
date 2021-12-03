package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A visual asset.
 *
 * @author Thomas Werner
 */
public class VisualAsset implements Blocker {

    private String assetId;
    private boolean obstacle;
    private Position position;
    private boolean stopsBullets;
    private boolean stopsExplosives;

    @Override
    public VisualAsset clone() {
        final VisualAsset clone = new VisualAsset();
        clone.setAssetId(assetId);
        clone.setPosition(position.clone());
        return clone;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(final String assetId) {
        this.assetId = assetId;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Position position) {
        this.position = position;
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
        this.stopsBullets = stopsBullets;
    }

    @Override
    public boolean isExplosiveStopper() {
        return stopsExplosives;
    }

    @Override
    public void setExplosiveStopper(final boolean stopsExplosives) {
        this.stopsExplosives = stopsExplosives;
    }

    @Override
    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public void setObstacle(final boolean obstacle) {
        this.obstacle = obstacle;
    }

}
