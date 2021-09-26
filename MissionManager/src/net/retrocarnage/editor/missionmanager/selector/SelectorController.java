package net.retrocarnage.editor.missionmanager.selector;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import net.retrocarnage.editor.missionmanager.MissionService;
import static net.retrocarnage.editor.missionmanager.MissionService.PROPERTY_MISSIONS;
import net.retrocarnage.editor.model.Mission;

/**
 * Controller for the SelectorTopComponent.
 *
 * @author Thomas Werner
 */
class SelectorController {

    private final PropertyChangeListener serviceChangeListener;
    private DefaultListModel<Mission> listModel;

    SelectorController() {
        final MissionService missionService = MissionService.getDefault();
        serviceChangeListener = (PropertyChangeEvent pce) -> {
            if (PROPERTY_MISSIONS.equals(pce.getPropertyName()) && null != listModel) {
                listModel.clear();
                listModel.addAll(missionService.getMissions());
            }
        };
        missionService.addPropertyChangeListener(serviceChangeListener);
    }

    void close() {
        MissionService.getDefault().removePropertyChangeListener(serviceChangeListener);
    }

    ListModel<Mission> getListModel() {
        if (null == listModel) {
            listModel = new DefaultListModel<>();
            listModel.addAll(MissionService.getDefault().getMissions());
        }
        return listModel;
    }

}
