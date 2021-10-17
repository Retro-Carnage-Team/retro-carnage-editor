package net.retrocarnage.editor.zoom.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import net.retrocarnage.editor.zoom.ZoomService;

/**
 * Implementation of ZoomService.
 *
 * @author Thomas Werner
 */
public class ZoomServiceImpl extends ZoomService {

    private final PropertyChangeSupport propChangeSupport;
    private int zoomLevel = 100;

    public ZoomServiceImpl() {
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
    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(final int newZoomLevel) {
        if ((newZoomLevel % 10 == 0) && (newZoomLevel >= 10) && (newZoomLevel <= 300)) {
            final int oldValue = zoomLevel;
            zoomLevel = newZoomLevel;
            propChangeSupport.firePropertyChange(ZoomService.PROPERTY_ZOOM, oldValue, zoomLevel);
        }
    }

}
