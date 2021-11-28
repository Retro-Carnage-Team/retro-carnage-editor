package net.retrocarnage.editor.playermodeloverlay.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import net.retrocarnage.editor.playermodeloverlay.PlayerModelOverlayService;

/**
 *
 * @author Thomas Werner
 */
public class PlayerModelOverlayServiceImpl extends PlayerModelOverlayService {

    private final PropertyChangeSupport propChangeSupport;
    private boolean visible = false;

    public PlayerModelOverlayServiceImpl() {
        propChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public boolean isPlayerModelOverlayVisible() {
        return visible;
    }

    public void setPlayerModelOverlayVisible(final boolean visible) {
        final boolean oldValue = visible;
        this.visible = visible;
        propChangeSupport.firePropertyChange(PlayerModelOverlayService.PROPERTY_VISIBILITY, oldValue, visible);
    }

}
