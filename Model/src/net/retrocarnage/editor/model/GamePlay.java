package net.retrocarnage.editor.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import javax.swing.event.ChangeEvent;

/**
 * Contains the gameplay of a Mission.
 *
 * @author Thomas Werner
 */
public final class GamePlay {

    public static final String PROPERTY_LAYERS = "layers";
    public static final String PROPERTY_MISSION_ID = "missionid";
    public static final String PROPERTY_SECTIONS = "sections";
    public static final String PROPERTY_UNKNOWN = "unknown";

    private final PropertyChangeSupport propertyChangeSupport;
    private final PropertyChangeListener nestedObjectChangeListener;    

    private String missionId;
    private ObservableList<Layer> layers;
    private ObservableList<Section> sections;

    public GamePlay() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        nestedObjectChangeListener = (PropertyChangeEvent e) -> 
                propertyChangeSupport.firePropertyChange(PROPERTY_UNKNOWN, null, null);
        
        layers = new ObservableList<>();
        layers.addChangeListener((ChangeEvent ce) -> propertyChangeSupport
                .firePropertyChange(PROPERTY_LAYERS, null, layers)
        );
        sections = new ObservableList<>();
        sections.addChangeListener((ChangeEvent ce) -> propertyChangeSupport
                .firePropertyChange(PROPERTY_SECTIONS, null, sections)
        );
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

    public void setMissionId(final String missionId) {
        final String oldMissionId = this.missionId;
        this.missionId = missionId;
        propertyChangeSupport.firePropertyChange(PROPERTY_MISSION_ID, oldMissionId, this.missionId);
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(final List<Layer> layers) {
        for(Layer l: this.layers) {
            l.removePropertyChangeListener(nestedObjectChangeListener);
        }
        this.layers.clear();
        this.layers.addAll(layers);
        for(Layer l: this.layers) {
            l.addPropertyChangeListener(nestedObjectChangeListener);
        }
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(final List<Section> sections) {
        for(Section s: this.sections) {
            s.removePropertyChangeListener(nestedObjectChangeListener);
        }
        this.sections.clear();
        this.sections.addAll(sections);
        for(Section s: this.sections) {
            s.addPropertyChangeListener(nestedObjectChangeListener);
        }
    }

}
