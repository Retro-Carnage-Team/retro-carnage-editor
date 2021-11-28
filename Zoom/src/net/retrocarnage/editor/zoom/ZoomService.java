package net.retrocarnage.editor.zoom;

import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.zoom.impl.ZoomServiceImpl;

/**
 * A service that can be used to manage / get the current zoom level.
 *
 * @author Thomas Werner
 */
public abstract class ZoomService {

    public static final String PROPERTY_ZOOM = "zoom";

    private static final ZoomService zoomServiceImpl = new ZoomServiceImpl();

    public abstract void addPropertyChangeListener(final PropertyChangeListener listener);

    public abstract void removePropertyChangeListener(final PropertyChangeListener listener);

    /**
     * @return the zoom level in percent
     */
    public abstract int getZoomLevel();

    /**
     * @return an instance of this service
     */
    public static ZoomService getDefault() {
        return zoomServiceImpl;
    }

}
