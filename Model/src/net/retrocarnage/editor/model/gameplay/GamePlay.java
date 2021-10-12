package net.retrocarnage.editor.model.gameplay;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the gameplay of a Mission.
 *
 * @author Thomas Werner
 */
public class GamePlay {

    public static final String PROPERTY_UNKNOWN = "unknown";

    private final PropertyChangeSupport propertyChangeSupport;

    private String missionId;
    private List<Section> sections;

    public GamePlay() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        sections = new ArrayList<>();
    }

    public GamePlay(final String missionId) {
        this();
        this.missionId = missionId;
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Informs listeners that some property of this object changed.
     *
     * This is useful to update e.g. the GamePlayEditor when it's content has been updated.
     */
    public void firePropertyChanged() {
        propertyChangeSupport.firePropertyChange(PROPERTY_UNKNOWN, null, null);
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
