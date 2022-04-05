package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Obstacle is something that blocks the movement of Players. Some obstacles block bullets, too.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/obstacle.go
 */
public class Obstacle implements Blocker, Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(Obstacle.class, "obstacle");

    private boolean stopsBullets;
    private boolean stopsExplosives;
    private Position position;

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

}
