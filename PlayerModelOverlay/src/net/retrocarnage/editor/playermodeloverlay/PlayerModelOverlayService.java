package net.retrocarnage.editor.playermodeloverlay;

import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.playermodeloverlay.impl.PlayerModelOverlayServiceImpl;

/**
 * A service that can be used to show / hide a player model for size comparison.
 *
 * @author Thomas Werner
 */
public abstract class PlayerModelOverlayService {

    public static final String PROPERTY_VISIBILITY = "player_model_overlay_visibility";

    private static final PlayerModelOverlayService playerModelOverlayServiceImpl = new PlayerModelOverlayServiceImpl();

    public abstract void addPropertyChangeListener(final PropertyChangeListener listener);

    public abstract void removePropertyChangeListener(final PropertyChangeListener listener);

    /**
     * @return the zoom level in percent
     */
    public abstract boolean isPlayerModelOverlayVisible();

    /**
     * @return an instance of this service
     */
    public static PlayerModelOverlayService getDefault() {
        return playerModelOverlayServiceImpl;
    }

}
