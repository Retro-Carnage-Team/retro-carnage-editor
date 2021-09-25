package net.retrocarnage.editor.missionmanager.editor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import net.retrocarnage.editor.model.Location;
import net.retrocarnage.editor.model.Mission;

/**
 * A thin wrapper around a Mission that can notify listeners about changes.
 *
 * @author Thomas Werner
 */
class MissionBean extends Mission {

    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_BRIEFING = "briefing";
    public static final String PROPERTY_CLIENT_ASSET_ID = "clientAssetId";
    public static final String PROPERTY_LOCATION = "location";
    public static final String PROPERTY_SONG = "song";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_REWARD = "reward";

    private final PropertyChangeSupport propertyChangeSupport;
    private Mission delegate;

    MissionBean() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @JsonIgnore
    Mission getMission() {
        return delegate;
    }

    void setMission(final Mission mission) {
        delegate = null == mission ? null : mission.getPartialCopyOfMetaData();
    }

    void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String getId() {
        return null != delegate ? delegate.getId() : null;
    }

    @Override
    public void setId(final String id) {
        if (null != delegate) {
            final String oldValue = delegate.getId();
            delegate.setId(id);

            if (isStringChanged(oldValue, id)) {
                propertyChangeSupport.firePropertyChange(PROPERTY_ID, oldValue, id);
            }
        }
    }

    @Override
    public String getBriefing() {
        return null != delegate ? delegate.getBriefing() : null;
    }

    @Override
    public void setBriefing(final String briefing) {
        if (null != delegate) {
            final String oldValue = delegate.getBriefing();
            delegate.setBriefing(briefing);

            if (isStringChanged(oldValue, briefing)) {
                propertyChangeSupport.firePropertyChange(PROPERTY_BRIEFING, oldValue, briefing);
            }
        }
    }

    @Override
    public String getClient() {
        return null != delegate ? delegate.getClient() : null;
    }

    @Override
    public void setClient(final String client) {
        if (null != delegate) {
            final String oldValue = delegate.getClient();
            delegate.setClient(client);

            if (isStringChanged(oldValue, client)) {
                propertyChangeSupport.firePropertyChange(PROPERTY_CLIENT_ASSET_ID, oldValue, client);
            }
        }
    }

    @Override
    public Location getLocation() {
        return null != delegate ? delegate.getLocation() : null;
    }

    @Override
    public void setLocation(final Location location) {
        if (null != delegate) {
            final Location oldValue = delegate.getLocation();
            delegate.setLocation(location);

            if (isLocationChanged(oldValue, location)) {
                propertyChangeSupport.firePropertyChange(PROPERTY_LOCATION, oldValue, location);
            }
        }
    }

    @Override
    public String getSong() {
        return null != delegate ? delegate.getSong() : null;
    }

    @Override
    public void setSong(final String song) {
        if (null != delegate) {
            final String oldValue = delegate.getSong();
            delegate.setSong(song);

            if (isStringChanged(oldValue, song)) {
                propertyChangeSupport.firePropertyChange(PROPERTY_SONG, oldValue, song);
            }
        }
    }

    @Override
    public String getName() {
        return null != delegate ? delegate.getName() : null;
    }

    @Override
    public void setName(final String name) {
        if (null != delegate) {
            final String oldValue = delegate.getName();
            delegate.setName(name);

            if (isStringChanged(oldValue, name)) {
                propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
            }
        }
    }

    @Override
    public int getReward() {
        return null != delegate ? delegate.getReward() : 0;
    }

    @Override
    public void setReward(final int reward) {
        if (null != delegate) {
            final int oldValue = delegate.getReward();
            delegate.setReward(reward);

            if (oldValue != reward) {
                propertyChangeSupport.firePropertyChange(PROPERTY_REWARD, oldValue, reward);
            }
        }
    }

    private static boolean isStringChanged(final String oldValue, final String newValue) {
        return (((oldValue != null) && (newValue == null))
                || ((oldValue == null) && (newValue != null))
                || ((oldValue != null) && !oldValue.equals(newValue)));
    }

    private static boolean isLocationChanged(final Location oldValue, final Location newValue) {
        final String oldString = null == oldValue ? null : oldValue.toString();
        final String newString = null == newValue ? null : newValue.toString();
        return isStringChanged(oldString, newString);
    }

}
