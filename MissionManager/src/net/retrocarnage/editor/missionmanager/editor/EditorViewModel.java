package net.retrocarnage.editor.missionmanager.editor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.missionmanager.MissionServiceFactory;
import net.retrocarnage.editor.model.Mission;

/**
 * A ViewModel class for the EditorTopComponent.
 *
 * @author Thomas Werner
 */
class EditorViewModel {

    static final String PROPERTY_SELECTED_MISSION = "selectedMission";
    static final String PROPERTY_UNSAVED_CHANGES = "unsavedChanges";
    static final String PROPERTY_MISSIONS = "missions";

    private final PropertyChangeSupport propertyChangeSupport;

    private List<Mission> missions;
    private final MissionBean selectedMissionBean;
    private boolean unsavedChanges = false;

    EditorViewModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        selectedMissionBean = new MissionBean();
        selectedMissionBean.addPropertyChangeListener(pce -> {
            if (!unsavedChanges) {
                unsavedChanges = true;
                this.propertyChangeSupport.firePropertyChange(PROPERTY_UNSAVED_CHANGES, false, true);
            }
        });
    }

    void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
        selectedMissionBean.addPropertyChangeListener(listener);
    }

    void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
        selectedMissionBean.removePropertyChangeListener(listener);
    }

    List<SelectableMission> getMissions() {
        return missions.stream().map(m -> new SelectableMission(m)).collect(Collectors.toList());
    }

    Mission getSelectedMission() {
        return selectedMissionBean;
    }

    /**
     * Used to reset the mission or set a new mission (that has not been saved, yet)
     *
     * @param mission the Mission to be set
     */
    void setSelectedMission(final Mission mission) {
        selectedMissionBean.setMission(mission);
        unsavedChanges = false;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_MISSION, null, selectedMissionBean);
    }

    /**
     * Used to select an existing mission from the Mission service.
     *
     * @param mission specifies the Mission to be selected
     */
    void selectMission(final SelectableMission mission) {
        selectedMissionBean.setMission(
                missions.stream()
                        .filter(m -> m.getId().equals(mission.getId()))
                        .findFirst()
                        .get()
        );
        this.propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_MISSION, null, selectedMissionBean);
    }

    boolean isEnabled() {
        return null != selectedMissionBean.getMission();
    }

    boolean isModified() {
        return unsavedChanges;
    }

    void setChangesSaved() {
        this.unsavedChanges = false;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_UNSAVED_CHANGES, true, false);
    }

    void updateMissionsFromService() {
        final MissionService service = MissionServiceFactory.buildMissionService();
        missions = new ArrayList<>();
        missions.addAll(service.getMissions());
        this.propertyChangeSupport.firePropertyChange(PROPERTY_MISSIONS, null, missions);
    }

}
