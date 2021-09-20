package net.retrocarnage.editor.missionmanager.editor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.missionmanager.impl.MissionServiceImpl;
import net.retrocarnage.editor.model.Mission;

/**
 * A ViewModel class for the EditorTopComponent.
 *
 * @author Thomas Werner
 */
class EditorViewModel {

    static final String PROPERTY_SELECTED_MISSION = "selectedMission";
    static final String PROPERTY_UNSAVED_CHANGED = "unsavedChanges";
    static final String PROPERTY_MISSIONS = "missions";

    private final PropertyChangeSupport propertyChangeSupport;

    private List<Mission> missions;
    private final MissionBean selectedMissionBean;
    private boolean unsavedChanges = false;

    EditorViewModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        updateMissionsFromService();

        selectedMissionBean = new MissionBean();
        selectedMissionBean.addPropertyChangeListener(pce -> {
            if (!unsavedChanges) {
                unsavedChanges = true;
                this.propertyChangeSupport.firePropertyChange(PROPERTY_UNSAVED_CHANGED, false, true);
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

    void selectMission(final SelectableMission mission) {
        selectedMissionBean.setMission(
                missions.stream()
                        .filter(m -> m.getId().equals(mission.getId()))
                        .findFirst()
                        .get()
        );
        this.propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_MISSION, null, selectedMissionBean);
    }

    void setChangesSaved() {
        this.unsavedChanges = false;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_UNSAVED_CHANGED, true, false);
    }

    private void updateMissionsFromService() {
        final MissionServiceImpl service = (MissionServiceImpl) MissionService.getDefault();
        missions = new ArrayList<>();
        missions.addAll(service.getMissions());
        this.propertyChangeSupport.firePropertyChange(PROPERTY_MISSIONS, null, missions);
    }

}
